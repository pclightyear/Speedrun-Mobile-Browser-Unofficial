package com.speedrun_mobile_unofficial.leaderboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom adapter for leaderboard ViewPager.
 */
public class BoardPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
//    private final Context context;

    public BoardPagerAdapter(FragmentManager fm) {
        super(fm);
//        this.context = context;
        for(CategoryBoard board : CategoryBoardModel.getSharedInstance().getAllCategoryBoard()) {
            BoardListFragment fragment = new BoardListFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("board", board);
            fragment.setArguments(bundle);
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

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}