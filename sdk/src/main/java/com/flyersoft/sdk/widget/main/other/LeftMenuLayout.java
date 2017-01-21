package com.flyersoft.sdk.widget.main.other;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.flyersoft.sdk.R;
import com.flyersoft.sdk.http.callback.RequestCallBack;
import com.flyersoft.sdk.javabean.account.UserInfo;
import com.flyersoft.sdk.widget.user.AccountData;

/**
 * 侧边栏菜单
 * Created by 37399 on 2016/10/23.
 */
public class LeftMenuLayout extends LinearLayout implements View.OnClickListener {

    private SimpleDraweeView pic;
    private TextView name;

    private View store;
    private View account;

    private OnMenuAction onMenuAction;

    public void setOnMenuAction(OnMenuAction onMenuAction) {
        this.onMenuAction = onMenuAction;
    }

    public LeftMenuLayout(Context context) {
        super(context);
    }

    public LeftMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LeftMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        store = findViewById(R.id.main_left_menu_user_store);
        account = findViewById(R.id.main_left_menu_user_account);
        pic = (SimpleDraweeView) findViewById(R.id.main_left_menu_user_pic);
        name = (TextView) findViewById(R.id.main_left_menu_user_name);

        AccountData.getInstance((Activity) getContext()).getUserInfo(getContext(), new RequestCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                pic.setImageURI(userInfo.getHeadPic());
                name.setText(userInfo.getPetName());
            }

            @Override
            public void onFailure(String msg) {
            }
        });
        store.setOnClickListener(this);
        account.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.main_left_menu_user_store) {
            if (onMenuAction != null) {
                onMenuAction.goBookCase();
            }

        } else if (id == R.id.main_left_menu_user_account) {
            if (onMenuAction != null) {
                onMenuAction.goAccount();
            }

        }
    }

    /**
     * 更新登陆状态
     *
     * @param userInfo
     */
    public void update(UserInfo userInfo) {
        if (userInfo != null) {
            name.setText(userInfo.getPetName() == null ? "游客": userInfo.getPetName());
            pic.setImageURI(userInfo.getHeadPic() == null ? "" : userInfo.getHeadPic());
        } else {
            name.setText("游客");
            pic.setImageURI("");
        }
    }

    public interface OnMenuAction {
        abstract void goBookCase();

        abstract void goAccount();
    }
}
