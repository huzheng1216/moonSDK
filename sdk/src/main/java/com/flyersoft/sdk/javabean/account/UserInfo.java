package com.flyersoft.sdk.javabean.account;

/**
 * 用户信息
 * Created by 37399 on 2016/10/23.
 */
public class UserInfo {

    public static final String ACCOUNTTYPE_ALIPAY = "alipay";
    public static final String ACCOUNTTYPE_WEIXIN = "weixin";

    private String accountType;
    private long createTime;
    private long updateTime;
    private String token;
    private String petName;
    private String id;
    private String headPic;
    private boolean needSignin = true;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public boolean isNeedSignin() {
        return needSignin;
    }

    public void setNeedSignin(boolean needSignin) {
        this.needSignin = needSignin;
    }
}
