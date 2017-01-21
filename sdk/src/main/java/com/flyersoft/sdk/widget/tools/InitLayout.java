package com.flyersoft.sdk.widget.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.flyersoft.sdk.R;

/**
 * 自定义界面初始化控件
 * Created by 37399 on 2016/12/14.
 */
public class InitLayout extends RelativeLayout {

    private ProgressBar progressBar;

    public InitLayout(Context context) {
        super(context);
    }

    public InitLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InitLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        progressBar = (ProgressBar) findViewById(R.id.init_state_progressBar);
    }
}
