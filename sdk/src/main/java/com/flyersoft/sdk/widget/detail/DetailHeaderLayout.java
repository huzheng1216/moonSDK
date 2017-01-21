package com.flyersoft.sdk.widget.detail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyersoft.sdk.R;

/**
 * 详情页头部控件
 * Created by zheng.hu on 2016/10/9.
 */
public class DetailHeaderLayout extends FrameLayout {

    public static final int SELECT_STATE_ALL = 1;//全选
    public static final int SELECT_STATE_NONE = 2;//全不选
    private int currentSelectState = 0;


    private OnHeaderLayoutListener onHeaderLayoutListener;
    private TextView title;
    private TextView selectAll;

    public DetailHeaderLayout(Context context) {
        super(context);
    }

    public DetailHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DetailHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.detail_header_layout, this);
        ImageView back = (ImageView) inflate.findViewById(R.id.detail_header_back_img);
        title = (TextView) inflate.findViewById(R.id.detail_header_title);
        selectAll = (TextView) inflate.findViewById(R.id.detail_header_select_all);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onHeaderLayoutListener.onFinish();
            }
        });
        selectAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSelectState == SELECT_STATE_ALL && onHeaderLayoutListener.exChangeSelectState(SELECT_STATE_ALL)) {
                    selectAll.setText(R.string.buy_books_chose_none);
                    currentSelectState = SELECT_STATE_NONE;
                } else if (currentSelectState == SELECT_STATE_NONE && onHeaderLayoutListener.exChangeSelectState(SELECT_STATE_NONE)) {
                    selectAll.setText(R.string.buy_books_chose_all);
                    currentSelectState = SELECT_STATE_ALL;
                }
            }
        });
    }

    public interface OnHeaderLayoutListener {
        public abstract void onFinish();

        public abstract boolean exChangeSelectState(int state);
    }

    /**
     * 初始化頂部欄
     *
     * @param name
     * @param onHeaderLayoutListener
     */
    public void setData(int name, OnHeaderLayoutListener onHeaderLayoutListener) {
        this.onHeaderLayoutListener = onHeaderLayoutListener;
        title.setText(name);
    }

    /**
     * 改变全选状态
     *
     * @param state
     */
    public void exchangeSelectAll(int state) {
        selectAll.setVisibility(View.VISIBLE);
        if (SELECT_STATE_ALL == state) {
            currentSelectState = SELECT_STATE_ALL;
            selectAll.setText(R.string.buy_books_chose_all);
        } else if (SELECT_STATE_NONE == state) {
            currentSelectState = SELECT_STATE_NONE;
            selectAll.setText(R.string.buy_books_chose_none);
        } else {
            selectAll.setVisibility(View.GONE);
        }
    }
}
