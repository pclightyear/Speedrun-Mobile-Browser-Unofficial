package com.speedrun_mobile_unofficial.leaderboard;

import java.io.Serializable;
import java.util.Map;

public class CategoryBoardItem implements Serializable {
    private String runId;
    private String ranking;
    private String player;
    private String nameStyle;
    private String color;
    private String colorFrom;
    private String colorTo;
    private String time;
    private String date;

    public CategoryBoardItem(Map input){
        if(input.get("runId") != null) {
            this.runId = (String) input.get("runId");
        }
        if(input.get("ranking") != null) {
            this.ranking = (String) input.get("ranking");
        }
        if(input.get("player") != null) {
            this.player = (String) input.get("player");
        }
        if(input.get("nameStyle") != null) {
            this.nameStyle = (String) input.get("nameStyle");
        }
        if(input.get("color") != null) {
            this.color = (String) input.get("color");
        }
        if(input.get("colorFrom") != null) {
            this.colorFrom = (String) input.get("colorFrom");
        }
        if(input.get("colorTo") != null) {
            this.colorTo = (String) input.get("colorTo");
        }
        if(input.get("time") != null) {
            this.time = (String) input.get("time");
        }
        if(input.get("date") != null) {
            this.date = (String) input.get("date");
        }
    }

    public String getRunId() {
        return runId;
    }

    public String getRanking() {
        return ranking;
    }

    public String getPlayer() {
        return player;
    }

    public String getNameStyle() {
        return nameStyle;
    }

    public String getColor() {
        return color;
    }

    public String getColorFrom() {
        return colorFrom;
    }

    public String getColorTo() {
        return colorTo;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }
}