package com.speedrun_mobile_unofficial.leaderboard;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom adapter for leaderboard ViewPager.
 */
public class BoardPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final Context context;

    public BoardPagerAdapter(FragmentManager fm, Context context, AllCategory allCategory) {
        super(fm);
        this.context = context;

        for(CategoryBoard board : allCategory.getAllCategoryBoard()) {
            BoardListFragment fragment = new BoardListFragment();
            fragment.setCateGoryBoard(board);
            mFragmentList.add(fragment);
            mFragmentTitleList.add(board.getCategoryName());
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void forceListChange(int position) {
        BoardListFragment fragment = (BoardListFragment) mFragmentList.get(position);
        fragment.forceListChange();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}