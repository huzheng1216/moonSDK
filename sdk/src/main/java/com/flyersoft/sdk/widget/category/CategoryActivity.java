package com.flyersoft.sdk.widget.category;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.flyersoft.sdk.BaseActivity;
import com.flyersoft.sdk.R;
import com.flyersoft.sdk.http.MRManager;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.BookDetail;
import com.flyersoft.sdk.tools.LogTools;
import com.flyersoft.sdk.tools.ToastTools;
import com.flyersoft.sdk.widget.category.adapter.CategoryAdapter;
import com.flyersoft.sdk.widget.category.listener.EndlessRecyclerOnScrollListener;
import com.flyersoft.sdk.widget.detail.DetailActivity;
import com.flyersoft.sdk.widget.detail.DetailHeaderLayout;
import com.flyersoft.sdk.widget.tools.DividerItemDecoration;

import java.util.List;

/**
 * 分类书籍列表（推荐，热门。。。 / 奇幻，仙侠。。。）
 * Created by zheng.hu on 2016/10/12.
 */
public class CategoryActivity extends BaseActivity {

    public static final int CALL_FROM_MODULE = 1;//从模块进来
    public static final int CALL_FROM_CATEGORY = 2;//从分类进来
    public static final int CALL_FROM_SEARCH = 3;//从搜索进来

    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private int from;
    private String id, key;

    @Override
    protected void initView() {
        setContentView(R.layout.category_main);
        DetailHeaderLayout detailHeaderLayout = (DetailHeaderLayout) findViewById(R.id.catalog_header_layout);
        detailHeaderLayout.setData(R.string.catalog_top_title, new DetailHeaderLayout.OnHeaderLayoutListener() {
            @Override
            public void onFinish() {
                CategoryActivity.this.finish();
            }

            @Override
            public boolean exChangeSelectState(int state) {
                return false;
            }
        });
    }

    @Override
    protected void initParam() {
        recyclerView = (RecyclerView) findViewById(R.id.catalog_category_recyclerview);
    }

    @Override
    protected void initData() {
        from = getIntent().getIntExtra("from", 1);
        id = getIntent().getStringExtra("id");
        key = getIntent().getStringExtra("key");

        getData(from, id, key, 1);
    }

    /**
     * 获取数据
     *
     * @param from
     * @param id
     * @param key
     * @param page
     */
    private void getData(int from, String id, String key, int page) {

        int start = 10 * page - 10;
        int end = 10;

        switch (from) {
            case CALL_FROM_MODULE:
                MRManager.getInstance(this).getModuleBooks(id, start, end, new RequestCallBack<List<BookDetail>>() {
                    @Override
                    public void onSuccess(List<BookDetail> bookDetails) {
                        fillData(bookDetails);
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastTools.showToast(CategoryActivity.this, R.string.no_data);
                    }
                });
                break;
            case CALL_FROM_CATEGORY:
                MRManager.getInstance(this).getSearchBooks("", id, start, end, new RequestCallBack<List<BookDetail>>() {
                    @Override
                    public void onSuccess(List<BookDetail> bookDetails) {
                        fillData(bookDetails);
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastTools.showToast(CategoryActivity.this, R.string.no_data);
                    }
                });
                break;
            case CALL_FROM_SEARCH:
                MRManager.getInstance(this).getSearchBooks(key, "", start, end, new RequestCallBack<List<BookDetail>>() {
                    @Override
                    public void onSuccess(List<BookDetail> bookDetails) {
                        fillData(bookDetails);
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastTools.showToast(CategoryActivity.this, R.string.no_data);
                    }
                });
                break;
        }
    }

    private void fillData(List<BookDetail> bookDetails) {

        if (bookDetails == null || bookDetails.size() < 1) {
            LogTools.showLogH("没有分类数据。。。。。。");
            return;
        }
        if (categoryAdapter == null) {
            categoryAdapter = new CategoryAdapter(bookDetails, CALL_FROM_SEARCH == from, key);
            if (bookDetails.size() < 10) {
                categoryAdapter.setFoot(false);
            }
            categoryAdapter.setOnRecyclerViewListener(new CategoryAdapter.OnRecyclerViewListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(CategoryActivity.this, DetailActivity.class);
                    intent.putExtra("id", categoryAdapter.getData().get(position).getBookId());
                    startActivity(intent);
                }

                @Override
                public boolean onItemLongClick(int position) {
                    return false;
                }

                @Override
                public void onFootViewClick() {

                }
            });
            recyclerView.setAdapter(categoryAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int currentPage) {
                    LogTools.showLogH("recyclerView loadMore " + currentPage);
                    getData(from, id, key, currentPage);
                }
            });
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        } else {
            if (bookDetails.size() < 10) {
                categoryAdapter.setFoot(false);
            }
            categoryAdapter.notifyDataSetChanged(bookDetails);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
