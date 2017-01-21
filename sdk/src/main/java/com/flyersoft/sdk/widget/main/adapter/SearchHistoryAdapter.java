package com.flyersoft.sdk.widget.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.flyersoft.sdk.R;

import java.util.List;

/**
 * 搜索历史适配器
 * Created by 37399 on 2016/12/23.
 */
public class SearchHistoryAdapter extends BaseAdapter {

    private Context context;
    private List<String> data;

    public SearchHistoryAdapter(Context context, List<String> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SearchViewHolder holder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.main_search_history_item, null);
            holder = new SearchViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (SearchViewHolder) convertView.getTag();
        }

        holder.title.setText(getItem(position));

        return convertView;
    }

    class SearchViewHolder{
        TextView title;

        public SearchViewHolder(View root){
            title = (TextView) root.findViewById(R.id.search_history_item_title);
        }
    }
}
