package com.speedrun_mobile_unofficial.leaderboard;

import java.io.Serializable;
import java.util.Map;

public class GameInfoModel implements Serializable {
    private String gameName;
    private String coverImageSmallUri;
    private String platforms;
    private String releaseDate;
    private String firstTrophyUri;
    private String secondTrophyUri;
    private String thirdTrophyUri;
    private String fourthTrophyUri;

    public GameInfoModel(Map input) {
        if(input.get("gameName") != null) {
            this.gameName = (String) input.get("gameName");
        }
        if(input.get("coverSmallUri") != null) {
            this.coverImageSmallUri = (String) input.get("coverSmallUri");
        }
        if(input.get("platforms") != null) {
            this.platforms = (String) input.get("platforms");
        }
        if(input.get("releaseDate") != null) {
            this.releaseDate = (String) input.get("releaseDate");
        }
        if(input.get("trophy-1st") != null) {
            this.firstTrophyUri = (String) input.get("trophy-1st");
        }
        if(input.get("trophy-2nd") != null) {
            this.secondTrophyUri = (String) input.get("trophy-2nd");
        }
        if(input.get("trophy-3rd") != null) {
            this.thirdTrophyUri = (String) input.get("trophy-3rd");
        }
        if(input.get("trophy-4th") != null) {
            this.fourthTrophyUri = (String) input.get("trophy-4th");
        }
    }

    public String getGameName() {
        return gameName;
    }

    public String getCoverImageSmallUri() {
        return coverImageSmallUri;
    }

    public String getPlatforms() {
        return platforms;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getFirstTrophyUri() {
        return firstTrophyUri;
    }

    public String getSecondTrophyUri() {
        return secondTrophyUri;
    }

    public String getThirdTrophyUri() {
        return thirdTrophyUri;
    }

    public String getFourthTrophyUri() {
        return fourthTrophyUri;
    }
}
