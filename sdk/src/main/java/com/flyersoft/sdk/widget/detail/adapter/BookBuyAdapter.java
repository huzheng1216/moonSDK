package com.flyersoft.sdk.widget.detail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyersoft.sdk.R;
import com.flyersoft.sdk.javabean.DetailCatalogDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 书籍购买适配器
 * Created by 37399 on 2016/12/11.
 */
public class BookBuyAdapter extends BaseExpandableListAdapter {

    private Context context;

    private Map<Integer, List<DetailCatalogDetail>> data;//全部数据
    private int dataCount = 0;//总章节数

    private ArrayList<String> checked;//存储选中的章节

    private OnCountChangeListener onCountChangeListener;//通知activity选中的数量变化


    public BookBuyAdapter(Context context, OnCountChangeListener onCountChangeListener) {
        this.context = context;
        this.onCountChangeListener = onCountChangeListener;
        data = new HashMap<>(10);
        checked = new ArrayList<>(10);
    }

    public void putData(List<DetailCatalogDetail> detailCatalogDetails) {
        if (detailCatalogDetails != null && detailCatalogDetails.size() > 0) {
            data.clear();
            dataCount = 0;
            for(DetailCatalogDetail d : detailCatalogDetails){
                if(d.getLock()!=2) dataCount++;
            }
            int t = detailCatalogDetails.size() / 20;
            int a = 0;
            if (t == 0) {
                data.put(a, detailCatalogDetails.subList(0, 20));
            } else {
                for (; a < t; a++) {
                    data.put(a, detailCatalogDetails.subList(a * 20, a * 20 + 20));
                }
            }
            onCountChangeListener.onCountChange(0);
        }
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public DetailCatalogDetail getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        GroupHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.buy_group_item_layout, null);
            holder = new GroupHolder(view, groupPosition);
            view.setTag(holder);
        } else {
            holder = (GroupHolder) view.getTag();
            holder.exChange(groupPosition);
        }
        StringBuilder title = new StringBuilder();
        title.append("第").append(groupPosition * 20 + 1).append("章 - 第").append(groupPosition * 20 + 20).append("章");
        holder.groupName.setText(title);
        boolean check = true;
        for (DetailCatalogDetail detail : data.get(groupPosition)) {
            if (!detail.isChecked() && detail.getLock() != 2) {
                check = false;
                break;
            }
        }
        holder.checkBox.setChecked(check);
        if (isExpanded) {
            holder.indicator.setImageResource(R.mipmap.icon_list_expansion);
        } else {
            holder.indicator.setImageResource(R.mipmap.icon_list_shrink);
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        ChildHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.buy_child_item_layout, null);
            holder = new ChildHolder(view, groupPosition, childPosition);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
            holder.exChange(groupPosition, childPosition);
        }
        holder.childName.setText(getChild(groupPosition, childPosition).getChapterName());
        holder.price.setText(getChild(groupPosition, childPosition).getPrice() + "书币");
        holder.checkBox.setChecked(data.get(groupPosition).get(childPosition).isChecked());
        //已购买章节
        if (getChild(groupPosition, childPosition).getLock() == 2) {
            holder.checkBox.setChecked(false);
            holder.checkBox.setEnabled(false);
            holder.checkBox.setVisibility(View.INVISIBLE);
        } else {
            holder.checkBox.setEnabled(true);
            holder.checkBox.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    //全部选中
    public boolean selectAll() {
        checked.clear();
        int count = 0;
        for (List<DetailCatalogDetail> ds : data.values()) {
            for (DetailCatalogDetail d : ds) {
                //已购买章节不做统计
                if (d.getLock() == 2) {
                    continue;
                }
                d.setChecked(true);
                count += d.getPrice();
                checked.add(d.getChapterNo() + "");
            }
        }
        onCountChangeListener.onCountChange(count);
        notifyDataSetChanged();
        return true;
    }

    //全部取消选中
    public boolean selectNone() {
        checked.clear();
        for (List<DetailCatalogDetail> ds : data.values()) {
            for (DetailCatalogDetail d : ds) {
                //已购买章节不做统计
                if (d.getLock() == 2) {
                    continue;
                }
                d.setChecked(false);
            }
        }
        onCountChangeListener.onCountChange(0);
        notifyDataSetChanged();
        return true;
    }

    class GroupHolder {
        public int groupId;
        public ImageView indicator;
        public TextView groupName;
        public CheckBox checkBox;

        public GroupHolder(View view, int groupId) {
            this.groupId = groupId;
            this.indicator = (ImageView) view.findViewById(R.id.buy_group_item_indicator);
            this.groupName = (TextView) view.findViewById(R.id.buy_group_item_title);
            this.checkBox = (CheckBox) view.findViewById(R.id.buy_group_item_checkbox);
            this.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = 0;
                    for (DetailCatalogDetail detail : data.get(GroupHolder.this.groupId)) {
                        //已购买章节不做统计
                        if (detail.getLock() == 2) {
                            continue;
                        }
                        if (GroupHolder.this.checkBox.isChecked()) {
                            if (!detail.isChecked()) {
                                detail.setChecked(true);
                                count += detail.getPrice();
                                checked.add(detail.getChapterNo() + "");
                            }
                        } else {
                            if (detail.isChecked()) {
                                detail.setChecked(false);
                                count = count - detail.getPrice();
                                checked.remove(detail.getChapterNo() + "");
                            }
                        }
                    }
                    onCountChangeListener.onCountChange(count);
                    notifyDataSetChanged();
                }
            });
        }

        public void exChange(int groupId) {
            this.groupId = groupId;
        }
    }

    class ChildHolder {
        public int groupId;
        public int childId;
        public TextView childName;
        public TextView price;
        public CheckBox checkBox;

        public ChildHolder(View view, int groupId, int childId) {
            this.groupId = groupId;
            this.childId = childId;
            this.childName = (TextView) view.findViewById(R.id.buy_child_item_title);
            this.price = (TextView) view.findViewById(R.id.buy_child_item_price);
            this.checkBox = (CheckBox) view.findViewById(R.id.buy_child_item_checkbox);
            this.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailCatalogDetail detailCatalogDetail = data.get(ChildHolder.this.groupId).get(ChildHolder.this.childId);
                    detailCatalogDetail.setChecked(ChildHolder.this.checkBox.isChecked());
                    int count = ChildHolder.this.checkBox.isChecked() ? detailCatalogDetail.getPrice() : detailCatalogDetail.getPrice() * -1;
                    if (ChildHolder.this.checkBox.isChecked()) {
                        checked.add(detailCatalogDetail.getChapterNo() + "");
                    } else {
                        checked.remove(detailCatalogDetail.getChapterNo() + "");
                    }
                    onCountChangeListener.onCountChange(count);
                    notifyDataSetChanged();
                }
            });
        }

        public void exChange(int groupId, int childId) {
            this.groupId = groupId;
            this.childId = childId;
        }
    }

    public interface OnCountChangeListener {
        abstract void onCountChange(int count);
    }

    public ArrayList<String> getChecked() {
        return checked;
    }

    public int getDataCount(){
        return dataCount;
    }
}
