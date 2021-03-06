package eu.steakholders.bingo.api;


import android.content.Context;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Game extends ModelGetterAndSetter implements Serializable{
    protected static String API_PATH = "games";
    protected  static String FILTER = "game=";
    private int id;
    private String name;
    private String date;
    private String time;
    private int duration;
    private int gameTypeId;
    private int placeId;
    private int primaryCategoryId;
    private int secondaryCategoryId;
    private ArrayList<Tile> tiles;


    public Game(Context c) {
        super(c);
    }

    public Game(int id, String name, String date, String time, int duration, int gameTypeId, int placeId, int primaryCategoryId, int secondaryCategoryId, ArrayList<Tile> tiles) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.gameTypeId = gameTypeId;
        this.placeId = placeId;
        this.primaryCategoryId = primaryCategoryId;
        this.secondaryCategoryId = secondaryCategoryId;
        this.tiles = tiles;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getGameTypeId() {
        return gameTypeId;
    }

    public void setGameTypeId(int gameTypeId) {
        this.gameTypeId = gameTypeId;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public int getPrimaryCategoryId() {
        return primaryCategoryId;
    }

    public void setPrimaryCategoryId(int primaryCategoryId) {
        this.primaryCategoryId = primaryCategoryId;
    }

    public int getSecondaryCategoryId() {
        return secondaryCategoryId;
    }

    public void setSecondaryCategoryId(int secondaryCategoryId) {
        this.secondaryCategoryId = secondaryCategoryId;
    }


    private JSONObject toJSONObject(){
        JSONObject jOjct = new JSONObject();
        try {
            jOjct.put("name",this.name);
            jOjct.put("date", this.date);
            jOjct.put("time",this.time);
            jOjct.put("duration",this.duration);
            jOjct.put("game_type",this.gameTypeId);
            jOjct.put("place",this.placeId);
            jOjct.put("primary_category",this.primaryCategoryId);
            jOjct.put("secondary_category",this.secondaryCategoryId);
            jOjct.put("tiles",this.tiles);
            jOjct.put("active",true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jOjct;
    }

    public void save(Context context, Response.Listener success, Response.ErrorListener error){
        ModelGetterAndSetter.create(context, API_PATH,this.toJSONObject(),success, error);
    }

    public void getWinners(Context context, final Response.Listener success, Response.ErrorListener error){
        ModelGetter.getAll(context,Winner.API_PATH,FILTER,id,this.toJSONObject(),

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        success.onResponse(Winner.fromJSONArray(response));
                    }
                }, error);
    }


    protected static Game fromJSONObject(JSONObject response) {
        return new Game(
                response.optInt("id", -1),
                response.optString("name", ""),
                response.optString("date",""),
                response.optString("time",""),
                response.optInt("duration",1),
                response.optInt("game_type", -1),
                response.optInt("place", -1),
                response.optInt("primary_category", -1),
                response.optInt("secondary_category", -1),
                tilesArrayListFromJSONArray(response.optJSONArray("tiles"))
        );
    }

    private static ArrayList<Tile> tilesArrayListFromJSONArray(JSONArray tiles) {
        System.out.println("tile");
        System.out.println(tiles);
        ArrayList<Tile> tiles_n = new ArrayList<Tile>();
        if(tiles != null){
            for(int i = 0; i < tiles.length(); i++){
                try{
                    System.out.print(tiles.getJSONObject(i).optString("name"));
                    tiles_n.add(new Tile(tiles.getJSONObject(i).optInt("id"),tiles.getJSONObject(i).optString("name")));
                }
                catch(Exception e){
                    System.out.println(e);
                    System.out.println("tile failed");
                }
            }
        }
        return tiles_n;
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
    public static ArrayList<Game> fromJSONArray(JSONArray response) {
        System.out.println("from jsn array");
        ArrayList<Game> pames = new ArrayList<Game>();
        for( int i = 0; i < response.length(); i++){
            try{
                pames.add(Game.fromJSONObject(response.getJSONObject(i)));
            }
            catch (Exception e){
                System.out.println(e);
                System.out.println("Game from json array failed");
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

    public ArrayList<Tile> getTiles() {
        System.out.println("get tiles");
        return tiles;
    }
}
