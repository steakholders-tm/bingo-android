package eu.steakholders.bingo.api;

import android.content.Context;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Winner extends ModelGetterAndSetter {
    protected static String API_PATH = "winners";
    private int id;
    private String name;
    private String description;


    public Winner(Context c) {
        super(c);
    }

    public Winner(int id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static void getById(Context context, int id, final Response.Listener success , Response.ErrorListener error){
        ModelGetter.getById(
                context,
                API_PATH,
                id,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response: " + response.toString());
                        success.onResponse(Winner.fromJSONObject(response));
                    }
                },
                error);

    }

    private static Winner fromJSONObject(JSONObject response) {
        return new Winner(
                response.optInt("id", -1),
                response.optString("name", ""),
                response.optString("description","")
        );
    }

    private static ArrayList<Winner> fromJSONArray(JSONArray response) {
        ArrayList<Winner> winners = new ArrayList<Winner>();
        for( int i = 0; i < response.length(); i++){
            try{
                winners.add(Winner.fromJSONObject(response.getJSONObject(i)));
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        return winners;
    }

    public static void getAll(Context context, final Response.Listener success , Response.ErrorListener error){
        ModelGetter.getAll(
                context,
                API_PATH,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("Response: " + response.toString());
                        success.onResponse(Winner.fromJSONArray(response));
                    }
                },
                error);
    }
}
