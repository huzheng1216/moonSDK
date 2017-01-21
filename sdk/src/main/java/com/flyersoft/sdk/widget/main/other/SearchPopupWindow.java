package com.flyersoft.sdk.widget.main.other;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.flyersoft.sdk.R;
import com.flyersoft.sdk.widget.main.adapter.SearchHistoryAdapter;

import java.util.List;

/**
 * 搜索历史弹窗
 * Created by 37399 on 2016/12/19.
 */
public class SearchPopupWindow extends PopupWindow {

    private View conentView;
    private int width;

    private ListView listView;
    private SearchHistoryAdapter adapter;
    private Button button;

    public SearchPopupWindow(Activity context, List<String> data, final SearchPopupWindowListener searchPopupWindowListener){

        conentView = LayoutInflater.from(context).inflate(R.layout.main_search_history_layout, null);
        listView = (ListView) conentView.findViewById(R.id.search_listview);
        adapter = new SearchHistoryAdapter(context, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchPopupWindowListener.onItemClick(position);
            }
        });

        button = (Button) conentView.findViewById(R.id.search_clean_bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchPopupWindowListener!=null){
                    searchPopupWindowListener.cleanHistory();
                }
            }
        });
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        double w = context.getWindowManager().getDefaultDisplay().getWidth();
        width = (int)(w * 0.95);
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(width);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
        this.setOutsideTouchable(false);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimationPreview);
//        LinearLayout addTaskLayout = (LinearLayout) conentView
//                .findViewById(R.id.add_task_layout);
//        LinearLayout teamMemberLayout = (LinearLayout) conentView
//                .findViewById(R.id.team_member_layout);
//        addTaskLayout.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                AddPopWindow.this.dismiss();
//            }
//        });

    }

    public void show(Activity context, View parent){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int xPos = -width / 2 + parent.getWidth() / 2;
        this.showAsDropDown(parent, xPos, -20);
    }

    public void dismiss(){
        super.dismiss();
    }

    /**
     * 更新搜索数据
     */
    public void notifyDataChange() {
        adapter.notifyDataSetChanged();
    }

    public interface SearchPopupWindowListener{
        abstract void cleanHistory();
        abstract void onItemClick(int position);
    }
}
