package com.speedrun_mobile_unofficial.search;

import java.io.Serializable;
import java.util.Map;

public class SearchListItem implements Serializable {
    private String name;
    private String abbreviation;
    private String imageUri;
//    private String type;

    public SearchListItem(Map input) {
        if(input.get("name") != null) {
            this.name = (String) input.get("name");
        }
        if(input.get("abbreviation") != null) {
            this.abbreviation = (String) input.get("abbreviation");
        }
        if(input.get("imageUri") != null) {
            this.imageUri = (String) input.get("imageUri");
        }
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getImageUri() {
        return imageUri;
    }
}
