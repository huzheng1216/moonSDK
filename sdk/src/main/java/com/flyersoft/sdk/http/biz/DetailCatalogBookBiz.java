package com.flyersoft.sdk.http.biz;

import android.content.Context;

import com.flyersoft.sdk.http.base.BaseBiz;
import com.flyersoft.sdk.http.body.BaseRequest;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.DetailCatalogDetail;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Describe:二级页图书目录列表业务
 * Created by ${zheng.hu} on 2016/9/27.
 */
public class DetailCatalogBookBiz extends BaseBiz{

    public DetailCatalogBookBiz(Context context){
        super(context);
    }

    /**
     * 获取活动书籍列表
     */
    public void getCatalogBooks(String bookId, int from, int maxCountfinal, final RequestCallBack<List<DetailCatalogDetail>> callback){

        Observable<BaseRequest<List<DetailCatalogDetail>>> observable = mMRRequestService.getCatalogBooks(bookId,from,maxCountfinal);
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<List<DetailCatalogDetail>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseRequest<List<DetailCatalogDetail>> listBaseRequest) {
                        callback.onSuccess(listBaseRequest.getData());
                    }
                });

    }
}
