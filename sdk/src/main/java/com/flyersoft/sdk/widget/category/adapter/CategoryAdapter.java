package com.flyersoft.sdk.widget.category.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flyersoft.sdk.R;
import com.flyersoft.sdk.javabean.BookDetail;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Describe: 分类书籍列表适配器
 * Created by ${zheng.hu} on 2016/10/7.
 */
public class CategoryAdapter extends RecyclerView.Adapter {

    public static final int TYPE_FOOT = 0;
    public static final int TYPE_DATA = 1;

    private boolean showKey;
    private String key;

    private List<BookDetail> data;
    private OnRecyclerViewListener onRecyclerViewListener;
    private boolean hasFoot;

    public static interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);

        void onFootViewClick();
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    public CategoryAdapter(List<BookDetail> data, boolean showKey, String key) {
        this.showKey = showKey;
        this.key = key;
        setFoot(true);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOT) {
            return onCreateFootViewHolder(parent, viewType);
        }else {
            return onCreateDataViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof FootViewHolder){
            FootViewHolder holder = (FootViewHolder) viewHolder;
            holder.text.setVisibility(View.VISIBLE);
        }else{
            PersonViewHolder holder = (PersonViewHolder) viewHolder;
            holder.position = position;
            BookDetail bookDetail = data.get(position);
            holder.img.setImageURI(bookDetail.getMidImage());
            if(showKey){
                holder.title.setText(highlightKeyword(bookDetail.getBookName()));
                holder.doc.setText(highlightKeyword(bookDetail.getBrief()));
                holder.author.setText(highlightKeyword(bookDetail.getAuthor()));
            }else{
                holder.title.setText(bookDetail.getBookName());
                holder.doc.setText(bookDetail.getBrief());
                holder.author.setText(bookDetail.getAuthor());
            }
        }
    }

    /**
     * 高亮某个关键字，如果有多个则全部高亮
     */
    private SpannableString highlightKeyword(String name) {
        SpannableString sp = new SpannableString(name);
        Pattern p = Pattern.compile(key);
        Matcher m = p.matcher(name);

        while (m.find()) {  //通过正则查找，逐个高亮
            int start = m.start();
            int end = m.end();
            sp.setSpan(new ForegroundColorSpan(Color.parseColor("#1e88e5")), start ,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return sp;
    }

    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new PersonViewHolder(view);
    }

    public RecyclerView.ViewHolder onCreateFootViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_foot_progress, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new FootViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return data.size() + (hasFoot ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == data.size() && hasFoot) {
            return TYPE_FOOT;
        } else {
            return TYPE_DATA;
        }
    }
    
    public void setFoot(boolean hasFoot){
        this.hasFoot = hasFoot;
    }

    public void notifyDataSetChanged(List<BookDetail> bookDetails) {
        if(bookDetails!=null){
            if(bookDetails.size()<10) setFoot(false);
            data.addAll(bookDetails);
            this.notifyDataSetChanged();
        }
    }

    public List<BookDetail> getData(){
        return data;
    }

    class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public int position;
        private View rootView;
        private SimpleDraweeView img;
        private TextView title;
        private TextView doc;
        private TextView author;

        public PersonViewHolder(View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.category_item_layout);
            img = (SimpleDraweeView) itemView.findViewById(R.id.category_item_img);
            title = (TextView) itemView.findViewById(R.id.category_item_title);
            doc = (TextView) itemView.findViewById(R.id.category_item_doc);
            author = (TextView) itemView.findViewById(R.id.category_item_auther);

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

    class FootViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View rootView;
        private TextView text;

        public FootViewHolder(View itemView) {
            super(itemView);
            rootView = itemView.findViewById(R.id.category_foot_item_layout);
            text = (TextView) itemView.findViewById(R.id.category_foot_item_title);

            rootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onFootViewClick();
            }
        }

    }
}
