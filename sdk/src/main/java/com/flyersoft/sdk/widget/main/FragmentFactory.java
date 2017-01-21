package com.flyersoft.sdk.widget.main;

import android.app.Fragment;
import android.content.Context;

import com.flyersoft.sdk.R;
import com.flyersoft.sdk.widget.main.fragment.CategoryFragment;
import com.flyersoft.sdk.widget.main.fragment.FreeBooksFragment;
import com.flyersoft.sdk.widget.main.fragment.RankingsFragment;
import com.flyersoft.sdk.widget.main.fragment.RecommendFragment;

import java.util.HashMap;

/**
 * Describe: fragment工厂
 * Created by ${zheng.hu} on 2016/10/3.
 */
public class FragmentFactory {

    private HashMap<String, Fragment> fragments = new HashMap<String, Fragment>(4);

    public FragmentFactory(Context context) {
    }

    public HashMap<String, Fragment> getFragments() {
        return fragments;
    }

    public Fragment getInstanceByIndex(int index) {
        Fragment fragment = null;
        if (index == R.id.main_radiobutton1) {
            fragment = fragments.get("" + R.id.main_radiobutton1);
            if (fragment == null) {
                fragment = new RecommendFragment();
                fragments.put("" + R.id.main_radiobutton1, fragment);
            }

        } else if (index == R.id.main_radiobutton2) {
            fragment = fragments.get("" + R.id.main_radiobutton2);
            if (fragment == null) {
                fragment = new FreeBooksFragment();
                fragments.put("" + R.id.main_radiobutton2, fragment);
            }

        } else if (index == R.id.main_radiobutton3) {
            fragment = fragments.get("" + R.id.main_radiobutton3);
            if (fragment == null) {
                fragment = new RankingsFragment();
                fragments.put("" + R.id.main_radiobutton3, fragment);
            }

        } else if (index == R.id.main_radiobutton4) {
            fragment = fragments.get("" + R.id.main_radiobutton4);
            if (fragment == null) {
                fragment = new CategoryFragment();
                fragments.put("" + R.id.main_radiobutton4, fragment);
            }

        } else {
            fragment = fragments.get("" + R.id.main_radiobutton1);
            if (fragment == null) {
                fragment = new RecommendFragment();
                fragments.put("" + R.id.main_radiobutton1, fragment);
            }

        }
        return fragment;
    }
}
