package com.speedrun_mobile_unofficial.leaderboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class CategoryBoard implements Serializable {
    private String categoryName;
    private String categoryRule;
    private ArrayList<CategoryBoardItem> leaderboard;

    public CategoryBoard(Map input) {
        if(input.get("category") != null) {
            this.categoryName = (String) input.get("category");
        }
        if(input.get("categoryRule") != null) {
            this.categoryRule = (String) input.get("categoryRule");
        }
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryRule() {
        return categoryRule;
    }

    public ArrayList<CategoryBoardItem> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(ArrayList<CategoryBoardItem> boardlist) {
        this.leaderboard = boardlist;
    }
}
