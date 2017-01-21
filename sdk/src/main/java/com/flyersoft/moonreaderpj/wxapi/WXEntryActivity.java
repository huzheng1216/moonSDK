package com.flyersoft.moonreaderpj.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.flyersoft.sdk.javabean.account.AccountAction;
import com.flyersoft.sdk.javabean.account.WXLandingConfig;
import com.flyersoft.sdk.tools.LogTools;
import com.flyersoft.sdk.widget.user.AccountData;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 37399 on 2016/11/2.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogTools.showLog(LogTools.TAG_ACCOUNT,"WXEntryActivity onCreate");
        WXLandingConfig WXLandingConfig = AccountData.getInstance(this).getwXLandingConfig();
        if(WXLandingConfig ==null){
            return;
        }
        api = WXAPIFactory.createWXAPI(this, WXLandingConfig.getAppid(), false);
        api.registerApp(WXLandingConfig.getAppid());
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq arg0) {
    }

    @Override
    public void onResp(BaseResp resp) {

        LogTools.showLog(LogTools.TAG_ACCOUNT, "WXEntryActivity onResp = " + resp.errCode);
        switch (resp.errCode){
            //同意
            case BaseResp.ErrCode.ERR_OK:
                SendAuth.Resp sendResp = (SendAuth.Resp) resp;
                String code = sendResp.code;
                LogTools.showLog(LogTools.TAG_ACCOUNT, "sendResp.code = "+sendResp.code);
                AccountAction action1 = new AccountAction(AccountAction.ACCREDIT_LANDING_OK);
                action1.setAccredit_landing_code(code);
                EventBus.getDefault().post(action1);
                break;
            //不同意
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                AccountAction action2 = new AccountAction(AccountAction.ACCREDIT_LANDING_NO);
                EventBus.getDefault().post(action2);
                break;
            //取消
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                AccountAction action3 = new AccountAction(AccountAction.ACCREDIT_LANDING_CANCEL);
                EventBus.getDefault().post(action3);
                break;
        }
        finish();
    }
}
