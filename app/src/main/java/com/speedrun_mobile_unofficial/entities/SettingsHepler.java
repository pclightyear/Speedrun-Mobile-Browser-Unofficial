package com.speedrun_mobile_unofficial.entities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;

public class SettingsHepler {

    private static String SHARED_PREFERENCES_KEY = "SRMBUSharedPrefs";

    public static HashSet<String> getSettingStrSet(Context activity, String setting){
        SharedPreferences prefs = activity.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        switch(setting){
            case Enums.SETTING.SUBSCRIPTION:
                return new HashSet<>(prefs.getStringSet(Enums.SETTING.SUBSCRIPTION, new HashSet<>()));
            default:
                return new HashSet<>();
        }
    }

    public static void addStrToSettingStrSet(Context activity, String setting, String value) {
        SharedPreferences.Editor prefsEditor = activity.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).edit();
        HashSet<String> set = getSettingStrSet(activity, Enums.SETTING.SUBSCRIPTION);
        set.add(value);
        prefsEditor.putStringSet(setting, set).apply();
    }

    public static void removeStrFromSettingStrSet(Context activity, String setting, String value) {
        SharedPreferences.Editor prefsEditor = activity.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).edit();
        HashSet<String> set = getSettingStrSet(activity, Enums.SETTING.SUBSCRIPTION);
        set.remove(value);
        prefsEditor.putStringSet(setting, set).apply();
    }

}
