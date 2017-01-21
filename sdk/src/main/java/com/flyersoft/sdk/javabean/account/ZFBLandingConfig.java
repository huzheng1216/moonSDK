package com.flyersoft.sdk.javabean.account;

/**
 * 登陆信息
 * Created by 37399 on 2016/11/5.
 */
public class ZFBLandingConfig {

    //支付宝登陆配置
    private String data;//授权url
    private String errorCode;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
