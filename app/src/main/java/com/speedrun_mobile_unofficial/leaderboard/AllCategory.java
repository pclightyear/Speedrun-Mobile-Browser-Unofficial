package com.speedrun_mobile_unofficial.leaderboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class AllCategory implements Serializable {
    private String gameName;
    private ArrayList<CategoryBoard> allCategoryBoard;

    public AllCategory(Map input) {
        if(input.get("gameName") != null) {
            this.gameName = (String) input.get("gameName");
        }
    }

    public String getGameName() {
        return gameName;
    }

    public ArrayList<CategoryBoard> getAllCategoryBoard() {
        return allCategoryBoard;
    }

    public void setAllCategoryBoard(ArrayList<CategoryBoard> allCategoryBoard) {
        this.allCategoryBoard = allCategoryBoard;
    }
}
