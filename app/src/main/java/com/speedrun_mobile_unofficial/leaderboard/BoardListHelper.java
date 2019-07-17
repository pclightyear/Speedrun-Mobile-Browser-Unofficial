package com.speedrun_mobile_unofficial.leaderboard;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.speedrun_mobile_unofficial.APICallback;
import com.speedrun_mobile_unofficial.RequestQueueSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoardListHelper {

    public static void fetchLeaderBoardData(final Context context, String game, final APICallback callback) {
        String url = String.format("https://www.speedrun.com/api/v1/games/%s/records?top=10", game);
        System.out.println(url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                callback.onResponse(true, parseRecordJson(response));
            } catch (JSONException e) {
//                Log.e(getClass().getName(), e.getLocalizedMessage());
            }
        }, error -> {
            callback.onResponse(false, null);
        });

        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void fetchGameData(final Context context, String game, final APICallback callback) {
        String url = String.format("https://www.speedrun.com/api/v1/games/%s", game);
        System.out.println(url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                callback.onResponse(true, parseGameJson((response)));
            } catch (JSONException e) {

            }
        }, error -> {
           callback.onResponse(false, null);
        });

        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static Map<String, Object> parseRecordJson(JSONObject jsonObject) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        JSONArray data = jsonObject.getJSONArray("data");
        ArrayList<Map<String, Object>> allCategoryBoard = new ArrayList<>();

        for(int i = 0; i < data.length(); ++i) {
            Map<String, Object> categoryMap = new HashMap<>();
            categoryMap.put("category", data.getJSONObject(i).getString("weblink").split("#")[1]);

            ArrayList<Map<String, Object>> leaderboard = new ArrayList<>();
            JSONArray runs = data.getJSONObject(i).getJSONArray("runs");

            for(int j = 0; j < runs.length(); ++j) {
                Map<String, Object> leaderboardItemMap = new HashMap<>();
                leaderboardItemMap.put("ranking", getRankingString(runs.getJSONObject(j).getInt("place")));

                JSONObject run = runs.getJSONObject(j).getJSONObject("run");

                if(run.getJSONArray("players").getJSONObject(0).getString("rel").equals("user")) {
                    leaderboardItemMap.put("player", run.getJSONArray("players").getJSONObject(0).getString("id"));
                } else if(run.getJSONArray("players").getJSONObject(0).getString("rel").equals("guest")) {
                    leaderboardItemMap.put("player", run.getJSONArray("players").getJSONObject(0).getString("name"));
                }
                leaderboardItemMap.put("time", getTimeString(run.getJSONObject("times").getDouble("primary_t")));
                leaderboardItemMap.put("date", run.getString("date"));
                leaderboard.add(leaderboardItemMap);
            }

            categoryMap.put("leaderboard", leaderboard);
            allCategoryBoard.add(categoryMap);
        }


        map.put("allCategoryBoard", allCategoryBoard);

        return map;
    }

    public static Map<String, Object> parseGameJson(JSONObject jsonObject) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        JSONObject data = jsonObject.getJSONObject("data");

        JSONObject gameNames = data.getJSONObject("names");
        if(!gameNames.isNull("international")) {
            map.put("gameName", gameNames.getString("international"));
        } else if(!gameNames.isNull("japanese")) {
            map.put("gameName", gameNames.getString("japanese"));
        } else if(!gameNames.isNull("twitch")) {
            map.put("gameName", gameNames.getString("twitch"));
        }


        JSONObject assets = data.getJSONObject("assets");
        if(!assets.isNull("trophy-1st")) {
            map.put("trophy-1st", assets.getJSONObject("trophy-1st").getString("uri"));
        }
        if(!assets.isNull("trophy-2nd")) {
            map.put("trophy-2nd", assets.getJSONObject("trophy-2nd").getString("uri"));
        }
        if(!assets.isNull("trophy-3rd")) {
            map.put("trophy-3rd", assets.getJSONObject("trophy-3rd").getString("uri"));
        }
        if(!assets.isNull("trophy-4th")) {
            map.put("trophy-4th", assets.getJSONObject("trophy-4th").getString("uri"));
        }

        return map;
    }

    private static String getRankingString(int place) {
        if(place == 1) {
            return "1st";
        } else if(place == 2) {
            return "2nd";
        } else if(place == 3) {
            return "3rd";
        } else {
            return String.format("%dst", place);
        }
    }

    private static String getTimeString(double time) {
        int totalTime = (int) (time * 1000);
        int milliSecond = totalTime % 1000;
        totalTime /= 1000;
        int second = totalTime % 60;
        totalTime /= 60;
        int minute = totalTime % 60;
        totalTime /= 60;
        int hour = totalTime % 24;
        totalTime /= 24;
        int day = totalTime % 365;
        totalTime /= 365;
        int year = totalTime;

        String timeString = "";
        if(year != 0) {
            timeString += String.format(" %dy", year);
        }
        if(day != 0) {
            timeString += String.format(" %dd", day);
        }
        if(hour != 0 || day != 0) {
            timeString += String.format(" %dh", hour);
        }
        if(minute != 0 || hour != 0) {
            timeString += String.format(" %02dm", minute);
        }
        timeString += String.format(" %02ds %03d", second, milliSecond);

        return timeString;
    }
}
