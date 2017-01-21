package com.flyersoft.sdk.widget.user.buys;

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
import com.flyersoft.sdk.widget.category.listener.EndlessRecyclerOnScrollListener;
import com.flyersoft.sdk.widget.detail.DetailActivity;
import com.flyersoft.sdk.widget.detail.DetailHeaderLayout;
import com.flyersoft.sdk.widget.tools.DividerItemDecoration;
import com.flyersoft.sdk.widget.user.buys.adapter.MyBuyAdapter;

import java.util.List;

/**
 * 我的购买
 * Created by 37399 on 2016/12/16.
 */
public class MyBuyBooksActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private MyBuyAdapter myBuyAdapter;

    private int maxCount = 20;//每次请求的最大数
    private int page = 0;//请求位置


    @Override
    protected void initView() {
        setContentView(R.layout.mybuy_main);
        DetailHeaderLayout detailHeaderLayout = (DetailHeaderLayout) findViewById(R.id.mybuy_header_layout);
        detailHeaderLayout.setData(R.string.catalog_top_title,new DetailHeaderLayout.OnHeaderLayoutListener() {
            @Override
            public void onFinish() {
                MyBuyBooksActivity.this.finish();
            }

            @Override
            public boolean exChangeSelectState(int state) {
                return false;
            }
        });
    }

    @Override
    protected void initParam() {
        recyclerView = (RecyclerView) findViewById(R.id.mybuy_recyclerview);
    }

    @Override
    protected void initData() {
        getData();
    }

    public void getData(){
        MRManager.getInstance(this).getBuyBookRecords(page++ * 20, maxCount, new RequestCallBack<List<BookDetail>>() {
            @Override
            public void onSuccess(List<BookDetail> bookDetails) {
                fillData(bookDetails);
            }
            @Override
            public void onFailure(String msg) {
                ToastTools.showToast(MyBuyBooksActivity.this, R.string.no_data);
            }
        });
    }

    private void fillData(List<BookDetail> bookDetails) {
        if(bookDetails==null || bookDetails.size()<1){
            LogTools.showLogH("没有分类数据。。。。。。");
            return;
        }
        if(myBuyAdapter == null){
            myBuyAdapter = new MyBuyAdapter(bookDetails);
            if(bookDetails.size()<maxCount){
                myBuyAdapter.setFoot(false);
            }
            myBuyAdapter.setOnRecyclerViewListener(new MyBuyAdapter.OnRecyclerViewListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(MyBuyBooksActivity.this, DetailActivity.class);
                    intent.putExtra("id", myBuyAdapter.getData().get(position).getBookId());
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
            recyclerView.setAdapter(myBuyAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int currentPage) {
                    LogTools.showLogH("recyclerView loadMore "+currentPage);
                    if(myBuyAdapter.getData().size() >= maxCount){//小于maxCount说明后台没数据了
                        getData();
                    }
                }
            });
            recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        }else{
            if(bookDetails.size()<maxCount){
                myBuyAdapter.setFoot(false);
            }
            myBuyAdapter.notifyDataSetChanged(bookDetails);
        }
    }
}
