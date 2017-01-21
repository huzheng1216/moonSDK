package com.flyersoft.sdk.widget.main.fragment;

import android.os.Bundle;

import com.flyersoft.sdk.tools.LogTools;

/**
 * Describe:
 * Created by ${zheng.hu} on 2016/10/5.
 */
public class RankingsFragment extends BaseRecommendFragment {
    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogTools.showLogH("RankingsFragment onCreate");
        getData("7");
        getData("8");
        getData("9");
    }
}
