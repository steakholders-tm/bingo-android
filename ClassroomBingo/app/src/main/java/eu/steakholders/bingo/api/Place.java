package eu.steakholders.bingo.api;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Place extends ModelGetter {

    protected static String API_PATH = "place";
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public Place(Context c) {
        super(c);
    }

    public Place(int id, String name, String description){
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

                        success.onResponse(Place.fromJSONObject(response));
                    }
                },
                error);

    }

    public static Place fromJSONObject(JSONObject response) {
        if(response == null){
            return null;
        }
        return new Place(
                response.optInt("id", -1),
                response.optString("name", ""),
                response.optString("description","")
        );
    }

    public static ArrayList<Place> fromJSONArray(JSONArray response) {
        if(response == null){
            return null;
        }
        ArrayList<Place> places = new ArrayList<Place>();
        for( int i = 0; i < response.length(); i++){
            try{
                places.add(Place.fromJSONObject(response.getJSONObject(i)));
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        return places;
    }

    public static void getAll(Context context, final Response.Listener success , Response.ErrorListener error){
        ModelGetter.getAll(
                context,
                API_PATH,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        success.onResponse(Place.fromJSONArray(response));
                    }
                },
                error);
    }
}
