package eu.steakholders.bingo.api;

import android.content.Context;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PrimaryCategory extends ModelGetter {
    protected static String API_PATH = "primary-categories";
    private int id;
    private String name;
    private String description;


    public PrimaryCategory(Context c) {
        super(c);
    }

    public PrimaryCategory(int id, String name, String description){
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
                        success.onResponse(PrimaryCategory.fromJSONObject(response));
                    }
                },
                error);

    }

    private static PrimaryCategory fromJSONObject(JSONObject response) {
        return new PrimaryCategory(
                response.optInt("id", -1),
                response.optString("name", ""),
                response.optString("description","")
        );
    }

    private static ArrayList<PrimaryCategory> fromJSONArray(JSONArray response) {
        ArrayList<PrimaryCategory> primary_categorys = new ArrayList<PrimaryCategory>();
        for( int i = 0; i < response.length(); i++){
            try{
                primary_categorys.add(PrimaryCategory.fromJSONObject(response.getJSONObject(i)));
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        return primary_categorys;
    }

    public static void getAll(Context context, final Response.Listener success , Response.ErrorListener error){
        ModelGetter.getAll(
                context,
                API_PATH,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("Response: " + response.toString());
                        success.onResponse(PrimaryCategory.fromJSONArray(response));
                    }
                },
                error);
    }
}
