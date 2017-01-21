package com.flyersoft.sdk.widget.detail;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.flyersoft.sdk.BaseActivity;
import com.flyersoft.sdk.R;
import com.flyersoft.sdk.http.MRManager;
import com.flyersoft.sdk.http.body.BaseRequest;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.BookContent;
import com.flyersoft.sdk.javabean.DetailCatalogDetail;
import com.flyersoft.sdk.javabean.account.AmountInfo;
import com.flyersoft.sdk.tools.ToastTools;
import com.flyersoft.sdk.widget.user.account.UserInfoActivity;

import java.util.List;

/**
 * 阅读界面
 * Created by 37399 on 2016/12/13.
 */
public class ReadActivity extends BaseActivity {

    private String bookId;
    private int currentPage;

    private TextView contentV;
    private View needBuy;
    private TextView price;
    private TextView balance;
    private Button affirm;
    private CheckBox checkBox;

    private int bookPrice = -1;
    private int userBalance = -1;


    @Override
    protected void initView() {
        setContentView(R.layout.read_main);
        contentV = (TextView) findViewById(R.id.read_content_tv);
        needBuy = findViewById(R.id.read_content_need_buy_layout);
        price = (TextView) findViewById(R.id.read_content_price3);
        balance = (TextView) findViewById(R.id.read_content_balance2);
        affirm = (Button) findViewById(R.id.read_content_buy_bt);
        checkBox = (CheckBox) findViewById(R.id.read_content_checkbox);
        affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bookPrice==-1 || userBalance==-1) return;

                if(userBalance>bookPrice){
                    affirm.setEnabled(false);
                    MRManager.getInstance(ReadActivity.this).buyBook(bookId, currentPage+"", checkBox.isChecked(), new RequestCallBack<Boolean>() {
                        @Override
                        public void onSuccess(Boolean aBoolean) {
                            //购买成功
                            initData();
                        }

                        @Override
                        public void onFailure(String msg) {
                            //购买失败
                            initButton();
                            ToastTools.showToast(ReadActivity.this, "购买失败");
                        }
                    });
                }else{
                    Intent intent = new Intent(ReadActivity.this, UserInfoActivity.class);
                    ReadActivity.this.startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initParam() {
        bookId = getIntent().getStringExtra("bookId");
        currentPage = getIntent().getIntExtra("currentPage", 1);
    }

    @Override
    protected void initData() {
        MRManager.getInstance(this).getContent(bookId, currentPage, "0", new RequestCallBack<BaseRequest<BookContent>>() {
            @Override
            public void onSuccess(BaseRequest<BookContent> request) {
                if (request.getErrorCode() == 1002) {
                    //需要付费
                    MRManager.getInstance(ReadActivity.this).getDetailCategoryBooks(bookId, currentPage, 1, new RequestCallBack<List<DetailCatalogDetail>>() {
                        @Override
                        public void onSuccess(List<DetailCatalogDetail> detailCatalogDetails) {
                            if(detailCatalogDetails.size()>0){
                                DetailCatalogDetail detailCatalogDetail = detailCatalogDetails.get(0);
                                price.setText(detailCatalogDetail.getPrice()+"书币");
                                bookPrice = detailCatalogDetail.getPrice();
                                initButton();
                            }
                            needBuy.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onFailure(String msg) {
                            ToastTools.showToast(ReadActivity.this, "获取章节失败，请重试");
                        }
                    });
                    MRManager.getInstance(ReadActivity.this).getAmount(new RequestCallBack<AmountInfo>() {
                        @Override
                        public void onSuccess(AmountInfo userInfo) {
                            balance.setText(userInfo.getData()+"书币");
                            userBalance = userInfo.getData();
                            initButton();
                        }
                        @Override
                        public void onFailure(String msg) {
                            affirm.setEnabled(false);
                        }
                    });
                } else {
                    needBuy.setVisibility(View.GONE);
                    contentV.setText(request.getData().getContent());
                }
            }

            @Override
            public void onFailure(String msg) {
                affirm.setEnabled(false);
                ToastTools.showToast(ReadActivity.this, "获取内容失败，请重试");
            }
        });

    }

    private void initButton(){
        if(bookPrice!=-1 && userBalance!=-1){
            affirm.setEnabled(true);
            if(bookPrice>userBalance){
                affirm.setText(R.string.read_buy_not_sufficient_funds);
            }else{
                affirm.setText(R.string.read_buy_current);
            }
        }else{
            affirm.setEnabled(false);
        }
    }
}
