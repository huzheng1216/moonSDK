package com.flyersoft.sdk.widget.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flyersoft.sdk.R;
import com.flyersoft.sdk.javabean.CatalogDetail;

import java.util.List;

/**
 * Describe: 分类列表适配器
 * Created by ${zheng.hu} on 2016/10/7.
 */
public class MainCategoryAdapter extends RecyclerView.Adapter {

    private List<CatalogDetail> data;
    private OnRecyclerViewListener onRecyclerViewListener;

    public static interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    public MainCategoryAdapter(List<CatalogDetail> data, OnRecyclerViewListener onRecyclerViewListener) {
        this.data = data;
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_category_item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        PersonViewHolder holder = (PersonViewHolder) viewHolder;
        holder.position = position;
        CatalogDetail catalogDetail = data.get(position);
        holder.title.setText(catalogDetail.getCategoryName());
        holder.img1.setImageURI(catalogDetail.getImgs()[0]);
        holder.img2.setImageURI(catalogDetail.getImgs()[1]);
        holder.img3.setImageURI(catalogDetail.getImgs()[2]);
        StringBuffer sb = new StringBuffer();
        sb.append("共").append(catalogDetail.getCount()).append("册");
        holder.dec.setText(sb.toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public int position;
        private View rootView;
        private TextView title;
        private SimpleDraweeView img1;
        private SimpleDraweeView img2;
        private SimpleDraweeView img3;
        private TextView dec;

        public PersonViewHolder(View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.main_category_item_layout);
            title = (TextView) itemView.findViewById(R.id.main_category_item_title);
            img1 = (SimpleDraweeView) itemView.findViewById(R.id.main_category_item_img1);
            img2 = (SimpleDraweeView) itemView.findViewById(R.id.main_category_item_img2);
            img3 = (SimpleDraweeView) itemView.findViewById(R.id.main_category_item_img3);
            dec = (TextView) itemView.findViewById(R.id.main_category_item_dec);

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
