package com.flyersoft.sdk.widget.main.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 防止重复inflater
 */
public abstract class BaseFragment extends Fragment {

    protected View contentView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = initView();
        }
        if (contentView != null) {
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (contentView != null && contentView.getParent()!=null)
            ((ViewGroup) contentView.getParent()).removeView(contentView);
        super.onDestroyView();
    }

    public abstract ViewGroup initView();
}
