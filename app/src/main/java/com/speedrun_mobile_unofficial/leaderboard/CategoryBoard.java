package com.speedrun_mobile_unofficial.leaderboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class CategoryBoard implements Serializable {
    private String categoryName;
    private ArrayList<CategoryBoardItem> leaderboard;

    public CategoryBoard(Map input) {
        if(input.get("category") != null) {
            this.categoryName = (String) input.get("category");
        }
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ArrayList<CategoryBoardItem> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(ArrayList<CategoryBoardItem> boardlist) {
        this.leaderboard = boardlist;
    }
}
