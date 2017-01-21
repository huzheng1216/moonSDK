package com.flyersoft.sdk.widget.main.autoscroll;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flyersoft.sdk.R;
import com.flyersoft.sdk.javabean.Book;
import com.flyersoft.sdk.tools.DensityUtil;
import com.flyersoft.sdk.widget.detail.DetailActivity;

import java.util.List;

/**
 * Describe: 轮播组件
 * Created by ${zheng.hu} on 2016/10/6.
 */
public class AutoScrollLayout extends FrameLayout {

    private int banaSize;
    private LinearLayout selectPointLin;
    private  AutoScrollViewPager autoScrollViewPager;

    public AutoScrollLayout(Context context) {
        super(context);
    }

    public AutoScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        autoScrollViewPager = (AutoScrollViewPager) findViewById(R.id.main_recomment_activitys_autoscrollviewpager);

    }

    public void initAutoScrollViewPager(List<Book> activityBooks){

        ImagePagerAdapter vpAdapter = new ImagePagerAdapter(getContext(), activityBooks);
        vpAdapter.setInfiniteLoop(true);
        autoScrollViewPager.setAdapter(vpAdapter);
        autoScrollViewPager.setInterval(3000);
        autoScrollViewPager.setVisibility(View.VISIBLE);
        vpAdapter.setScenario("0x010100");
        vpAdapter.setOnItemClickListener(new ImagePagerAdapter.OnItemClickListener() {
            @Override
            public void OnClick(Book bookDetail) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("id", bookDetail.getId());
                getContext().startActivity(intent);
            }
        });
        banaSize = activityBooks.size();
        if (banaSize > 0) {
            autoScrollViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2
                    % banaSize);
        }
        autoScrollViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                setPagePointSelect(arg0 % banaSize);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        autoScrollViewPager.startAutoScroll();


        selectPointLin = (LinearLayout) findViewById(R.id.top_page_select_point);
        selectPointLin.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(DensityUtil.dip2px(
                getContext(), 5), DensityUtil.dip2px(getContext(), 5));
        layout.setMargins(DensityUtil.dip2px(getContext(), 2), 0,
                DensityUtil.dip2px(getContext(), 2), 0);

        for (int i = 0; i < banaSize; i++) {
            Drawable drawable = getContext().getResources().getDrawable(
                    R.drawable.main_activity_books_selector);

            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(layout);
            imageView.setId(i);
            imageView.setImageDrawable(drawable);
            selectPointLin.addView(imageView);
        }

        ImageView firstPoint = (ImageView) selectPointLin.findViewById(0);
        firstPoint.setSelected(true);
    }


    private void setPagePointSelect(int currentPostion) {
        if (banaSize < 2 || currentPostion < 0 || currentPostion >= banaSize) {
            return;
        }
        for (int i = 0; i < banaSize; i++) {
            ImageView img = (ImageView) selectPointLin.findViewById(i);
            if (img == null) {
                continue;
            }
            if (currentPostion == i) {
                img.setSelected(true);
            } else {
                img.setSelected(false);
            }
        }
    }
}
