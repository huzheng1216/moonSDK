package com.flyersoft.sdk.widget.main.other;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.flyersoft.sdk.R;


/**
 * 限制了最大高度的layout
 * Created by 37399 on 2016/12/26.
 */
public class MaxHeightLinearLayout extends LinearLayout {

    private int mMaxHeight;

    public MaxHeightLinearLayout(Context context) {
        super(context);
        initMaxHeight(context);
    }

    public MaxHeightLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMaxHeight(context);
    }

    public MaxHeightLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMaxHeight(context);
    }

    public void initMaxHeight(Context context) {
        mMaxHeight = (int) context.getResources().getDimension(R.dimen.search_history_max_list_height);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }

        if (heightMode == MeasureSpec.UNSPECIFIED) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }
        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
                heightMode);
        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec);
    }

    //
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(maxHeight, 0), MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
}
