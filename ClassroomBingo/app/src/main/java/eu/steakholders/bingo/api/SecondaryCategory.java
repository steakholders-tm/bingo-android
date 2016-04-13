package eu.steakholders.bingo.api;

import android.content.Context;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SecondaryCategory extends ModelGetter{
    protected static String API_PATH = "secondary-categories";
    private int id;
    private String name;
    private String description;


    public SecondaryCategory(Context c) {
        super(c);
    }

    public SecondaryCategory(int id, String name, String description){
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
                        success.onResponse(SecondaryCategory.fromJSONObject(response));
                    }
                },
                error);

    }

    private static SecondaryCategory fromJSONObject(JSONObject response) {
        return new SecondaryCategory(
                response.optInt("id", -1),
                response.optString("name", ""),
                response.optString("description","")
        );
    }

    private static ArrayList<SecondaryCategory> fromJSONArray(JSONArray response) {
        ArrayList<SecondaryCategory> secondary_categorys = new ArrayList<SecondaryCategory>();
        for( int i = 0; i < response.length(); i++){
            try{
                secondary_categorys.add(SecondaryCategory.fromJSONObject(response.getJSONObject(i)));
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        return secondary_categorys;
    }

    public static void getAll(Context context, final Response.Listener success , Response.ErrorListener error){
        ModelGetter.getAll(
                context,
                API_PATH,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("Response: " + response.toString());
                        success.onResponse(SecondaryCategory.fromJSONArray(response));
                    }
                },
                error);
    }
}
