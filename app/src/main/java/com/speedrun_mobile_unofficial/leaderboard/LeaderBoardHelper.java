package com.speedrun_mobile_unofficial.leaderboard;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.speedrun_mobile_unofficial.entities.APICallback;
import com.speedrun_mobile_unofficial.entities.RequestQueueSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeaderBoardHelper {

    public static void fetchLeaderBoardData(final Context context, String game, final APICallback callback) {
        String url = String.format("https://www.speedrun.com/api/v1/games/%s/records?top=200&embed=players,category", game);
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
        String url = String.format("https://www.speedrun.com/api/v1/games/%s?embed=platforms", game);
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

    private static Map<String, Object> parseRecordJson(JSONObject jsonObject) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        JSONArray data = jsonObject.getJSONArray("data");
        ArrayList<Map<String, Object>> allCategoryBoard = new ArrayList<>();

        for(int i = 0; i < data.length(); ++i) {
            Map<String, Object> categoryMap = new HashMap<>();
            JSONObject category = data.getJSONObject(i);
            JSONObject categoryInfo = category.getJSONObject("category").getJSONObject("data");
            categoryMap.put("category", categoryInfo.getString("name"));
            categoryMap.put("categoryRule", categoryInfo.getString("rules"));

            ArrayList<Map<String, Object>> leaderboard = new ArrayList<>();
            JSONArray runs = category.getJSONArray("runs");
            JSONArray players = category.getJSONObject("players").getJSONArray("data");

            for(int j = 0; j < runs.length(); ++j) {
                Map<String, Object> leaderboardItemMap = new HashMap<>();
                leaderboardItemMap.put("ranking", getRankingString(runs.getJSONObject(j).getInt("place")));

                JSONObject run = runs.getJSONObject(j).getJSONObject("run");
                leaderboardItemMap.put("runId", run.getString("id"));
                JSONObject playerInRun = run.getJSONArray("players").getJSONObject(0);
                JSONObject playerInfo = players.getJSONObject(j);

                if(("user").equals(playerInRun.getString("rel"))) {
                    if(playerInRun.getString("id").equals(playerInfo.getString("id"))) {
                        JSONObject names = playerInfo.getJSONObject("names");
                        if(!names.isNull("international")) {
                            leaderboardItemMap.put("player", names.getString("international"));
                        } else if(!names.isNull("japanese")) {
                            leaderboardItemMap.put("player", names.getString("japanese"));
                        }
                    }
                } else if(("guest").equals(playerInRun.getString("rel"))) {
                    if(playerInfo.getString("name").equals(playerInRun.getString("name"))) {
                        leaderboardItemMap.put("player", playerInfo.getString("name"));
                    }
                }

                if(playerInfo.has("name-style")) {
                    JSONObject nameStyle = playerInfo.getJSONObject("name-style");
                    if(("solid").equals(nameStyle.getString("style"))) {
                        leaderboardItemMap.put("nameStyle", "solid");
                        leaderboardItemMap.put("color", nameStyle.getJSONObject("color").getString("dark"));
                    } else if(("gradient").equals(nameStyle.getString("style"))) {
                        leaderboardItemMap.put("nameStyle", "gradient");
                        leaderboardItemMap.put("colorFrom", nameStyle.getJSONObject("color-from").getString("dark"));
                        leaderboardItemMap.put("colorTo", nameStyle.getJSONObject("color-to").getString("dark"));
                    }
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

    private static Map<String, Object> parseGameJson(JSONObject jsonObject) throws JSONException {
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

        map.put("coverSmallUri", data.getJSONObject("assets").getJSONObject("cover-small").getString("uri"));

        JSONArray platforms = data.getJSONObject("platforms").getJSONArray("data");
        ArrayList<String> platformNames = new ArrayList<>();
        for(int i = 0; i < platforms.length(); ++i) {
            platformNames.add(platforms.getJSONObject(i).getString("name"));
        }
        map.put("platforms", String.join(", ", platformNames));

        map.put("releaseDate", data.getString("release-date"));

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
        if(place == 0) {
            return "----";
        } else if(place % 10 == 1 && place % 100 != 11) {
            return String.format("%dst", place);
        } else if(place % 10 == 2 && place % 100 != 12) {
            return String.format("%dnd", place);
        } else if(place % 10 == 3 && place % 100 != 13) {
            return String.format("%drd", place);
        } else {
            return String.format("%dth", place);
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
