package com.flyersoft.sdk.widget.user.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.flyersoft.sdk.R;
import com.flyersoft.sdk.config.Const;
import com.flyersoft.sdk.http.MRManager;
import com.flyersoft.sdk.http.body.BaseRequest;
import com.flyersoft.sdk.http.body.PayBody;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.DefaultInfo;
import com.flyersoft.sdk.javabean.account.AccountAction;
import com.flyersoft.sdk.javabean.account.AmountInfo;
import com.flyersoft.sdk.javabean.account.PayConfig;
import com.flyersoft.sdk.javabean.account.UserInfo;
import com.flyersoft.sdk.tools.LogTools;
import com.flyersoft.sdk.tools.ToastTools;
import com.flyersoft.sdk.tools.Tools;
import com.flyersoft.sdk.widget.detail.DetailHeaderLayout;
import com.flyersoft.sdk.widget.user.AccountData;
import com.flyersoft.sdk.widget.user.buys.MyBuyBooksActivity;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 用户中心
 * Created by zheng.hu on 2016/11/9.
 */
public class UserInfoActivity extends Activity {

    private RechargeDialog rechargeDialog;
    private RechargeChoseDialog rechargeChoseDialog;
    private TextView amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initParam();
        initData();
    }

    protected void initView() {
        setContentView(R.layout.userinfo_main);
        EventBus.getDefault().register(this);
    }

    protected void initParam() {
        DetailHeaderLayout detailHeaderLayout = (DetailHeaderLayout) findViewById(R.id.userinfo_header_layout);
        detailHeaderLayout.setData(R.string.userinfo_title, new DetailHeaderLayout.OnHeaderLayoutListener() {
            @Override
            public void onFinish() {
                UserInfoActivity.this.finish();
            }

            @Override
            public boolean exChangeSelectState(int state) {
                return false;
            }
        });
        amount = (TextView) findViewById(R.id.userinfo_balance_count_tx);
    }

    protected void initData() {
        MRManager.getInstance(this).getAmount(new RequestCallBack<AmountInfo>() {
            @Override
            public void onSuccess(AmountInfo userInfo) {
                amount.setText(userInfo.getData() + "");
            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }

    //去充值
    public void recharge(View v) {
        rechargeDialog = new RechargeDialog(this, new RechargeDialog.RechargeListener() {
            @Override
            public void recharge(int count) {
                rechargeDialog.dismiss();
                choseRechargeType(count);
            }
        });
        rechargeDialog.show();
    }

    //选择充值类型
    private void choseRechargeType(int count) {
        rechargeChoseDialog = new RechargeChoseDialog(this, count, new RechargeChoseDialog.RechargeListener() {
            @Override
            public void rechargeWX(int count) {
                rechargeChoseDialog.dismiss();
                wxPay(count);
            }

            @Override
            public void rechargeZFB(int count) {
                rechargeChoseDialog.dismiss();
                zfbPay(count);
            }
        });
        rechargeChoseDialog.show();
    }

    /**
     * 微信支付
     *
     * @param count
     */
    private void wxPay(int count) {
        MRManager.getInstance(this).getWXPayInfo(new PayBody(Const.ACCOUNTTYPE_WEIXIN, count * 100))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<BaseRequest<PayConfig>, Observable<PayConfig>>() {
                    @Override
                    public Observable<PayConfig> call(BaseRequest<PayConfig> userInfoBaseRequest) {
                        return Observable.just(userInfoBaseRequest.getData());
                    }
                })
                .subscribe(new Subscriber<PayConfig>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogTools.showLog(LogTools.TAG_ACCOUNT, "onError = " + e.toString());
                    }

                    @Override
                    public void onNext(PayConfig payConfig) {
                        IWXAPI api = WXAPIFactory.createWXAPI(UserInfoActivity.this, payConfig.getAppid(), true);
                        AccountData.getInstance(UserInfoActivity.this).setWxPayConfig(payConfig);
                        PayReq request = new PayReq();
                        request.appId = payConfig.getAppid();
                        request.partnerId = payConfig.getPartnerid();
                        request.prepayId = payConfig.getPrepayid();
                        request.packageValue = "Sign=WXPay";
                        request.nonceStr = payConfig.getNoncestr();
                        request.timeStamp = payConfig.getTimestamp();
                        request.sign = payConfig.getSign();
                        api.sendReq(request);
                    }
                });
    }

    /**
     * 支付宝支付
     *
     * @param count
     */
    private void zfbPay(int count) {

        MRManager.getInstance(this).getZFBPayInfo(new PayBody(Const.ACCOUNTTYPE_ALIPAY, count * 100))
                .observeOn(Schedulers.newThread())
                .flatMap(new Func1<DefaultInfo, Observable<Map<String, String>>>() {
                    @Override
                    public Observable<Map<String, String>> call(DefaultInfo defaultInfo) {

                        LogTools.showLog(LogTools.TAG_ACCOUNT, "defaultInfo.getData() = " + defaultInfo.getData());
                        PayTask alipay = new PayTask(UserInfoActivity.this);
                        Map<String, String> result = alipay.payV2(defaultInfo.getData(), true);
                        return Observable.just(result);
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Map<String, String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogTools.showLog(LogTools.TAG_ACCOUNT, "onError = " + e.toString());
                    }

                    @Override
                    public void onNext(Map<String, String> result) {

                        LogTools.showLog(LogTools.TAG_ACCOUNT, "onNext = " + result.toString());
                        @SuppressWarnings("unchecked")
                        PayResult payResult = new PayResult(result);
                        /**
                         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            ToastTools.showToast(UserInfoActivity.this, "支付成功。");
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            ToastTools.showToast(UserInfoActivity.this, "支付异常。");
                        }
                        initData();
                    }
                });
    }

    //购买的书籍
    public void showPurchase(View v) {
        Intent intent = new Intent(UserInfoActivity.this, MyBuyBooksActivity.class);
        startActivity(intent);
    }

    //充值记录
    public void showRecharge(View v) {
        Intent intent = new Intent(UserInfoActivity.this, RechargeHistoryActivity.class);
        startActivity(intent);
    }

    public void logOut(View v) {
        Tools.setInformain("nxck", "", this);
        MRManager.getInstance(this).logOut(new RequestCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                ToastTools.showToast(UserInfoActivity.this, "成功退出");
            }
            @Override
            public void onFailure(String msg) {
            }
        });
        AccountData.getInstance(this).setmUserInfo(null);
        EventBus.getDefault().post(new UserInfo());
        this.finish();
    }


    @Subscribe
    public void onEventMainThread(AccountAction token) {
        switch (token.getAction()) {
            case AccountAction.ACCREDIT_PAY_OK:
                ToastTools.showToast(this, "同意支付!");
                break;
            case AccountAction.ACCREDIT_PAY_NO:
                ToastTools.showToast(this, "微信用户不同意支付!");
                break;
            case AccountAction.ACCREDIT_PAY_CANCEL:
                ToastTools.showToast(this, "微信用户取消了支付!");
                break;
        }
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
