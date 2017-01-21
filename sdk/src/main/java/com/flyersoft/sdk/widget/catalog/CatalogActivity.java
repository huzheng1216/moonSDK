package com.flyersoft.sdk.widget.catalog;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.flyersoft.sdk.BaseActivity;
import com.flyersoft.sdk.R;
import com.flyersoft.sdk.http.MRManager;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.DetailCatalogDetail;
import com.flyersoft.sdk.tools.LogTools;
import com.flyersoft.sdk.widget.catalog.adapter.DetailCatalogAdapter;
import com.flyersoft.sdk.widget.detail.DetailHeaderLayout;
import com.flyersoft.sdk.widget.detail.ReadActivity;
import com.flyersoft.sdk.widget.tools.DividerItemDecoration;
import com.flyersoft.sdk.widget.tools.OnRcvScrollListener;
import com.flyersoft.sdk.widget.user.AccountData;
import com.flyersoft.sdk.widget.user.landing.LandingPageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 书本目录
 * Created by zheng.hu on 2016/10/12.
 */
public class CatalogActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private DetailCatalogAdapter adapter;

    private List<DetailCatalogDetail> detailCatalogDetails;

    private String bookId;
    private int from = 0;//记录请求位置
    private boolean isLoading = false;
    private boolean hasData = true;//服务器是否还有数据

    @Override
    protected void initView() {
        setContentView(R.layout.catalog_main);
        DetailHeaderLayout detailHeaderLayout = (DetailHeaderLayout) findViewById(R.id.catalog_header_layout);
        detailHeaderLayout.setData(R.string.catalog_top_title, new DetailHeaderLayout.OnHeaderLayoutListener() {
            @Override
            public void onFinish() {
                CatalogActivity.this.finish();
            }

            @Override
            public boolean exChangeSelectState(int state) {
                return false;
            }
        });
    }

    @Override
    protected void initParam() {
        detailCatalogDetails = new ArrayList<>(20);
        recyclerView = (RecyclerView) findViewById(R.id.catalog_category_recyclerview);
        recyclerView.setOnScrollListener(new OnRcvScrollListener() {
            @Override
            public void onBottom() {
                super.onBottom();
                if(!isLoading && hasData){
                    getData();
                }
            }
        });
        adapter = new DetailCatalogAdapter(detailCatalogDetails, new DetailCatalogAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                if (AccountData.getInstance(CatalogActivity.this).getmUserInfo() != null && !AccountData.getInstance(CatalogActivity.this).getmUserInfo().isNeedSignin()) {
                    Intent intent = new Intent(CatalogActivity.this, ReadActivity.class);
                    intent.putExtra("bookId", bookId);
                    intent.putExtra("currentPage", position+1);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(CatalogActivity.this, LandingPageActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    protected void initData() {
        bookId = getIntent().getStringExtra("id");
        getData();
    }

    private void getData() {
        isLoading = true;
        MRManager.getInstance(this).getDetailCategoryBooks(bookId, from, 20, new RequestCallBack<List<DetailCatalogDetail>>() {
            @Override
            public void onSuccess(List<DetailCatalogDetail> detailCatalogDetails) {
                fillData(detailCatalogDetails);
                isLoading = false;
            }

            @Override
            public void onFailure(String msg) {
                isLoading = false;
            }
        });
    }

    private void fillData(List<DetailCatalogDetail> detailCatalogDetails) {

        if (detailCatalogDetails == null || detailCatalogDetails.size() < 1) {
            LogTools.showLogH("没有分类数据。。。。。。");
            return;
        }
        this.detailCatalogDetails.addAll(detailCatalogDetails);
        adapter.notifyDataSetChanged();
        if(detailCatalogDetails.size()<20) hasData = false;
        from += 20;
    }
}
