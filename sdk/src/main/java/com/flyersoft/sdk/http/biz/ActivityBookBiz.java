package com.flyersoft.sdk.http.biz;

import android.content.Context;

import com.flyersoft.sdk.http.base.BaseBiz;
import com.flyersoft.sdk.http.body.BaseRequest;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.Book;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 活动图书列表
 * Created by Administrator on 2016/9/23.
 */
public class ActivityBookBiz extends BaseBiz {

    public ActivityBookBiz(Context context){
        super(context);
    }

    /**
     * 获取活动书籍列表
     */
    public void getActivityBooks(final RequestCallBack<List<Book>> callback){

        Observable<BaseRequest<List<Book>>> observable = mMRRequestService.getActivityBooks();
        observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseRequest<List<Book>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseRequest<List<Book>> activityBooks) {
                        callback.onSuccess(activityBooks.getData());
                    }
                });
    }
}
