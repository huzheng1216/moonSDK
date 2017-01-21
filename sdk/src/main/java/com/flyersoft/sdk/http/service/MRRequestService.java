package com.flyersoft.sdk.http.service;

import com.flyersoft.sdk.http.body.BaseRequest;
import com.flyersoft.sdk.javabean.Book;
import com.flyersoft.sdk.javabean.BookContent;
import com.flyersoft.sdk.javabean.BookDetail;
import com.flyersoft.sdk.javabean.CatalogDetail;
import com.flyersoft.sdk.javabean.ChargeRecords;
import com.flyersoft.sdk.javabean.DefaultCode;
import com.flyersoft.sdk.javabean.DefaultInfo;
import com.flyersoft.sdk.javabean.DetailBookInfo;
import com.flyersoft.sdk.javabean.DetailCatalogDetail;
import com.flyersoft.sdk.javabean.ShelfBook;
import com.flyersoft.sdk.javabean.account.AmountInfo;
import com.flyersoft.sdk.javabean.account.PayConfig;
import com.flyersoft.sdk.javabean.account.UserInfo;
import com.flyersoft.sdk.javabean.account.WXLandingConfig;
import com.flyersoft.sdk.javabean.account.ZFBLandingConfig;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 请求服务接口
 * Created by Administrator on 2016/9/23.
 */
public interface MRRequestService {

    @GET("mreader/common/listActivity.do")
    Observable<BaseRequest<List<Book>>> getActivityBooks();

    @FormUrlEncoded
    @POST("mreader/common/getModuleData.do")
    Observable<BaseRequest<List<BookDetail>>> getActivityBooks(@Field("moduleId") String moduleId, @Field("from") int from, @Field("limit") int limit);

    @POST("mreader/common/getCategory.do")
    Observable<BaseRequest<List<CatalogDetail>>> getCatalogBooks();

    @FormUrlEncoded
    @POST("mreader/common/queryCatalog.do")
    Observable<BaseRequest<List<DetailCatalogDetail>>> getCatalogBooks(@Field("bookId") String bookId, @Field("from") int from, @Field("maxCount") int maxCount);

    @FormUrlEncoded
    @POST("mreader/common/search.do")
    Observable<BaseRequest<List<BookDetail>>> getSearchBooks(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("mreader/common/getBookInfo.do")
    Observable<BaseRequest<DetailBookInfo>> getDetailBook(@Field("bookId") String bookId);

    @GET("mreader/third/beforeLogin.do?model=app")
    Observable<BaseRequest<WXLandingConfig>> getLandingConfig(@Query("app") String app);

    @GET("mreader/third/beforeLogin.do?model=app&app=alipay")
    Observable<ZFBLandingConfig> getZFBLandingConfig();

    @POST("mreader/u/getUserInfo.do")
    Observable<BaseRequest<UserInfo>> getUserInfo();

    @FormUrlEncoded
    @POST("mreader/u/getBuyBookRecords.do")
    Observable<BaseRequest<List<BookDetail>>> getBuyBookRecords(@Field("from") int from, @Field("limit") int limit);

    @FormUrlEncoded
    @POST("mreader/u/chargeRecords.do")
    Observable<BaseRequest<List<ChargeRecords>>> getChargeRecords(@Field("from") int from, @Field("limit") int limit);

    @GET("mreader/third/appThirdLogin.do")
    Observable<BaseRequest<UserInfo>> uploadCode(@QueryMap Map<String, String> map);

    @GET("mreader/third/beforePay.do")
    Observable<BaseRequest<PayConfig>> getWXPayInfo(@QueryMap Map<String, String> map);

    @GET("mreader/third/beforePay.do")
    Observable<DefaultInfo> getZFBPayInfo(@QueryMap Map<String, String> map);

    @GET("mreader/u/getAmount.do")
    Observable<AmountInfo> getAmount();

    @FormUrlEncoded
    @POST("mreader/u/batchBuy.do")
    Observable<BaseRequest> buyBook(@Field("bookId") String bookId, @Field("chapterNums") String chapterNums, @Field("autoDebits") boolean autoDebits);

    @FormUrlEncoded
    @POST("mreader/u/add2self.do")
    Observable<BaseRequest<BookDetail>> addToSelf(@Field("bookId") String bookId);

    @FormUrlEncoded
    @POST("mreader/common/ifInSelf.do")
    Observable<BaseRequest<DefaultCode>> ifInSelf(@Field("bookId") String bookId);

    @FormUrlEncoded
    @POST("mreader/u/getMyShelf.do")
    Observable<BaseRequest<List<ShelfBook>>> getMyShelf(@Field("from") int from, @Field("limit") int limit);

    @FormUrlEncoded
    @POST("mreader/common/getContent.do")
    Observable<BaseRequest<BookContent>> getContent(@Field("bookId") String bookId, @Field("chapterNo") int chapterNo, @Field("direction") String direction);

    @POST("mreader/u/logOut.do")
    Observable<BaseRequest<Boolean>> logOut();
}
