package eu.steakholders.bingo.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public abstract class ModelGetter {
    protected Context context;
    protected static RequestQueue queue = null;
    protected static String API_URL = "https://steakholders.eu/api/v1/";
    protected static String API_FORMAT = "/?format=json";

    public ModelGetter(Context c) {
        context = c;
    }

    protected ModelGetter() {
    }


    protected static void getById(Context context, String url, int id, Response.Listener success , Response.ErrorListener error){
        request(context, Request.Method.GET, API_URL + url + "/" + id + API_FORMAT, null, success, error);
    }

    protected static void getAll(Context context,String url, Response.Listener success , Response.ErrorListener error){
        requestAll(context, Request.Method.GET, API_URL + url + API_FORMAT, null, success, error);
    }

    protected static void request(Context context,int method, String url, JSONObject payload, Response.Listener success , Response.ErrorListener error){
        if(queue == null){
            queue = Volley.newRequestQueue(context);
        }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(method, url, payload, success, error);

        queue.add(jsObjRequest);
    }

    protected static void requestAll(Context context, int method, String url, JSONArray payload, Response.Listener success , final Response.ErrorListener errorListener){
        if(queue == null){
            queue = Volley.newRequestQueue(context);
        }

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(method, url, payload, success,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(new String(error.networkResponse.data));
                        errorListener.onErrorResponse(error);
                    }
                }
        );

        queue.add(jsObjRequest);
    }



    public static void getAll(Context context, String apiPath, String filter, int id, JSONObject jsonObject, Response.Listener success, Response.ErrorListener error) {
        requestAll(context, Request.Method.GET, API_URL + apiPath + API_FORMAT + "&" + filter + id, null, success, error);
    }
}

