package com.speedrun_mobile_unofficial.homepage;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

public class WatchTimeModel implements Serializable {
    private LocalDate date;
    private int timeInMinutes;

    public WatchTimeModel(Map input) {
        if(input.get("date") != null) {
            this.date = (LocalDate) input.get("date");
        }
        if(input.get("timeInMinutes") != null) {
            this.timeInMinutes = (int) input.get("timeInMinutes");
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
    }
}
