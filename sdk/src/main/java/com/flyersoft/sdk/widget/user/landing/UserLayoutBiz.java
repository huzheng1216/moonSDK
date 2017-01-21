package com.flyersoft.sdk.widget.user.landing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.flyersoft.sdk.widget.main.other.LeftMenuLayout;
import com.flyersoft.sdk.widget.user.AccountData;
import com.flyersoft.sdk.widget.user.account.UserInfoActivity;
import com.flyersoft.sdk.widget.user.books.UserBooksActivity;

/**
 * 处理侧边栏相关业务
 * Created by 37399 on 2016/10/23.
 */
public class UserLayoutBiz implements LeftMenuLayout.OnMenuAction {

    private AccountData accountData;
    private Context c;

    public UserLayoutBiz(Activity c){
        accountData = AccountData.getInstance(c);
        this.c = c;
    }

    @Override
    public void goBookCase() {
        if(accountData.getmUserInfo()!=null && !accountData.getmUserInfo().isNeedSignin()){
            Intent intent = new Intent(c, UserBooksActivity.class);
            c.startActivity(intent);
        }else{
            goSignIn();
        }
    }

    @Override
    public void goAccount() {
        if(accountData.getmUserInfo()!=null && !accountData.getmUserInfo().isNeedSignin()){
            Intent intent = new Intent(c, UserInfoActivity.class);
            c.startActivity(intent);
        }else{
            goSignIn();
        }
    }

    private void goSignIn() {
        Intent intent = new Intent(c, LandingPageActivity.class);
        c.startActivity(intent);
    }

}
