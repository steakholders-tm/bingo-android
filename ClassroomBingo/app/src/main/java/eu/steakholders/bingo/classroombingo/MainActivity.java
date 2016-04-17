package eu.steakholders.bingo.classroombingo;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.android.volley.*;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import eu.steakholders.bingo.api.Game;
import eu.steakholders.bingo.api.GameType;
import eu.steakholders.bingo.api.Place;
import eu.steakholders.bingo.api.PrimaryCategory;
import eu.steakholders.bingo.api.SecondaryCategory;
import eu.steakholders.bingo.api.Tile;
import eu.steakholders.bingo.api.Winner;

public class MainActivity extends AppCompatActivity {

    //https://steakholders.eu/api-docs/v1/api-docs

    //Fragments
    private JoinGameFragment joinGameFragment;
    private CreateGameFragment createGameFragment;

    //Layout variable
    private RelativeLayout mainPage;

    //Elements in layout
    private Spinner gameTypeSpinner;
    private Spinner objectSpinner;
    private Spinner primaryCatSpinner;
    private Spinner secondaryCatSpinner;

    //Selected game to join
    private String gameName;

    //Spinner arrays
    private String array1[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Init layout variable
        mainPage = (RelativeLayout) findViewById(R.id.main_frame);

        //Init spinner variables
        gameTypeSpinner = (Spinner) findViewById(R.id.spinner_gt);
        objectSpinner = (Spinner) findViewById(R.id.spinner_place);
        primaryCatSpinner = (Spinner) findViewById(R.id.spinner_pc);
        secondaryCatSpinner = (Spinner) findViewById(R.id.spinner_sc);
/*
        Place.getById(this,1,  new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        System.out.println("Response: " + object.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        Place.getAll(this, new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        System.out.println("Response: " + object.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        Game.getById(this,1,  new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        System.out.println("Response: " + object.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        Game.getAll(this,  new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        System.out.println("Response: " + object.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        GameType.getById(this,1,  new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        System.out.println("Response: " + object.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        GameType.getAll(this,  new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        System.out.println("Response: " + object.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        Tile.getById(this,1,  new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        System.out.println("Response: " + object.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        Tile.getAll(this,  new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        System.out.println("Response: " + object.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        Winner.getById(this,1,  new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        System.out.println("Response: " + object.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        Winner.getAll(this,  new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        System.out.println("Response: " + object.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        PrimaryCategory.getById(this,1,  new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        System.out.println("Response: " + object.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        PrimaryCategory.getAll(this,  new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        System.out.println("Response: " + object.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        SecondaryCategory.getById(this,1,  new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        System.out.println("Response: " + object.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        SecondaryCategory.getAll(this,  new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        System.out.println("Response: " + object.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });*/
       /* Game game = new Game(1,"Tetst fromjava", "1993-12-27", "13:37",1,1,1,1,1,null );

        game.save(this,  new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        System.out.println("Response: " + object.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.networkResponse);
                        System.out.println(new String(error.networkResponse.data));
                        System.out.println(error.getLocalizedMessage());
                    }
                });*/

        Winner winner = new Winner("Dag", 1, "2016-04-13T20:58:19Z");
        winner.save(this,  new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        System.out.println("Response: " + object.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToJoinGame(View view){
        //TODO add check for fields if filled out


        removeFragment(view);
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container_main) != null) {
            hideMain();

            // Create a new Fragment to be objectd in the activity layout
            joinGameFragment = new JoinGameFragment();


            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            joinGameFragment.setArguments(getIntent().getExtras());


            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_main, joinGameFragment).addToBackStack("join").commit();

        }

    }

    public void joinGame(View view){
        //TODO join game at server
    }

    public void goToCreateGame(View view){
        //TODO add check for fields if filled out


        removeFragment(view);
        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container_main) != null) {
            hideMain();

            // Create a new Fragment to be objectd in the activity layout
            createGameFragment = new CreateGameFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            createGameFragment.setArguments(getIntent().getExtras());


            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_main, createGameFragment).addToBackStack("create").commit();

        }

    }

    public void newGame(View view){
        //TODO join and create game to server
    }


    public void removeFragment(View view){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_main);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            showMain();
        }
    }

    public void hideMain(){
        if (mainPage.getVisibility() == View.VISIBLE){
            mainPage.setVisibility(View.INVISIBLE);
        }
    }

    public void showMain(){
        if(mainPage.getVisibility() == View.INVISIBLE){
            mainPage.setVisibility(View.VISIBLE);
        }
    }

    public void addGameTypes(){
        //TODO add gametypes to spinner
    }

    public void addPlaces(){
        //TODO add objects to spinner
    }

    public void addPrimary(){
        //TODO add pc to spinner
    }

    public void addSecondary(){
        //TODO add sc to spinner
    }

    public void populateGameList(){
        //TODO populate gameList with items from server based on spinners
    }

    public void setSelectedName(){
        //TODO set gameName = selected existing game
    }

    @Override
    public void onBackPressed(){
        //Override back button for now
        if(getSupportFragmentManager().getBackStackEntryCount() == 0){
            return;
        }
        showMain();
        super.onBackPressed();
    }

}
