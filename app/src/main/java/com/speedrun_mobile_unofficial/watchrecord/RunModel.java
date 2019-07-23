package com.speedrun_mobile_unofficial.watchrecord;

import java.io.Serializable;
import java.util.Map;

public class RunModel implements Serializable {

    private String platform;
    private String region;

    public RunModel(Map input) {
        if(input.get("platform") != null) {
            this.platform = (String) input.get("platform");
        }
        if(input.get("region") != null) {
            this.region = (String) input.get("region");
        }
    }

    public String getPlatform() {
        return platform;
    }

    public String getRegion() {
        return region;
    }
}
