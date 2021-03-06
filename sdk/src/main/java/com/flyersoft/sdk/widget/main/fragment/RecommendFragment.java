package com.flyersoft.sdk.widget.main.fragment;

import android.os.Bundle;

import com.flyersoft.sdk.http.MRManager;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.Book;
import com.flyersoft.sdk.tools.LogTools;

import java.util.List;

/**
 * Describe: 主页推荐模块fragment
 * Created by ${zheng.hu} on 2016/10/5.
 */
public class RecommendFragment extends BaseRecommendFragment {

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogTools.showLogH("RecommendFragment onCreate");
        MRManager.getInstance(getActivity()).getActivityBooks(new RequestCallBack<List<Book>>() {
            @Override
            public void onSuccess(List<Book> activityBooks) {
                initActivityBooksView(activityBooks);
            }

            @Override
            public void onFailure(String msg) {
            }
        });
        getData("1");
        getData("2");
        getData("3");
    }
}
