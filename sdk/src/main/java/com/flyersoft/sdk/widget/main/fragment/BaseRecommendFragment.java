package com.flyersoft.sdk.widget.main.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flyersoft.sdk.R;
import com.flyersoft.sdk.http.MRManager;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.Book;
import com.flyersoft.sdk.javabean.BookDetail;
import com.flyersoft.sdk.tools.LogTools;
import com.flyersoft.sdk.tools.ToastTools;
import com.flyersoft.sdk.widget.category.CategoryActivity;
import com.flyersoft.sdk.widget.detail.DetailActivity;
import com.flyersoft.sdk.widget.main.autoscroll.AutoScrollLayout;

import java.util.List;

/**
 *  推荐/免费/排行 基本fragment
 */
public abstract class BaseRecommendFragment extends BaseFragment implements OnClickListener {


    @Override
    public ViewGroup initView() {
        LogTools.showLogH("initView");
        ViewGroup view = (ViewGroup) LayoutInflater.from(this.getActivity()).inflate(R.layout.main_recomment_fragment, null);
        TextView title1 = (TextView) view.findViewById(R.id.main_recomment_item_include1).findViewById(R.id.main_three_books_item_title);
        title1.setText(R.string.read_recommend);
        TextView title2 = (TextView) view.findViewById(R.id.main_recomment_item_include2).findViewById(R.id.main_three_books_item_title);
        title2.setText(R.string.hot_books);
        TextView title3 = (TextView) view.findViewById(R.id.main_recomment_item_include3).findViewById(R.id.main_three_books_item_title);
        title3.setText(R.string.favorable_books);
        return view;
    }


    /**
     * 初始化图片轮播
     * @param activityBooks
     */
    protected void initActivityBooksView(List<Book> activityBooks) {
        ViewStub viewStub = (ViewStub) contentView.findViewById(R.id.main_recomment_activitys_layout);
        viewStub.inflate();
        AutoScrollLayout autoScrollLayout = (AutoScrollLayout) contentView.findViewById(R.id.autoscrolllayout);
        autoScrollLayout.initAutoScrollViewPager(activityBooks);
    }

    protected void getData(final String id) {
        MRManager.getInstance(getActivity()).getModuleBooks(id, new RequestCallBack<List<BookDetail>>() {
            @Override
            public void onSuccess(List<BookDetail> moduleBooks) {
                fillData(moduleBooks, Integer.parseInt(id));
            }

            @Override
            public void onFailure(String msg) {
                ToastTools.showToast(BaseRecommendFragment.this.getActivity(), msg);
            }
        });
    }

    /**
     * 填充数据
     * @param moduleBooks
     * @param type 填充模块 1:畅读 2:瞩目 3:优惠
     */
    private void fillData(List<BookDetail> moduleBooks, int type){

        View view = null;
        switch(type){
            case 1:
                view = contentView.findViewById(R.id.main_recomment_item_include1);
                break;
            case 2:
                view = contentView.findViewById(R.id.main_recomment_item_include2);
                break;
            case 3:
                view = contentView.findViewById(R.id.main_recomment_item_include3);
                break;
            case 4:
                view = contentView.findViewById(R.id.main_recomment_item_include1);
                break;
            case 5:
                view = contentView.findViewById(R.id.main_recomment_item_include2);
                break;
            case 6:
                view = contentView.findViewById(R.id.main_recomment_item_include3);
                break;
            case 7:
                view = contentView.findViewById(R.id.main_recomment_item_include1);
                break;
            case 8:
                view = contentView.findViewById(R.id.main_recomment_item_include2);
                break;
            case 9:
                view = contentView.findViewById(R.id.main_recomment_item_include3);
                break;
        }
        ViewStub viewStub = (ViewStub) view.findViewById(R.id.main_three_books_item_stub);
        viewStub.inflate();

        List<BookDetail> data = moduleBooks;
        View layout1 =  view.findViewById(R.id.main_three_books_item_1_layout);
        layout1.setTag(data.get(0));
        View more =  view.findViewById(R.id.main_three_books_item_more);
        more.setTag(type+"");
        SimpleDraweeView img1 = (SimpleDraweeView) view.findViewById(R.id.main_three_books_item_1_img);
        TextView title1 = (TextView) view.findViewById(R.id.main_three_books_item_1_title);
        TextView dec1 = (TextView) view.findViewById(R.id.main_three_books_item_1_dec);
        img1.setImageURI(data.get(0).getMidImage());
        title1.setText(data.get(0).getBookName());
        dec1.setText(data.get(0).getAuthor());

        View layout2 =  view.findViewById(R.id.main_three_books_item_2_layout);
        layout2.setTag(data.get(1));
        SimpleDraweeView img2 = (SimpleDraweeView) view.findViewById(R.id.main_three_books_item_2_img);
        TextView title2 = (TextView) view.findViewById(R.id.main_three_books_item_2_title);
        TextView dec2 = (TextView) view.findViewById(R.id.main_three_books_item_2_dec);
        img2.setImageURI(data.get(1).getMidImage());
        title2.setText(data.get(1).getBookName());
        dec2.setText(data.get(1).getAuthor());

        View layout3 =  view.findViewById(R.id.main_three_books_item_3_layout);
        layout3.setTag(data.get(2));
        SimpleDraweeView img3 = (SimpleDraweeView) view.findViewById(R.id.main_three_books_item_3_img);
        TextView title3 = (TextView) view.findViewById(R.id.main_three_books_item_3_title);
        TextView dec3 = (TextView) view.findViewById(R.id.main_three_books_item_3_dec);
        img3.setImageURI(data.get(2).getMidImage());
        title3.setText(data.get(2).getBookName());
        dec3.setText(data.get(2).getAuthor());

        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
        layout3.setOnClickListener(this);
        more.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.main_three_books_item_1_layout || id == R.id.main_three_books_item_2_layout || id == R.id.main_three_books_item_3_layout) {
            BookDetail bookDetail = (BookDetail) v.getTag();
            onItemClick(bookDetail);

        } else if (id == R.id.main_three_books_item_more) {
            goCategory((String) v.getTag());

        }

    }

    /**
     * 跳转分类目录
     * @param tag
     */
    private void goCategory(String tag){
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        intent.putExtra("from",CategoryActivity.CALL_FROM_MODULE);
        intent.putExtra("id", tag);
        getActivity().startActivity(intent);
    };

    /**
     * 点击跳转二级页
     * @param bookDetail
     */
    protected void onItemClick(BookDetail bookDetail){

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("id", bookDetail.getBookId());
        getActivity().startActivity(intent);
    };

    /**
     * 切换到其他fragment
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 退出
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
