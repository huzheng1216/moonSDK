package com.flyersoft.sdk.widget.user;

import android.app.Activity;
import android.content.Context;

import com.flyersoft.sdk.http.MRManager;
import com.flyersoft.sdk.http.body.BaseRequest;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.account.PayConfig;
import com.flyersoft.sdk.javabean.account.UserInfo;
import com.flyersoft.sdk.javabean.account.WXLandingConfig;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 37399 on 2016/10/31.
 */
public class AccountData {

    private WXLandingConfig wXLandingConfig;//微信登陆配置，供WXEntryActivity获取
    private PayConfig wxPayConfig;//微信支付配置，供WXEntryActivity获取

    private UserInfo mUserInfo;

    private static volatile AccountData accountData = null;

    private AccountData(Activity context) {
    }

    public static AccountData getInstance(Activity context) {
        synchronized (AccountData.class) {
            if (accountData == null) {
                accountData = new AccountData(context);
            }
            return accountData;
        }
    }

    public void getUserInfo(Context context, final RequestCallBack<UserInfo> requestCallBack) {
        if (mUserInfo == null) {
            MRManager.getInstance(context).getUserInfo()
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BaseRequest<UserInfo>>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            mUserInfo = new UserInfo();
                            mUserInfo.setNeedSignin(true);
                            mUserInfo.setPetName("游客");
                            requestCallBack.onSuccess(mUserInfo);
                        }

                        @Override
                        public void onNext(BaseRequest<UserInfo> userInfo) {
                            mUserInfo = userInfo.getData();
                            mUserInfo.setNeedSignin(false);
                            requestCallBack.onSuccess(mUserInfo);
                        }
                    });
        } else {
            requestCallBack.onSuccess(mUserInfo);
        }
    }

    public WXLandingConfig getwXLandingConfig() {
        return wXLandingConfig;
    }

    public void setwXLandingConfig(WXLandingConfig wXLandingConfig) {
        this.wXLandingConfig = wXLandingConfig;
    }

    public PayConfig getWxPayConfig() {
        return wxPayConfig;
    }

    public void setWxPayConfig(PayConfig wxPayConfig) {
        this.wxPayConfig = wxPayConfig;
    }

    public UserInfo getmUserInfo() {
        return mUserInfo;
    }

    public void setmUserInfo(UserInfo mUserInfo) {
        this.mUserInfo = mUserInfo;
    }
}
