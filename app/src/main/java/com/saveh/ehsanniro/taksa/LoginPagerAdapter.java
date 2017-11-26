package com.saveh.ehsanniro.taksa;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by hossein on 18/11/2017.
 */

public class LoginPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public LoginPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LoginTabFragment1 tab1 = new LoginTabFragment1();
                return tab1;
            case 1:
                LoginTabFragment2 tab2 = new LoginTabFragment2();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}