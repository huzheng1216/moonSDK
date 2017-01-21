package com.flyersoft.sdk.widget.user.books.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flyersoft.sdk.R;
import com.flyersoft.sdk.javabean.ShelfBook;

import java.util.List;

/**
 * 书架图书适配器
 * Created by 37399 on 2016/12/13.
 */
public class ShelfAdapter extends BaseAdapter {

    private List<ShelfBook> shelfBooks;
    private Context context;

    public ShelfAdapter(Context context, List<ShelfBook> shelfBooks){
        this.context = context;
        this.shelfBooks = shelfBooks;
    }

    @Override
    public int getCount() {
        return shelfBooks.size();
    }

    @Override
    public ShelfBook getItem(int position) {
        return shelfBooks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MHolder holder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.userbooks_item_layout,null);
            holder = new MHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (MHolder) convertView.getTag();
        }

        holder.img.setImageURI(getItem(position).getImgUrl());

        return convertView;
    }

    public class MHolder{
        private SimpleDraweeView img;

        public MHolder(View rootView){
            img = (SimpleDraweeView) rootView.findViewById(R.id.userbooks_item_img);
        }
    }
}
