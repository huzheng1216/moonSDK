package com.flyersoft.sdk.widget.user.account;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.flyersoft.sdk.BaseActivity;
import com.flyersoft.sdk.R;
import com.flyersoft.sdk.http.MRManager;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.ChargeRecords;
import com.flyersoft.sdk.tools.ToastTools;
import com.flyersoft.sdk.widget.detail.DetailHeaderLayout;
import com.flyersoft.sdk.widget.tools.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 充值记录
 * Created by zheng.hu on 2016/11/14.
 */
public class RechargeHistoryActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private List<ChargeRecords> data;
    private RechargeHistoryAdapter adapter;

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            adapter.notifyDataSetChanged();
//        }
//    };

    @Override
    protected void initView() {
        setContentView(R.layout.userinfo_recharge_history_main);
    }

    @Override
    protected void initParam() {
        data = new ArrayList<ChargeRecords>(10);
        DetailHeaderLayout detailHeaderLayout = (DetailHeaderLayout) findViewById(R.id.header_layout);
        detailHeaderLayout.setData(R.string.userinfo_recharge_history_title, new DetailHeaderLayout.OnHeaderLayoutListener() {
            @Override
            public void onFinish() {
                RechargeHistoryActivity.this.finish();
            }

            @Override
            public boolean exChangeSelectState(int state) {
                return false;
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.userinfo_recharge_history_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        adapter = new RechargeHistoryAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        MRManager.getInstance(this).getChargeRecords(1, 50, new RequestCallBack<List<ChargeRecords>>() {
            @Override
            public void onSuccess(List<ChargeRecords> chargeRecordses) {
                data.addAll(chargeRecordses);
                adapter.notifyDataSetChanged();
//                handler.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(String msg) {
                ToastTools.showToast(RechargeHistoryActivity.this, "无法获取充值记录");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        data.clear();
    }
}
