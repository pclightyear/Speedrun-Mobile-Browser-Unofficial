package com.speedrun_mobile_unofficial.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.speedrun_mobile_unofficial.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView mSearchlistView;
    private SearchListAdapter mSearchListAdapter;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
        }
        mSearchlistView = findViewById(R.id.search_list);
        mSearchlistView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mSearchlistView.setAdapter(new SearchListAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();
        SearchHelper.fetchSearchData(getApplicationContext(), query, ((success, result) -> {
            if(success) {
                SearchListModel model = this.prepareSearchModel(result);

                mSearchListAdapter = new SearchListAdapter(this, R.layout.activity_search_list_item, model.getSearchList());
                mSearchlistView.setAdapter(mSearchListAdapter);

                findViewById(R.id.search_progress_bar).setVisibility(ProgressBar.INVISIBLE);
                if(model.getSearchList() == null) {
                    findViewById(R.id.search_no_result_text).setVisibility(TextView.VISIBLE);
                }
            }
        }));
    }

    private SearchListModel prepareSearchModel(Map result) {
        SearchListModel model = new SearchListModel();
        List results = (ArrayList) result.get("searchList");
        ArrayList<SearchListItem> searchList = new ArrayList<>();

        if ((results != null ? results.size() : 0) > 0) {
            for (int i = 0; i < results.size(); i++) {
                Map<String, Object> map = (Map) results.get(i);
                SearchListItem item = new SearchListItem(map);
                searchList.add(item);
            }

            model.setSearchList(searchList);
        }

        return model;
    }

}
