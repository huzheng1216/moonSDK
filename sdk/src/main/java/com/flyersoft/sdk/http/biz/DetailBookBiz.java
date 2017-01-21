package com.flyersoft.sdk.http.biz;

import android.content.Context;

import com.flyersoft.sdk.http.base.BaseBiz;
import com.flyersoft.sdk.http.body.BaseRequest;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.DetailBookInfo;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Describe:二级页图书详情业务
 * Created by ${zheng.hu} on 2016/9/27.
 */
public class DetailBookBiz extends BaseBiz{

    public DetailBookBiz(Context context){
        super(context);
    }

    /**
     * 获取活动书籍列表
     * @param bookId 书id
     * @param callback
     */
    public void getDetailBook(String bookId, final RequestCallBack<DetailBookInfo> callback){

        Observable<BaseRequest<DetailBookInfo>> observable = mMRRequestService.getDetailBook(bookId);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<DetailBookInfo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseRequest<DetailBookInfo> baseRequest) {
                        callback.onSuccess(baseRequest.getData());
                    }
                });
    }
}
