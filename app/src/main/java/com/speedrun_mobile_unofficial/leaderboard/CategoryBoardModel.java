package com.speedrun_mobile_unofficial.leaderboard;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class CategoryBoardModel implements Serializable {
    private static CategoryBoardModel sharedInstance;
    private String gameName;
    private ArrayList<CategoryBoard> allCategoryBoard;

    public CategoryBoardModel(Map input) {
        if(input.get("gameName") != null) {
            this.gameName = (String) input.get("gameName");
        }
    }

    public static CategoryBoardModel getSharedInstance() {
        return sharedInstance;
    }

    public static void setSharedInstance(CategoryBoardModel sharedInstance) {
        CategoryBoardModel.sharedInstance = sharedInstance;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public ArrayList<CategoryBoard> getAllCategoryBoard() {
        return allCategoryBoard;
    }

    public void setAllCategoryBoard(ArrayList<CategoryBoard> allCategoryBoard) {
        this.allCategoryBoard = allCategoryBoard;
    }
}
