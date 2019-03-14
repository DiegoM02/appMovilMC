package com.e.appmc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class VisitPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public VisitPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                SummaryVisitFragment tab1 = new SummaryVisitFragment();
                return tab1;
            case 1:
                MyVisitFragment tab2 = new MyVisitFragment();
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
