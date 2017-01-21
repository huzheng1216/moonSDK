package com.flyersoft.sdk.widget.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.flyersoft.sdk.BaseActivity;
import com.flyersoft.sdk.R;
import com.flyersoft.sdk.javabean.account.UserInfo;
import com.flyersoft.sdk.widget.main.other.LeftMenuLayout;
import com.flyersoft.sdk.widget.user.landing.UserLayoutBiz;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Set;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;

    private FragmentFactory fragmentFactory;
    private FragmentManager fragmentManager;
    private RadioGroup radioGroup;
    private HeaderLayout headerLayout;
    private LeftMenuLayout leftMenuLayout;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initParam() {

        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawerLayout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                Gravity.LEFT);
        leftMenuLayout = (LeftMenuLayout) findViewById(R.id.main_left_menu_main);
        leftMenuLayout.setOnClickListener(null);
        leftMenuLayout.setOnMenuAction(new UserLayoutBiz(this));
        //开启登陆/注销监听
        EventBus.getDefault().register(this);

        fragmentManager = getFragmentManager();
        fragmentFactory = new FragmentFactory(this);
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        headerLayout = (HeaderLayout) findViewById(R.id.header);
        headerLayout.setOnHeaderViewListener(new HeaderLayout.OnHeaderViewListener() {
            @Override
            public void open() {
                if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.closeDrawers();
                }else{
                    drawerLayout.openDrawer(Gravity.LEFT);
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                            Gravity.LEFT);
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment fragment = fragmentFactory.getInstanceByIndex(checkedId);
                if(!fragment.isAdded()){
                    transaction.add(R.id.content, fragment, ""+checkedId);
                }
                HashMap<String, Fragment> fragments = fragmentFactory.getFragments();
                Set<String> strings = fragments.keySet();
                for(String s : strings){
                    if(String.valueOf(checkedId).equals(s)){
                        transaction.attach(fragment);
                    }else{
                        Fragment f = fragments.get(s);
                        transaction.detach(f);
                    }
                }
                transaction.commit();
                headerLayout.onTabCheckedChange(((RadioButton)findViewById(checkedId)).getText().toString());
            }
        });
        RadioButton radioButton = (RadioButton) findViewById(R.id.main_radiobutton1);
        radioButton.setChecked(true);
    }

    @Override
    protected void initData() {

    }

    @Subscribe
    public void onEventMainThread(UserInfo userInfo) {
        leftMenuLayout.update(userInfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
