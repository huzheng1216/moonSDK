/*
 * Copyright 2014 trinea.cn All right reserved. This software is the
 * confidential and proprietary information of trinea.cn
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with trinea.cn.
 */
package com.flyersoft.sdk.widget.main.autoscroll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flyersoft.sdk.R;
import com.flyersoft.sdk.javabean.Book;

import java.util.List;

/**
 * ImagePagerAdapter
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {

	private Context mcontext;
	private List<Book> activityBooks;
	private LayoutInflater mInflater;
	private int size;
	private boolean isInfiniteLoop;
	private String scenario;
	private OnItemClickListener onItemClickListener;


	public ImagePagerAdapter(Context context, List<Book> activityBooks) {
		this.mcontext = context;
		this.mInflater = LayoutInflater.from(mcontext);
		this.activityBooks = activityBooks;
		this.size = activityBooks.size();
		isInfiniteLoop = false;
	}

//	public void setDataChange(ArrayList<ZZNewsinfo> headerflowNewsList) {
//		this.mheaderflowNewsList = headerflowNewsList;
//		this.size = mheaderflowNewsList.size();
//		super.notifyDataSetChanged();
//	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	@Override
	public int getCount() {
		return isInfiniteLoop ? Integer.MAX_VALUE : activityBooks.size();
	}

	/**
	 * get really position
	 * 
	 * @param position
	 * @return
	 */
	private int getPosition(int position) {
		if (position == 0) {
			return 0;
		}
		return isInfiniteLoop ? position % size : position;
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public View getView(final int position, View view, ViewGroup container) {
		ViewHolder holder;
		final Book bookDetail = activityBooks.get(getPosition(position));
		if (view == null) {
			holder = new ViewHolder();
			view = mInflater.inflate(R.layout.main_activity_books_item_layout, container,
					false);
			holder.imageView = (SimpleDraweeView) view.findViewById(R.id.main_recomment_activitys_item_img);
//			holder.textview = (TextView) view.findViewById(R.id.fiflow_title_id);
//			holder.adView = (TextView) view.findViewById(R.id.fiflow_ad_id);
//			holder.marginleftView = view.findViewById(R.id.marginleft);
			view.setTag(holder);
//			if (flowNewsBlockList != null){ // 轮播添加数据统计
//				XZReportAgent.onItemShow(scenario, flowNewsBlockList.getContent_id(),flowNewsBlockList.getCpack(), StringUtils.strToLong(flowNewsBlockList.getServer_time()), null, null);
//			}
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(onItemClickListener!=null){
						onItemClickListener.OnClick(bookDetail);
					}
				}
			});
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.imageView.setImageURI(bookDetail.getImgUrl());



//		if (flowNewsBlockList.getAdObject() != null) { // 广告条目
//			final FlowAd flowNewsinfo = (FlowAd) mheaderflowNewsList.get(getPosition(position)).getAdObject();
//			// 利用Glide加载图片
//			GlideImageLoader.loadImage(mcontext, holder.imageView, flowNewsinfo.getImg(),
//					"centerCrop", R.drawable.load_day);
//			if (StringTools.isNotEmpty(flowNewsinfo.getTitle())) {
//				holder.textview.setText(flowNewsinfo.getTitle());
//			}
//
//			view.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View arg0) {
//					AdOnClickUtil ad = new AdOnClickUtil(flowNewsinfo, mcontext);
//					ad.onClick();
//				}
//			});
//			holder.adView.setVisibility(View.VISIBLE);
//			holder.marginleftView.setVisibility(View.VISIBLE);
//		} else {
//			// 利用Glide加载图片
//			if (flowNewsBlockList.getImgs()!=null&&flowNewsBlockList.getImgs().size()>0){
//				GlideImageLoader.loadImage(mcontext, holder.imageView, flowNewsBlockList.getImgs().get(0).getUrl(),"centerCrop", R.drawable.load_day);
//			}
//			if (StringTools.isNotEmpty(flowNewsBlockList.getTitle())) {
//				holder.textview.setText(flowNewsBlockList.getTitle());
//			}
//			view.setOnClickListener(new StartOtherActivity.GotoZZDetailActivityListener(mcontext,flowNewsBlockList,scenario));
//			holder.adView.setVisibility(View.GONE);
//			holder.marginleftView.setVisibility(View.GONE);
//		}

		return view;
	}

	private static class ViewHolder {
		SimpleDraweeView imageView;
//		TextView textview, adView; // proportiontextview;
//		View marginleftView;
	}

	/**
	 * @return the isInfiniteLoop
	 */
	public boolean isInfiniteLoop() {
		return isInfiniteLoop;
	}

	/**
	 * @param isInfiniteLoop
	 *            the isInfiniteLoop to set
	 */
	public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
		this.isInfiniteLoop = isInfiniteLoop;
		return this;
	}

	public interface OnItemClickListener{
		public abstract void OnClick(Book bookDetail);
	}
}
