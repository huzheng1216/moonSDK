package com.flyersoft.sdk.javabean.account;

/**
 * 余额信息
 * Created by 37399 on 2016/11/5.
 */
public class AmountInfo {

    private int data;//余额
    private String errorCode;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
