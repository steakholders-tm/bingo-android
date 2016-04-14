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
    private String date;
    private int duration;
    private GameType gameType;
    private Place place;
    private PrimaryCategory primaryCategory;
    private SecondaryCategory secondaryCategory;
    private ArrayList<Tile> tiles;


    public Game(Context c) {
        super(c);
    }

    public Game(int id, String name, String date, int duration, GameType gameType, Place place, PrimaryCategory primaryCategory, SecondaryCategory secondaryCategory, ArrayList<Tile> tiles) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.gameType = gameType;
        this.place = place;
        this.primaryCategory = primaryCategory;
        this.secondaryCategory = secondaryCategory;
        this.tiles = tiles;
    }



    private static Game fromJSONObject(JSONObject response) {
        return new Game(
                response.optInt("id", -1),
                response.optString("name", ""),
                response.optString("date",""),
                response.optInt("duration",1),
                GameType.fromJSONObject(response.optJSONObject("game_type")),
                Place.fromJSONObject(response.optJSONObject("place")),
                PrimaryCategory.fromJSONObject(response.optJSONObject("primary_category")),
                SecondaryCategory.fromJSONObject(response.optJSONObject("secondary_category")),
                Tile.fromJSONArray(response.optJSONArray("tiles"))
        );
    }
    public static void getById(Context context, int id, final Response.Listener success , Response.ErrorListener error){
        ModelGetter.getById(
                context,
                API_PATH,
                id,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        success.onResponse(Game.fromJSONObject(response));
                    }
                },
                error);

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

                        success.onResponse(Game.fromJSONArray(response));
                    }
                },
                error);
    }
}
