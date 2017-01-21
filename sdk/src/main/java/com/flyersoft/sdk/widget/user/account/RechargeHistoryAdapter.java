package com.flyersoft.sdk.widget.user.account;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyersoft.sdk.R;
import com.flyersoft.sdk.javabean.ChargeRecords;

import java.util.List;

/**
 * Describe: 充值记录适配器
 * Created by ${zheng.hu} on 2016/10/7.
 */
public class RechargeHistoryAdapter extends RecyclerView.Adapter {

    private List<ChargeRecords> data;

    public RechargeHistoryAdapter(List<ChargeRecords> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userinfo_recharge_history_item, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        PersonViewHolder holder = (PersonViewHolder) viewHolder;
        holder.position = position;
        ChargeRecords chargeRecords = data.get(position);
        StringBuffer sb = new StringBuffer();
        sb.append(chargeRecords.getTradeAmont()).append("书币/").append(chargeRecords.getTradeAmont()/100f).append("元");
        holder.title.setText(sb.toString());
        holder.dec.setText(chargeRecords.getCreateTime());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder{

        public int position;
        private TextView title;
        private TextView dec;

        public PersonViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.userinfo_recharge_history_item_title);
            dec = (TextView) itemView.findViewById(R.id.userinfo_recharge_history_item_time);
        }

    }
}
