package com.speedrun_mobile_unofficial.entities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;


public class DataStorageHepler {

    private static String SHARED_PREFERENCES_KEY = "SRMBUSharedPrefs";

    public static long getStorageLong(Context activity, String storage) {
        SharedPreferences prefs = activity.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        return prefs.getLong(storage, 0);
    }

    public static HashSet<String> getStorageStrSet(Context activity, String storage){
        SharedPreferences prefs = activity.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        switch(storage) {
            case Enums.STORAGE.SUBSCRIPTION:
                return new HashSet<>(prefs.getStringSet(Enums.STORAGE.SUBSCRIPTION, new HashSet<>()));
            default:
                return new HashSet<>();
        }
    }

    public static void setStorageLong(Context activity, String storage, long value) {
        SharedPreferences.Editor prefsEditor = activity.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).edit();
        prefsEditor.putLong(storage, value).apply();
    }

    public static void addStrToStorageStrSet(Context activity, String storage, String value) {
        SharedPreferences.Editor prefsEditor = activity.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).edit();
        HashSet<String> set = getStorageStrSet(activity, Enums.STORAGE.SUBSCRIPTION);
        set.add(value);
        prefsEditor.putStringSet(storage, set).apply();
    }

    public static void removeStrFromStorageStrSet(Context activity, String storage, String value) {
        SharedPreferences.Editor prefsEditor = activity.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).edit();
        HashSet<String> set = getStorageStrSet(activity, Enums.STORAGE.SUBSCRIPTION);
        set.remove(value);
        prefsEditor.putStringSet(storage, set).apply();
    }

}
