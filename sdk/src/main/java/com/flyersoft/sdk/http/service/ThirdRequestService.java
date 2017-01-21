package com.flyersoft.sdk.http.service;

import com.flyersoft.sdk.javabean.account.WXAccessToken;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 请求第三方服务接口
 * Created by Administrator on 2016/9/23.
 */
public interface ThirdRequestService {

    @GET("sns/oauth2/access_token")
    Observable<WXAccessToken> getAccessToken(@QueryMap Map<String, String> map);
}
