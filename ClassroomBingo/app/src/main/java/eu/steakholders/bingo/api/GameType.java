package eu.steakholders.bingo.api;

import android.content.Context;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GameType extends ModelGetter {


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

    protected static String API_PATH = "game-type";
    private int id;
    private String name;
    private String description;


    public GameType(Context c) {
        super(c);
    }

    public GameType(int id, String name, String description){
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

                        success.onResponse(GameType.fromJSONObject(response));
                    }
                },
                error);

    }

    public static GameType fromJSONObject(JSONObject response) {
        if(response == null){
            return null;
        }
        return new GameType(
                response.optInt("id", -1),
                response.optString("name", ""),
                response.optString("description","")
        );
    }

    public static ArrayList<GameType> fromJSONArray(JSONArray response) {
        if(response == null){
            return null;
        }
        ArrayList<GameType> game_types = new ArrayList<GameType>();
        for( int i = 0; i < response.length(); i++){
            try{
                game_types.add(GameType.fromJSONObject(response.getJSONObject(i)));
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        return game_types;
    }

    public static void getAll(Context context, final Response.Listener success , Response.ErrorListener error){
        ModelGetter.getAll(
                context,
                API_PATH,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        success.onResponse(GameType.fromJSONArray(response));
                    }
                },
                error);
    }
}
