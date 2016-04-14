package eu.steakholders.bingo.api;

import android.content.Context;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Winner extends ModelGetterAndSetter {
    protected static String API_PATH = "winners";
    private int id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Winner(Context c) {
        super(c);
    }

    public Winner(int id, String name){
        this.id = id;
        this.name = name;
    }

    public static void getById(Context context, int id, final Response.Listener success , Response.ErrorListener error){
        ModelGetter.getById(
                context,
                API_PATH,
                id,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        success.onResponse(Winner.fromJSONObject(response));
                    }
                },
                error);

    }

    public static Winner fromJSONObject(JSONObject response) {
        if(response == null){
            return null;
        }
        return new Winner(
                response.optInt("id", -1),
                response.optString("name", "")
        );
    }

    public static ArrayList<Winner> fromJSONArray(JSONArray response) {
        if(response == null){
            return null;
        }
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

                        success.onResponse(Winner.fromJSONArray(response));
                    }
                },
                error);
    }
}
