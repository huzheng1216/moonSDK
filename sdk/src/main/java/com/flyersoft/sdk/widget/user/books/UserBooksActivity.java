package com.flyersoft.sdk.widget.user.books;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.flyersoft.sdk.BaseActivity;
import com.flyersoft.sdk.R;
import com.flyersoft.sdk.http.MRManager;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.ShelfBook;
import com.flyersoft.sdk.tools.LogTools;
import com.flyersoft.sdk.widget.detail.DetailActivity;
import com.flyersoft.sdk.widget.detail.DetailHeaderLayout;
import com.flyersoft.sdk.widget.user.books.adapter.ShelfAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的书架
 * Created by zheng.hu on 2016/11/9.
 */
public class UserBooksActivity extends BaseActivity {

    private GridView gridView;
    private ShelfAdapter adapter;
    private List<ShelfBook> shelfBooks = new ArrayList<>(5);

    @Override
    protected void initView() {
        setContentView(R.layout.userbooks_main);
        gridView = (GridView) findViewById(R.id.userbooks_gridview);
        adapter = new ShelfAdapter(this, shelfBooks);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(UserBooksActivity.this, DetailActivity.class);
                intent.putExtra("id", shelfBooks.get(position).getBookId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initParam() {
        DetailHeaderLayout detailHeaderLayout = (DetailHeaderLayout) findViewById(R.id.userbooks_header_layout);
        detailHeaderLayout.setData(R.string.userbooks_title, new DetailHeaderLayout.OnHeaderLayoutListener() {
            @Override
            public void onFinish() {
                UserBooksActivity.this.finish();
            }

            @Override
            public boolean exChangeSelectState(int state) {
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        MRManager.getInstance(this).getMyShelf(1, 20, new RequestCallBack<List<ShelfBook>>() {
            @Override
            public void onSuccess(List<ShelfBook> shelfBooks) {
                LogTools.showLogH("getMyShelf = "+shelfBooks.size());
                UserBooksActivity.this.shelfBooks.addAll(shelfBooks);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
}
