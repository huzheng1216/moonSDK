package com.flyersoft.sdk.http.biz;

import android.content.Context;

import com.flyersoft.sdk.config.Const;
import com.flyersoft.sdk.http.base.BaseBiz;
import com.flyersoft.sdk.http.body.BaseRequest;
import com.flyersoft.sdk.http.body.LandingCodeBody;
import com.flyersoft.sdk.http.body.PayBody;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.BookContent;
import com.flyersoft.sdk.javabean.BookDetail;
import com.flyersoft.sdk.javabean.ChargeRecords;
import com.flyersoft.sdk.javabean.DefaultCode;
import com.flyersoft.sdk.javabean.DefaultInfo;
import com.flyersoft.sdk.javabean.ShelfBook;
import com.flyersoft.sdk.javabean.account.AmountInfo;
import com.flyersoft.sdk.javabean.account.PayConfig;
import com.flyersoft.sdk.javabean.account.UserInfo;
import com.flyersoft.sdk.javabean.account.WXLandingConfig;
import com.flyersoft.sdk.javabean.account.ZFBLandingConfig;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 处理账户相关业务
 * Created by 37399 on 2016/10/23.
 */
public class AccountBiz extends BaseBiz {

    public AccountBiz(Context context) {
        super(context);
    }

    /**
     * 获取登陆配置
     */
    public Observable<BaseRequest<WXLandingConfig>> getLandingConfig(int app) {

        String appStr = Const.ACCOUNT_ZFB == app ? "alipay" : (Const.ACCOUNT_WX == app ? "weixin" : "");
        if (appStr.isEmpty()) {
            return null;
        }
        return mMRRequestService.getLandingConfig(appStr);
    }

    /**
     * 获取支付宝登陆配置
     *
     * @return
     */
    public Observable<ZFBLandingConfig> getZFBLandingConfig() {
        return mMRRequestService.getZFBLandingConfig();
    }

    /**
     * 获取用户信息
     */
    public Observable<BaseRequest<UserInfo>> getUserInfo() {

        return mMRRequestService.getUserInfo();
    }

    /**
     * 获取已购买书籍
     */
    public void getBuyBookRecords(int from, int limitfinal, final RequestCallBack<List<BookDetail>> callback) {

        Observable<BaseRequest<List<BookDetail>>> observable = mMRRequestService.getBuyBookRecords(from, limitfinal);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<List<BookDetail>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseRequest<List<BookDetail>> userInfo) {
                        callback.onSuccess(userInfo.getData());
                    }
                });
    }

    /**
     * 获取用户充值记录
     */
    public void getChargeRecords(int from, int limitfinal, final RequestCallBack<List<ChargeRecords>> callback) {

        Observable<BaseRequest<List<ChargeRecords>>> observable = mMRRequestService.getChargeRecords(from, limitfinal);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<List<ChargeRecords>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseRequest<List<ChargeRecords>> userInfo) {
                        callback.onSuccess(userInfo.getData());
                    }
                });
    }

    /**
     * 上传授权的code
     *
     * @return
     */
    public Observable<BaseRequest<UserInfo>> uploadCode(LandingCodeBody landingCodeBody) {
        return mMRRequestService.uploadCode(landingCodeBody.toMap());
    }

    /**
     * 获取微信支付配置
     *
     * @param payBody
     * @return
     */
    public Observable<BaseRequest<PayConfig>> getWXPayInfo(PayBody payBody) {
        return mMRRequestService.getWXPayInfo(payBody.toMap());
    }

    /**
     * 获取支付宝支付配置
     *
     * @param payBody
     * @return
     */
    public Observable<DefaultInfo> getZFBPayInfo(PayBody payBody) {
        return mMRRequestService.getZFBPayInfo(payBody.toMap());
    }

    /**
     * 获取账户余额
     * @return
     */
    public void getAmount(final RequestCallBack<AmountInfo> callback) {
        Observable<AmountInfo> observable = mMRRequestService.getAmount();
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AmountInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(AmountInfo userInfo) {
                        callback.onSuccess(userInfo);
                    }
                });
    }

    /**
     * 购买书籍
     * @param bookId
     * @param chapterNums
     * @param autoDebits
     * @param callback
     */
    public void buyBook(String bookId, String chapterNums, boolean autoDebits, final RequestCallBack<Boolean> callback) {
        Observable<BaseRequest> observable = mMRRequestService.buyBook(bookId,chapterNums,autoDebits);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseRequest baseRequest) {
                        if(baseRequest.getErrorCode() == 0){
                            callback.onSuccess(true);
                        }else{
                            callback.onFailure("");
                        }
                    }
                });
    }

    /**
     * 添加到书架
     * @param bookId
     * @param callback
     */
    public void addToSelf(String bookId, final RequestCallBack<BookDetail> callback) {
        Observable<BaseRequest<BookDetail>> observable = mMRRequestService.addToSelf(bookId);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<BookDetail>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseRequest<BookDetail> baseRequest) {
                        if(baseRequest.getErrorCode() == 0){
                            callback.onSuccess(baseRequest.getData());
                        }else{
                            callback.onFailure("");
                        }
                    }
                });
    }

    /**
     * 是否在书架上
     * @param bookId
     * @param callback
     */
    public void ifInSelf(String bookId, final RequestCallBack<DefaultCode> callback) {
        Observable<BaseRequest<DefaultCode>> observable = mMRRequestService.ifInSelf(bookId);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<DefaultCode>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseRequest<DefaultCode> baseRequest) {
                        if(baseRequest.getData().getCode() == 2){
                            callback.onSuccess(baseRequest.getData());
                        }else{
                            callback.onFailure("");
                        }
                    }
                });
    }

    /**
     * 获取书架内容
     * @param from
     * @param limit
     * @param callback
     */
    public void getMyShelf(int from, int limit, final RequestCallBack<List<ShelfBook>> callback) {
        Observable<BaseRequest<List<ShelfBook>>> observable = mMRRequestService.getMyShelf(from, limit);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<List<ShelfBook>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseRequest<List<ShelfBook>> baseRequest) {
                        callback.onSuccess(baseRequest.getData());
                    }
                });
    }

    /**
     * 获取正文内容
     * @param bookId
     * @param chapterNo
     * @param direction
     * @param callback
     */
    public void getContent(String bookId, int chapterNo, String direction, final RequestCallBack<BaseRequest<BookContent>> callback) {
        Observable<BaseRequest<BookContent>> observable = mMRRequestService.getContent(bookId, chapterNo, direction);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<BookContent>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseRequest<BookContent> baseRequest) {
                        callback.onSuccess(baseRequest);
                    }
                });
    }

    /**
     * 退出登陆
     * @return
     * @param callback
     */
    public void logOut(final RequestCallBack<Boolean> callback) {
        Observable<BaseRequest<Boolean>> observable = mMRRequestService.logOut();
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<Boolean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onSuccess(false);
                    }

                    @Override
                    public void onNext(BaseRequest<Boolean> baseRequest) {
                        callback.onSuccess(true);
                    }
                });
    }
}
