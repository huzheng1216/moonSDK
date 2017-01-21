package com.flyersoft.sdk.widget.catalog.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyersoft.sdk.R;
import com.flyersoft.sdk.javabean.DetailCatalogDetail;

import java.util.List;

/**
 * Describe: 目录列表适配器
 * Created by ${zheng.hu} on 2016/10/7.
 */
public class DetailCatalogAdapter extends RecyclerView.Adapter {

    private List<DetailCatalogDetail> data;
    private OnRecyclerViewListener onRecyclerViewListener;

    public static interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    public DetailCatalogAdapter(List<DetailCatalogDetail> data, OnRecyclerViewListener onRecyclerViewListener) {
        this.data = data;
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_catalog_item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        PersonViewHolder holder = (PersonViewHolder) viewHolder;
        holder.position = position;
        DetailCatalogDetail detailCatalogDetail = data.get(position);
//        StringBuffer sb = new StringBuffer();
//        sb.append("第").append(detailCatalogDetail.getChapterNo()).append("章");
//        holder.num.setText(sb.toString());

//        sb.setLength(0);
        holder.title.setText(detailCatalogDetail.getChapterName());

        int lock = detailCatalogDetail.getLock();
        if (2 == lock) {
            holder.lock.setVisibility(View.GONE);
        } else {
            holder.lock.setVisibility(View.VISIBLE);
            holder.lock.setEnabled(false);
            holder.lock.setClickable(false);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public int position;
        private View rootView;
//        private TextView num;
        private TextView title;
        private ImageView lock;

        public PersonViewHolder(View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.detail_category_item_layout);
//            num = (TextView) itemView.findViewById(R.id.detail_category_item_num);
            title = (TextView) itemView.findViewById(R.id.detail_category_item_title);
            lock = (ImageView) itemView.findViewById(R.id.detail_category_item_lockimg);

            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (null != onRecyclerViewListener) {
                return onRecyclerViewListener.onItemLongClick(position);
            }
            return false;
        }
    }
}
