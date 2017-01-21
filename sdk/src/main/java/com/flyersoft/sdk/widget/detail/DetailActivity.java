package com.flyersoft.sdk.widget.detail;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flyersoft.sdk.BaseActivity;
import com.flyersoft.sdk.R;
import com.flyersoft.sdk.http.MRManager;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.BookDetail;
import com.flyersoft.sdk.javabean.DefaultCode;
import com.flyersoft.sdk.javabean.DetailBookInfo;
import com.flyersoft.sdk.tools.ToastTools;
import com.flyersoft.sdk.widget.catalog.CatalogActivity;
import com.flyersoft.sdk.widget.user.AccountData;
import com.flyersoft.sdk.widget.user.landing.LandingPageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 二级界面
 */
public class DetailActivity extends BaseActivity {

    private DetailBookInfo bookDetail;

    private ScrollView scrollView;

    private Button buy;
    private Button free;
    private Button add;

    private List<BookDetail> recomment;//相关推荐数据
    private boolean isLoadingRcomment = false;
    private int countRecommtn = 0;

    @Override
    protected void initView() {
        setContentView(R.layout.detail_main);
        buy = (Button) findViewById(R.id.detail_bottom_buy_bt);
        free = (Button) findViewById(R.id.detail_bottom_free_bt);
        add = (Button) findViewById(R.id.detail_bottom_add_bt);
        scrollView = (ScrollView) findViewById(R.id.detail_main_scrollview);
    }

    @Override
    protected void initParam() {
        recomment = new ArrayList<>(10);
        DetailHeaderLayout detailHeaderLayout = (DetailHeaderLayout) findViewById(R.id.detail_header_layout);
        detailHeaderLayout.setData(R.string.detail_header_title, new DetailHeaderLayout.OnHeaderLayoutListener() {
            @Override
            public void onFinish() {
                DetailActivity.this.finish();
            }

            @Override
            public boolean exChangeSelectState(int state) {
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        MRManager.getInstance(this).getDetailBook(getIntent().getStringExtra("id"), new RequestCallBack<DetailBookInfo>() {
            @Override
            public void onSuccess(DetailBookInfo bookDetail) {
                fillData(bookDetail);
            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }

    private void fillData(final DetailBookInfo bookDetail) {

        this.bookDetail = bookDetail;

        //封面图片
        SimpleDraweeView img = (SimpleDraweeView) findViewById(R.id.detail_content_imag);
        img.setImageURI(bookDetail.getMidImage());
        //书名
        TextView name = (TextView) findViewById(R.id.detail_content_title);
        name.setText(bookDetail.getBookName());
        //作者
        TextView auther = (TextView) findViewById(R.id.detail_content_auther);
        auther.setText(bookDetail.getAuthor());
        //字数
        TextView count = (TextView) findViewById(R.id.detail_content_count);
        count.setText(bookDetail.getSize() + "字");
        //分类
        TextView type = (TextView) findViewById(R.id.detail_content_type);
        type.setText(bookDetail.getFeeType());
        //内容简介
        TextView doc = (TextView) findViewById(R.id.detail_content_dec);
        doc.setText(bookDetail.getBrief());
        //连载
        TextView serialize = (TextView) findViewById(R.id.detail_content_serialize);
        serialize.setText("连载至 " + bookDetail.getLeastCharpterTitle());
        //更新于
//        TextView update = (TextView) findViewById(R.id.detail_content_update);
//        update.setText("更新于 "+bookDetail.getUpdatetime());

        initRecomment(bookDetail);
        findViewById(R.id.detail_recommend_change_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exchangeRecomment(bookDetail);
            }
        });
        findViewById(R.id.detail_content_view_catalog_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCatalog(bookDetail);
            }
        });
        MRManager.getInstance(this).ifInSelf(bookDetail.getBookId(), new RequestCallBack<DefaultCode>() {
            @Override
            public void onSuccess(DefaultCode defaultCode) {
                add.setEnabled(false);
            }

            @Override
            public void onFailure(String msg) {
                add.setEnabled(true);
            }
        });
        free.setEnabled(true);
        buy.setEnabled(true);
    }

    private void goCatalog(DetailBookInfo bookDetail) {
        Intent intent = new Intent(this, CatalogActivity.class);
        intent.putExtra("id", bookDetail.getBookId());
        this.startActivity(intent);
    }

    private void initRecomment(DetailBookInfo bookDetail) {
        isLoadingRcomment = true;
        MRManager.getInstance(this).getSearchBooks("", bookDetail.getCategoryId(), countRecommtn, 20, new RequestCallBack<List<BookDetail>>() {
            @Override
            public void onSuccess(List<BookDetail> moduleBooks) {
                recomment.addAll(moduleBooks);
                fillRecommendData();
                countRecommtn = 20;
                isLoadingRcomment = false;
            }

            @Override
            public void onFailure(String msg) {
                isLoadingRcomment = false;
            }
        });

    }

    private void fillRecommendData() {

        if (recomment.size() < 3) {
            return;
        }

        //相关阅读
        final BookDetail book1 = recomment.remove(0);
        findViewById(R.id.detail_three_books_item_1_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecomment(book1);
            }
        });
        SimpleDraweeView img1 = (SimpleDraweeView) findViewById(R.id.detail_three_books_item_1_img);
        img1.setImageURI(book1.getMidImage());
        TextView name1 = (TextView) findViewById(R.id.detail_three_books_item_1_title);
        name1.setText(book1.getBookName());
        TextView auther1 = (TextView) findViewById(R.id.detail_three_books_item_1_dec);
        auther1.setText(book1.getAuthor());

        final BookDetail book2 = recomment.remove(0);
        findViewById(R.id.detail_three_books_item_2_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecomment(book2);
            }
        });
        SimpleDraweeView img2 = (SimpleDraweeView) findViewById(R.id.detail_three_books_item_2_img);
        img2.setImageURI(book2.getMidImage());
        TextView name2 = (TextView) findViewById(R.id.detail_three_books_item_2_title);
        name2.setText(book2.getBookName());
        TextView auther2 = (TextView) findViewById(R.id.detail_three_books_item_2_dec);
        auther2.setText(book2.getAuthor());

        final BookDetail book3 = recomment.remove(0);
        findViewById(R.id.detail_three_books_item_3_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecomment(book3);
            }
        });
        SimpleDraweeView img3 = (SimpleDraweeView) findViewById(R.id.detail_three_books_item_3_img);
        img3.setImageURI(book3.getMidImage());
        TextView name3 = (TextView) findViewById(R.id.detail_three_books_item_3_title);
        name3.setText(book3.getBookName());
        TextView auther3 = (TextView) findViewById(R.id.detail_three_books_item_3_dec);
        auther3.setText(book3.getAuthor());

    }

    /**
     * 相关推荐跳转
     *
     * @param book3
     */
    private void openRecomment(BookDetail book3) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", book3.getBookId());
        startActivity(intent);
    }

    /**
     * 更换相关推荐
     *
     * @param bookDetail
     */
    private void exchangeRecomment(DetailBookInfo bookDetail) {
        if (recomment.size() < 3) {
            if (!isLoadingRcomment) {
                initRecomment(bookDetail);
            }
        } else {
            fillRecommendData();
        }
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    //购买书籍
    public void buy(View view) {
        if (AccountData.getInstance(this).getmUserInfo() != null && !AccountData.getInstance(this).getmUserInfo().isNeedSignin()) {
            Intent intent = new Intent(this, BuyActivity.class);
            intent.putExtra("id", bookDetail.getBookId());
            startActivity(intent);
        } else {
            goSignIn();
        }
    }

    //免费阅读
    public void free(View view) {
        if (AccountData.getInstance(this).getmUserInfo() != null && !AccountData.getInstance(this).getmUserInfo().isNeedSignin()) {
            Intent intent = new Intent(this, ReadActivity.class);
            intent.putExtra("bookId", bookDetail.getBookId());
            intent.putExtra("currentPage", 1);
            startActivity(intent);
        } else {
            goSignIn();
        }
    }

    //添加到书库
    public void add(View view) {
        if (AccountData.getInstance(this).getmUserInfo() != null && !AccountData.getInstance(this).getmUserInfo().isNeedSignin()) {
            MRManager.getInstance(this).addToSelf(bookDetail.getBookId(), new RequestCallBack<BookDetail>() {
                @Override
                public void onSuccess(BookDetail bookDetail) {
                    add.setEnabled(false);
                    ToastTools.showToast(DetailActivity.this, "添加成功");
                }

                @Override
                public void onFailure(String msg) {
                    ToastTools.showToast(DetailActivity.this, "添加失败");
                }
            });
        } else {
            goSignIn();
        }
    }


    private void goSignIn() {
        Intent intent = new Intent(this, LandingPageActivity.class);
        startActivity(intent);
    }

}
