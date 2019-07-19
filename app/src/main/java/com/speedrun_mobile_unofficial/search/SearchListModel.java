package com.speedrun_mobile_unofficial.search;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchListModel implements Serializable {
    private ArrayList<SearchListItem> searchList;

    public ArrayList<SearchListItem> getSearchList() {
        return searchList;
    }

    public void setSearchList(ArrayList<SearchListItem> searchList) {
        this.searchList = searchList;
    }
}
