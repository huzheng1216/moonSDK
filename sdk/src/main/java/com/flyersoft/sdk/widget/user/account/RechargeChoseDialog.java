package com.flyersoft.sdk.widget.user.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.flyersoft.sdk.R;
import com.flyersoft.sdk.widget.BaseDialog;

/**
 * Created by zheng.hu on 2016/11/10.
 */
public class RechargeChoseDialog extends BaseDialog implements View.OnClickListener {

    private int count;
    private View layout;

    public RechargeChoseDialog(Context context, int count, RechargeListener rechargeListener) {
        super(context);
        this.rechargeListener = rechargeListener;
        this.count = count;
        TextView title = (TextView) layout.findViewById(R.id.userinfo_recharge_type_title);
        title.setText(count*100 + context.getResources().getString(R.string.mr_currency) + ": ￥" +count);
    }

    private RechargeListener rechargeListener;

    @Override
    protected int getDialogStyleId() {
        return DIALOG_COMMON_STYLE;
    }

    @Override
    protected View getView() {
        // 获取Dialog布局
        layout = LayoutInflater.from(context).inflate(R.layout.userinfo_racharge_type_dialog_layout, null);


        layout.findViewById(R.id.userinfo_recharge_del_img).setOnClickListener(this);
        layout.findViewById(R.id.userinfo_recharge_type_wx).setOnClickListener(this);
        layout.findViewById(R.id.userinfo_recharge_type_zfb).setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.userinfo_recharge_type_wx) {
            if (rechargeListener != null) {
                rechargeListener.rechargeWX(count);
            }

        } else if (id == R.id.userinfo_recharge_type_zfb) {
            if (rechargeListener != null) {
                rechargeListener.rechargeZFB(count);
            }

        } else if (id == R.id.userinfo_recharge_del_img) {
            dismiss();

        }
    }

    public interface RechargeListener{
        abstract void rechargeWX(int count);
        abstract void rechargeZFB(int count);
    }
}
