package com.speedrun_mobile_unofficial.entities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

public class DataStorageHepler {

    private static String SHARED_PREFERENCES_KEY = "SRMBUSharedPrefs";

    public static long getStorageLong(Context activity, String storage) {
        SharedPreferences prefs = activity.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        return prefs.getLong(storage, 0);
    }

    public static String getStorageStr(Context activity, String storage) {
        SharedPreferences prefs = activity.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        return prefs.getString(storage, "");
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

    public static void setStorageStr(Context activity, String storage, String value) {
        SharedPreferences.Editor prefsEditor = activity.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).edit();
        prefsEditor.putString(storage, value).apply();
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

    public static void exportDataToJSONFile(Context activity) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File dir = activity.getExternalFilesDir(Constants.EXTERNAL_STORAGE_DIRECTORY_NAME);
            dir.mkdir();
            File file = new File(dir, Constants.EXPORT_FILENAME);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(setupExportFileJSONString(activity).getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static String setupExportFileJSONString(Context activity) throws JSONException {
        SharedPreferences prefs = activity.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Enums.STORAGE.WATCHTIMEFORMAT);
        JSONObject root = new JSONObject();
        JSONObject watchTime = new JSONObject();

        String startDateString = DataStorageHepler.getStorageStr(activity, Enums.STORAGE.STARTDATE);
        LocalDate startDate = LocalDate.parse(startDateString);
        LocalDate todayDate = LocalDate.now();

        LocalDate currentDate = startDate;
        JSONArray records = new JSONArray();
        do {
            String currentDateString = currentDate.format(formatter);
            long time = getStorageLong(activity, currentDateString);
            if(time != 0) {
                JSONObject record = new JSONObject();
                record.put("date", currentDateString);
                record.put("time", time);
                records.put(record);
            }
            currentDate = currentDate.plusDays(1);
        } while(!currentDate.isAfter(todayDate));
        watchTime.put("start-date", startDateString);
        watchTime.put("records", records);

        JSONArray subGame = new JSONArray();
        HashSet<String> set = DataStorageHepler.getStorageStrSet(activity, Enums.STORAGE.SUBSCRIPTION);
        for(String name : set) {
            JSONObject game = new JSONObject();
            game.put("name", name);
            subGame.put(game);
        }

        root.put("sub-game", subGame);
        root.put("watch-time", watchTime);

        return root.toString();
    }

}
