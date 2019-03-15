package com.e.appmc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class VisitPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private int idUser;

    public VisitPagerAdapter(FragmentManager fm, int NumOfTabs, int idUser) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.idUser = idUser;
    }


    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                SummaryVisitFragment tab1 = new SummaryVisitFragment();
                return tab1;
            case 1:
                MyVisitFragment tab2 = new MyVisitFragment();
                Bundle args = new Bundle();
                args.putInt("userId", idUser);
                tab2.setArguments(args);
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
