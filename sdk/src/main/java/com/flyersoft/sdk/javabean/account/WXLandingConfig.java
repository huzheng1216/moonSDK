package com.flyersoft.sdk.javabean.account;

/**
 * 登陆信息
 * Created by 37399 on 2016/11/5.
 */
public class WXLandingConfig {

    //微信登陆配置
    private String scope;//应用授权作用域，如获取用户个人信息则填写snsapi_userinfo
    private String state;//用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
    private String appid;//应用唯一标识，在微信开放平台提交应用审核通过后获得
    private String appsecret;//密钥

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }
}
