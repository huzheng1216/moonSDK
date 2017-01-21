package com.flyersoft.sdk.http.biz;

import com.flyersoft.sdk.config.HttpBaseConfig;
import com.flyersoft.sdk.http.body.WXAccessTokenBody;
import com.flyersoft.sdk.http.service.ThirdRequestService;
import com.flyersoft.sdk.javabean.account.WXAccessToken;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 *  第三方业务
 * Created by 37399 on 2016/11/5.
 */
public class ThirdBiz {

    protected ThirdRequestService mThirdRequestService;

    public ThirdBiz(String host){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .addHeader("Accept-Encoding", "gzip, deflate")
                                .addHeader("Accept-Language", "zh-CN")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                .addHeader("Cookie", "uid=654321")
                                .build();
                        return chain.proceed(request);
                    }

                });
        builder.connectTimeout(15, TimeUnit.SECONDS);
        Retrofit retorfit = new Retrofit.Builder()
                .baseUrl(HttpBaseConfig.HOST)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mThirdRequestService = retorfit.create(ThirdRequestService.class);
    }


    /**
     * 获取微信access_token
     */
    public Observable<WXAccessToken> getAccessToken(WXAccessTokenBody wXAccessTokenBody) {

        return mThirdRequestService.getAccessToken(wXAccessTokenBody.toMap());
    }
}
