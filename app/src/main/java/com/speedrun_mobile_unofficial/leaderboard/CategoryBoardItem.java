package com.speedrun_mobile_unofficial.leaderboard;

import java.io.Serializable;
import java.util.Map;

public class CategoryBoardItem implements Serializable {
    private String ranking;
    private String player;
    private String time;
    private String date;

    public CategoryBoardItem(Map input){
        if(input.get("ranking") != null) {
            this.ranking = (String) input.get("ranking");
        }

        if(input.get("player") != null) {
            this.player = (String) input.get("player");
        }

        if(input.get("time") != null) {
            this.time = (String) input.get("time");
        }

        if(input.get("date") != null) {
            this.date = (String) input.get("date");
        }
    }

    public String getRanking() {
        return ranking;
    }

    public String getPlayer() {
        return player;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }
}