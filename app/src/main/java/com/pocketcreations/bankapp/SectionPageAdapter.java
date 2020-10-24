package com.pocketcreations.bankapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class SectionPageAdapter extends FragmentStatePagerAdapter {

    public final List<Fragment> FragmentList = new ArrayList();
    private final List<String> FragmentTitleList = new ArrayList();

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String str, int i) {
        this.FragmentList.add(i, fragment);
        this.FragmentTitleList.add(str);
    }

    public int getCount() {
        return this.FragmentList.size();
    }

    public Fragment getFragment(int i) {
        return (Fragment) this.FragmentList.get(i);
    }

    public Fragment getItem(int i) {
        return (Fragment) this.FragmentList.get(i);
    }

    public int getItemPosition(Object obj) {
        return -1;
    }
    public CharSequence getPageTitle(int i) {
        return (CharSequence) this.FragmentTitleList.get(i);
    }

    public int getPosiFromTitle(String str) {
        return this.FragmentTitleList.indexOf(str);
    }
}
