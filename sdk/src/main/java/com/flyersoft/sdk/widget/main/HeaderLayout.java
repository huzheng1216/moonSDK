package com.flyersoft.sdk.widget.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyersoft.sdk.R;
import com.flyersoft.sdk.tools.StringTools;
import com.flyersoft.sdk.tools.ToastTools;
import com.flyersoft.sdk.tools.Tools;
import com.flyersoft.sdk.widget.category.CategoryActivity;
import com.flyersoft.sdk.widget.main.other.SearchPopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe: 自定义头部控件
 * Created by ${zheng.hu} on 2016/10/7.
 */
public class HeaderLayout extends FrameLayout implements View.OnClickListener {

    private static final String key = "search_history";
    private View inflate;
    private TextView prompt;
    private View searchEdit;
    private View searchImg;
    private EditText et;
    private ImageView etDel;
    private ImageView menu;
    private SearchPopupWindow searchPopupWindow;

    private List<String> searchHistory;
    private String historyStr;

    private OnHeaderViewListener onHeaderViewListener;

    public void setOnHeaderViewListener(OnHeaderViewListener onHeaderViewListener) {
        this.onHeaderViewListener = onHeaderViewListener;
    }

    public HeaderLayout(Context context) {
        super(context);
    }

    public HeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        searchHistory = getHistory(getContext());
        inflate = LayoutInflater.from(getContext()).inflate(R.layout.main_activity_header_layout, this);
        prompt = (TextView) inflate.findViewById(R.id.main_header_prompt);
        searchEdit = inflate.findViewById(R.id.main_header_search_edittext_layout);
        searchImg = inflate.findViewById(R.id.main_header_search_icon);
        et = (EditText) inflate.findViewById(R.id.main_header_search_edittext);
        etDel = (ImageView) inflate.findViewById(R.id.main_header_search_imput_del);
        menu = (ImageView) inflate.findViewById(R.id.main_header_left_menu);

        searchImg.setOnClickListener(this);
        etDel.setOnClickListener(this);
        menu.setOnClickListener(this);

        searchPopupWindow = new SearchPopupWindow((Activity) getContext(), searchHistory, new SearchPopupWindow.SearchPopupWindowListener() {
            @Override
            public void cleanHistory() {
                searchHistory.clear();
                historyStr = "";
                Tools.setInformain(key, "", getContext());
                searchPopupWindow.dismiss();
            }

            @Override
            public void onItemClick(int position) {
                search(searchHistory.get(position));
            }
        });
        et.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //展示搜索历史
                if (searchHistory.size() > 0) {
                    if (hasFocus) {
                        searchPopupWindow.show((Activity) HeaderLayout.this.getContext(), inflate);
                    } else {
                        searchPopupWindow.dismiss();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.main_header_search_icon) {
            if (prompt.getVisibility() == View.VISIBLE) {
                prompt.setVisibility(View.GONE);
                searchEdit.setVisibility(View.VISIBLE);
            } else {
                String s = et.getText().toString();
                if (s != null && !s.isEmpty() && !";".equals(s)) {
                    search(s);
                } else {
                    ToastTools.showToast(getContext(), R.string.string_is_empty);
                }
            }

        } else if (id == R.id.main_header_search_edittext) {
        } else if (id == R.id.main_header_left_menu) {
            if (onHeaderViewListener != null) {
                onHeaderViewListener.open();
            }

        } else if (id == R.id.main_header_search_imput_del) {
            et.setText("");
            searchEdit.setVisibility(View.GONE);
            prompt.setVisibility(View.VISIBLE);

        }
    }

    private void search(String s) {
        if (searchHistory.contains(s)) {
            searchHistory.remove(s);
            historyStr = historyStr.replace(s + ";","");
        }
        searchHistory.add(0,s);
        historyStr = s + ";" + historyStr;
        Tools.setInformain(key, historyStr, getContext());
        searchPopupWindow.notifyDataChange();

        //跳转到分类列表
        Intent intent = new Intent(getContext(), CategoryActivity.class);
        intent.putExtra("from", CategoryActivity.CALL_FROM_SEARCH);
        intent.putExtra("key", s);
        getContext().startActivity(intent);
    }

    public void onTabCheckedChange(String promptStr) {
        prompt.setText(promptStr);
        et.setText("");
        searchEdit.setVisibility(View.GONE);
        prompt.setVisibility(View.VISIBLE);
    }

    public interface OnHeaderViewListener {
        void open();
    }

    public List<String> getHistory(Context context) {
        historyStr = Tools.getInformain(key, "", context);
        String[] split = {};
        if (StringTools.isNotEmpty(historyStr)) {
            split = historyStr.split(";");
        }
        ArrayList<String> list = new ArrayList<>(5);
        for (String h : split) {
            if(list.contains(h)){
                continue;
            }
            list.add(h);
        }
        return list;
    }
}
