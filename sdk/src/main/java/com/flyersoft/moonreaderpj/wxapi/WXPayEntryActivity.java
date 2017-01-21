package com.flyersoft.moonreaderpj.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.flyersoft.sdk.javabean.account.AccountAction;
import com.flyersoft.sdk.javabean.account.PayConfig;
import com.flyersoft.sdk.widget.user.AccountData;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
		PayConfig payConfig = AccountData.getInstance(this).getWxPayConfig();
		if(payConfig ==null){
			return;
		}
    	api = WXAPIFactory.createWXAPI(this, payConfig.getAppid());
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			switch (resp.errCode){
				//同意
				case BaseResp.ErrCode.ERR_OK:
					AccountAction action1 = new AccountAction(AccountAction.ACCREDIT_PAY_OK);
					EventBus.getDefault().post(action1);
					break;
				//不同意
				case BaseResp.ErrCode.ERR_AUTH_DENIED:
					AccountAction action2 = new AccountAction(AccountAction.ACCREDIT_PAY_NO);
					EventBus.getDefault().post(action2);
					break;
				//取消
				case BaseResp.ErrCode.ERR_USER_CANCEL:
					AccountAction action3 = new AccountAction(AccountAction.ACCREDIT_PAY_CANCEL);
					EventBus.getDefault().post(action3);
					break;
			}
		}
		finish();
	}
}