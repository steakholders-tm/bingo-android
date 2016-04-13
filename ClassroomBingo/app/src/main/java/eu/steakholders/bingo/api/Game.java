package eu.steakholders.bingo.api;


import android.content.Context;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Game extends ModelGetterAndSetter {
    protected static String API_PATH = "games";
    private int id;
    private String name;
    private String description;


    public Game(Context c) {
        super(c);
    }

    public Game(int id, String name, String description){
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
                        success.onResponse(Game.fromJSONObject(response));
                    }
                },
                error);

    }

    private static Game fromJSONObject(JSONObject response) {
        return new Game(
                response.optInt("id", -1),
                response.optString("name", ""),
                response.optString("description","")
        );
    }

    private static ArrayList<Game> fromJSONArray(JSONArray response) {
        ArrayList<Game> pames = new ArrayList<Game>();
        for( int i = 0; i < response.length(); i++){
            try{
                pames.add(Game.fromJSONObject(response.getJSONObject(i)));
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        return pames;
    }

    public static void getAll(Context context, final Response.Listener success , Response.ErrorListener error){
        ModelGetter.getAll(
                context,
                API_PATH,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("Response: " + response.toString());
                        success.onResponse(Game.fromJSONArray(response));
                    }
                },
                error);
    }
}
