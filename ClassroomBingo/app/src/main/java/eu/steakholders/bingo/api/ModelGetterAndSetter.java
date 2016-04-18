package eu.steakholders.bingo.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class ModelGetterAndSetter extends ModelGetter {

    public ModelGetterAndSetter(Context c) {
        super(c);
    }

    public ModelGetterAndSetter() {
    }

    protected static void create(Context context, String url, JSONObject payload, final Response.Listener success , Response.ErrorListener error){
        request(context, Request.Method.POST, API_URL + url + API_FORMAT, payload, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                success.onResponse(Game.fromJSONObject(response));
            }
        }, error);
    }
}
