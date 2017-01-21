package com.flyersoft.sdk.widget.detail;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.flyersoft.sdk.R;
import com.flyersoft.sdk.config.Const;
import com.flyersoft.sdk.http.MRManager;
import com.flyersoft.sdk.http.body.BaseRequest;
import com.flyersoft.sdk.http.body.PayBody;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.DefaultInfo;
import com.flyersoft.sdk.javabean.DetailCatalogDetail;
import com.flyersoft.sdk.javabean.account.AmountInfo;
import com.flyersoft.sdk.javabean.account.PayConfig;
import com.flyersoft.sdk.tools.LogTools;
import com.flyersoft.sdk.tools.ToastTools;
import com.flyersoft.sdk.widget.detail.adapter.BookBuyAdapter;
import com.flyersoft.sdk.widget.user.account.PayResult;
import com.flyersoft.sdk.widget.user.account.RechargeChoseDialog;
import com.flyersoft.sdk.widget.user.account.RechargeDialog;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 购买书籍界面
 */
public class BuyActivity extends Activity {

    private static final String TAG = "BuyActivity";

    private DetailHeaderLayout detailHeaderLayout;

    private String bookid;
    private int count = 0;//记录请求次数
    private boolean hasData = true;//服务器是否还有数据
    private boolean isLoading = false;

    private ExpandableListView listview;
    private BookBuyAdapter adapter;
    private List<DetailCatalogDetail> datas;


    private TextView pageCount;
    private TextView price;
    private TextView priceOld;
    private TextView balance;
    private Button affirm;

    //总价格/余额
    private int counts = 0;
    private int balanceCount = 0;


    //充值
    private RechargeDialog rechargeDialog;
    private RechargeChoseDialog rechargeChoseDialog;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what){
                case 1:
                    int checked = adapter.getChecked().size();
                    int count = adapter.getDataCount();
                    pageCount.setText(checked+"");
                    price.setText(counts+"书币");
                    if(balanceCount>counts){
                        affirm.setText(R.string.buy_books_affirm);
                    }else{
                        affirm.setText(R.string.buy_books_not_sufficient_funds);
                    }
                    if(checked==0){
                        affirm.setEnabled(false);
                    }else{
                        affirm.setEnabled(true);
                    }
                    if(checked==count){
                        detailHeaderLayout.exchangeSelectAll(DetailHeaderLayout.SELECT_STATE_NONE);
                    }else{
                        detailHeaderLayout.exchangeSelectAll(DetailHeaderLayout.SELECT_STATE_ALL);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initParam();
        initData();
    }

    protected void initView() {
        setContentView(R.layout.buy_main);
        detailHeaderLayout = (DetailHeaderLayout) findViewById(R.id.buy_header_layout);
        detailHeaderLayout.setData(R.string.buy_books_title,new DetailHeaderLayout.OnHeaderLayoutListener() {
            @Override
            public void onFinish() {
                BuyActivity.this.finish();
            }

            @Override
            public boolean exChangeSelectState(int state) {
                if(adapter==null){
                    return false;
                }
                counts = 0;
                if(state==DetailHeaderLayout.SELECT_STATE_ALL){
                    return adapter.selectAll();
                }else if(state==DetailHeaderLayout.SELECT_STATE_NONE){
                    return adapter.selectNone();
                }
                return false;
            }
        });
        detailHeaderLayout.exchangeSelectAll(DetailHeaderLayout.SELECT_STATE_ALL);

        listview = (ExpandableListView) findViewById(R.id.buy_category_expandablelistview);
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                            initData();
                        }
                        break;
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }
        });
        pageCount = (TextView) findViewById(R.id.buy_price_info_chose2);
        price = (TextView) findViewById(R.id.buy_price_info_price3);
        priceOld = (TextView) findViewById(R.id.buy_price_info_price2);
        balance = (TextView) findViewById(R.id.buy_price_info_balance2);
        affirm = (Button) findViewById(R.id.buy_price_affirm_bt);
        affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(balanceCount>counts){
                    //购买章节
                    affirm.setText("支付中....");
                    affirm.setEnabled(false);
                    StringBuilder stringBuilder = new StringBuilder();
                    for(String id : adapter.getChecked()){
                        stringBuilder.append(id).append(",");
                    }
                    MRManager.getInstance(BuyActivity.this).buyBook(bookid, stringBuilder.toString(), false, new RequestCallBack<Boolean>() {
                        @Override
                        public void onSuccess(Boolean aBoolean) {
                            //购买成功
                            ToastTools.showToast(BuyActivity.this, "购买成功");
                            BuyActivity.this.finish();
                        }

                        @Override
                        public void onFailure(String msg) {
                            //购买失败
                            ToastTools.showToast(BuyActivity.this, "购买失败");
                            affirm.setText("确认购买");
                            affirm.setEnabled(true);
                        }
                    });
                }else{
                    recharge();
//                    ToastTools.showToast(BuyActivity.this, R.string.buy_books_not_sufficient_funds);
                }
            }
        });
        initBalance();
    }

    private void initBalance() {
        MRManager.getInstance(this).getAmount(new RequestCallBack<AmountInfo>() {
            @Override
            public void onSuccess(AmountInfo userInfo) {
                balanceCount = userInfo.getData();
                balance.setText(userInfo.getData()+"书币");
            }
            @Override
            public void onFailure(String msg) {
            }
        });
    }

    protected void initParam() {
        listview.setGroupIndicator(null);
        adapter = new BookBuyAdapter(this, new BookBuyAdapter.OnCountChangeListener() {
            @Override
            public void onCountChange(int count) {
                BuyActivity.this.counts += count;
                handler.sendEmptyMessage(1);
            }
        });
        listview.setAdapter(adapter);
        datas = new ArrayList<>(10);
        bookid = getIntent().getStringExtra("id");
    }

    protected void initData() {
        if(isLoading || !hasData){
            return;
        }
        isLoading = true;
        MRManager.getInstance(this).getDetailCategoryBooks(bookid, count*200, 200, new RequestCallBack<List<DetailCatalogDetail>>() {
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
        if(detailCatalogDetails.size()>=200){
            count++;
        }else{
            hasData = false;
        }
        datas.addAll(detailCatalogDetails);
        adapter.putData(datas);
        adapter.notifyDataSetChanged();
    }


    //去充值
    public void recharge() {
        rechargeDialog = new RechargeDialog(this, new RechargeDialog.RechargeListener() {
            @Override
            public void recharge(int count) {
                rechargeDialog.dismiss();
                choseRechargeType(count);
            }
        });
        rechargeDialog.show();
    }


    //选择充值类型
    private void choseRechargeType(int count) {
        rechargeChoseDialog = new RechargeChoseDialog(this, count, new RechargeChoseDialog.RechargeListener() {
            @Override
            public void rechargeWX(int count) {
                rechargeChoseDialog.dismiss();
                wxPay(count);
            }

            @Override
            public void rechargeZFB(int count) {
                rechargeChoseDialog.dismiss();
                zfbPay(count);
            }
        });
        rechargeChoseDialog.show();
    }


    /**
     * 微信支付
     *
     * @param count
     */
    private void wxPay(int count) {

        MRManager.getInstance(this).getWXPayInfo(new PayBody(Const.ACCOUNTTYPE_WEIXIN, count*100))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<BaseRequest<PayConfig>, Observable<PayConfig>>() {
                    @Override
                    public Observable<PayConfig> call(BaseRequest<PayConfig> userInfoBaseRequest) {
                        return Observable.just(userInfoBaseRequest.getData());
                    }
                })
                .subscribe(new Subscriber<PayConfig>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogTools.showLog(LogTools.TAG_ACCOUNT, "onError = " + e.toString());
                    }

                    @Override
                    public void onNext(PayConfig payConfig) {
                        IWXAPI api = WXAPIFactory.createWXAPI(BuyActivity.this, payConfig.getAppid(), true);
                        PayReq request = new PayReq();
                        request.appId = payConfig.getAppid();
                        request.partnerId = payConfig.getPartnerid();
                        request.prepayId = payConfig.getPrepayid();
                        request.packageValue = "Sign=WXPay";
                        request.nonceStr = payConfig.getNoncestr();
                        request.timeStamp = payConfig.getTimestamp();
                        request.sign = payConfig.getSign();
                        api.sendReq(request);
                    }
                });
    }

    /**
     * 支付宝支付
     *
     * @param count
     */
    private void zfbPay(int count) {

        MRManager.getInstance(this).getZFBPayInfo(new PayBody(Const.ACCOUNTTYPE_ALIPAY, count*100))
                .observeOn(Schedulers.newThread())
                .flatMap(new Func1<DefaultInfo, Observable<Map<String, String>>>() {
                    @Override
                    public Observable<Map<String, String>> call(DefaultInfo defaultInfo) {

                        LogTools.showLog(LogTools.TAG_ACCOUNT, "defaultInfo.getData() = " + defaultInfo.getData());
                        PayTask alipay = new PayTask(BuyActivity.this);
                        Map<String, String> result = alipay.payV2(defaultInfo.getData(),true);
                        return Observable.just(result);
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Map<String, String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogTools.showLog(LogTools.TAG_ACCOUNT, "onError = " + e.toString());
                    }

                    @Override
                    public void onNext(Map<String, String> result) {

                        LogTools.showLog(LogTools.TAG_ACCOUNT, "onNext = " + result.toString());
                        @SuppressWarnings("unchecked")
                        PayResult payResult = new PayResult(result);
                        /**
                         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            ToastTools.showToast(BuyActivity.this, "支付成功。");
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            ToastTools.showToast(BuyActivity.this, "支付异常。");
                        }
                        initBalance();
                    }
                });
    }

}
