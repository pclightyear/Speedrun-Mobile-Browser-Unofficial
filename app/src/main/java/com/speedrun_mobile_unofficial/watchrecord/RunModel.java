package com.speedrun_mobile_unofficial.watchrecord;

import java.io.Serializable;
import java.util.Map;

public class RunModel implements Serializable {

    private String platform;
    private String region;
    private String weblink;

    private Map<String, Object> map;

    public RunModel(){}

    public RunModel(Map input) {
        if(input.get("platform") != null) {
            this.platform = (String) input.get("platform");
        }
        if(input.get("region") != null) {
            this.region = (String) input.get("region");
        }
        if(input.get("weblink") != null) {
            this.weblink = (String) input.get("weblink");
        }
    }

    public void setMap(Map input) {
        map = input;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public String getPlatform() {
        return platform;
    }

    public String getRegion() {
        return region;
    }

    public String getWeblink() {
        return weblink;
    }
}
