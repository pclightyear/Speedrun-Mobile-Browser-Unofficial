package com.speedrun_mobile_unofficial.entities;

import java.util.Map;

public interface APICallback {
    void onResponse(boolean success, Map result);
}