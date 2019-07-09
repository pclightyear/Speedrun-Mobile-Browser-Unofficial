package com.speedrun_mobile_unofficial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing list view to
 * display "recently watched" page at homepage.
 */
public class RecentlyWatchedPlaceholderFragment extends Fragment {

    public RecentlyWatchedPlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView textView = rootView.findViewById(R.id.section_label);
        textView.setText(R.string.RECENTLY_WATCHED_NOT_FINISHED);
        return rootView;
    }
}
