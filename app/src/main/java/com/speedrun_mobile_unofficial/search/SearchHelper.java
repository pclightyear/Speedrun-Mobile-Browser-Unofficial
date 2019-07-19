package com.speedrun_mobile_unofficial.search;

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

public class SearchHelper {

    public static void fetchSearchData(final Context context, String query, final APICallback callback) {
        String url = String.format("https://www.speedrun.com/api/v1/games?name=%s&max=250", query);
        System.out.println(url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                callback.onResponse(true, parseSearchJson(response));
            } catch (JSONException e) {
//                Log.e(getClass().getName(), e.getLocalizedMessage());
            }
        }, error -> {
            callback.onResponse(false, null);
        });

        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    private static Map<String, Object> parseSearchJson(JSONObject jsonObject) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Map<String, Object>> searchList = new ArrayList<>();

        JSONArray data = jsonObject.getJSONArray("data");

        for(int i = 0; i < data.length(); ++i) {
            Map<String, Object> item = new HashMap<>();
            JSONObject game = data.getJSONObject(i);

            JSONObject names = game.getJSONObject("names");
            if(!names.isNull("international")) {
                item.put("name", names.getString("international"));
            } else if(!names.isNull("japanese")) {
                item.put("name", names.getString("japanese"));
            } else if(!names.isNull("twitch")) {
                item.put("name", names.getString("twitch"));
            }

            item.put("abbreviation", game.getString("abbreviation"));
            item.put("imageUri", game.getJSONObject("assets").getJSONObject("cover-medium").getString("uri"));

            searchList.add(item);
        }

        map.put("searchList", searchList);
        return map;
    }

}
