package com.flyersoft.sdk.http.biz;

import android.content.Context;

import com.flyersoft.sdk.http.base.BaseBiz;
import com.flyersoft.sdk.http.body.BaseRequest;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.BookDetail;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Describe:模块图书业务
 * Created by ${zheng.hu} on 2016/9/27.
 */
public class ModuleBookBiz extends BaseBiz{

    public ModuleBookBiz(Context context){
        super(context);
    }

    /**
     * 获取活动书籍列表
     * @param moduleId 活动id
     * @param from
     * @param limit
     * @param callback
     */
    public void getModuleBooks(String moduleId, int from, int limit, final RequestCallBack<List<BookDetail>> callback){

        Observable<BaseRequest<List<BookDetail>>> observable = mMRRequestService.getActivityBooks(moduleId, from, limit);

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
                    public void onNext(BaseRequest<List<BookDetail>> listBaseRequest) {
                        callback.onSuccess(listBaseRequest.getData());
                    }
                });

    }
}
