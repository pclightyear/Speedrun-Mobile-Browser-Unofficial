package com.speedrun_mobile_unofficial.watchrecord;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.speedrun_mobile_unofficial.entities.APICallback;
import com.speedrun_mobile_unofficial.entities.RequestQueueSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WatchRunHelper {

    public static void fetchRunData(final Context context, String runId, final APICallback callback) {
        String url = String.format("https://www.speedrun.com/api/v1/runs/%s?embed=platform,region", runId);
        System.out.println(url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                callback.onResponse(true, parseRunJson(response));
            } catch (JSONException e) {
//                Log.e(getClass().getName(), e.getLocalizedMessage());
            }
        }, error -> {
            callback.onResponse(false, null);
        });

        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    public static Map<String, Object> parseRunJson(JSONObject jsonObject) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        JSONObject data = jsonObject.getJSONObject("data");

        if(!data.getJSONObject("system").isNull("platform")) {
            map.put("platform", data.getJSONObject("platform").getJSONObject("data").getString("name"));
        }
        if(!data.getJSONObject("system").isNull("region")) {
            map.put("region", data.getJSONObject("region").getJSONObject("data").getString("name"));
        }
        if(!data.isNull("videos")) {
            map.put("weblink", data.getJSONObject("videos").getJSONArray("links").getJSONObject(0).getString("uri"));
        }

        return map;
    }
}
