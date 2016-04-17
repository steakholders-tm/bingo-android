package eu.steakholders.bingo.api;

import android.content.Context;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import eu.steakholders.bingo.classroombingo.MainActivity;

public class Winner extends ModelGetterAndSetter {
    protected static String API_PATH = "winners";
    private int id;
    private String name;
    private int gameId;
    private String time;

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


    public Winner(int id, String name, int gameId, String time){
        this.id = id;
        this.name = name;
        this.gameId = gameId;
        this.time = time;
    }


    public Winner(String name, int gameId, String time){
        this.name = name;
        this.gameId = gameId;
        this.time = time;
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
                response.optString("name", ""),
                response.optInt("gameId", -1),
                response.optString("time", "")
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

    private JSONObject toJSONObject(){
        JSONObject jOjct = new JSONObject();
        try {
            jOjct.put("name",this.name);
            jOjct.put("gameId",this.gameId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jOjct;
    }

    public void save(Context context, Response.Listener success, Response.ErrorListener error){
        ModelGetterAndSetter.create(context, API_PATH,this.toJSONObject(),success, error);
    }
}
