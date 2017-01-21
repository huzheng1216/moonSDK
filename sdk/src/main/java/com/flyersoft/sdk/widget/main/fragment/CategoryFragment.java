package com.flyersoft.sdk.widget.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.flyersoft.sdk.R;
import com.flyersoft.sdk.http.MRManager;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.CatalogDetail;
import com.flyersoft.sdk.tools.LogTools;
import com.flyersoft.sdk.widget.category.CategoryActivity;
import com.flyersoft.sdk.widget.main.adapter.MainCategoryAdapter;
import com.flyersoft.sdk.widget.tools.DividerItemDecoration;

import java.util.List;

/**
 * Describe: 分类列表fragment
 * Created by ${zheng.hu} on 2016/10/5.
 */
public class CategoryFragment extends BaseFragment {

    private RecyclerView recyclerView;

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogTools.showLogH("CategoryFragment onCreate");
        MRManager.getInstance(getActivity()).getCategoryBooks(new RequestCallBack<List<CatalogDetail>>() {
            @Override
            public void onSuccess(List<CatalogDetail> catalogs) {
                fillData(catalogs);
            }
            @Override
            public void onFailure(String msg) {
            }
        });
    }

    @Override
    public ViewGroup initView() {
        ViewGroup view = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.main_category_fragment, null);
        recyclerView = (RecyclerView)view.findViewById(R.id.main_category_recyclerview);
        return view;
    }

    private void fillData(final List<CatalogDetail> catalogs) {

        if(catalogs==null){
            LogTools.showLogH("没有分类数据。。。。。。");
            return;
        }
        recyclerView.setAdapter(new MainCategoryAdapter(catalogs, new MainCategoryAdapter.OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("from",CategoryActivity.CALL_FROM_CATEGORY);
                intent.putExtra("id", catalogs.get(position).getCategoryId());
                getActivity().startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        }));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
    }


}
