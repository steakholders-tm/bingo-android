package eu.steakholders.bingo.classroombingo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

//https://steakholders.eu/api/v1/?format=api
//https://steakholders.eu/api-docs/v1/api-docs

import java.util.ArrayList;

import eu.steakholders.bingo.api.Game;
import eu.steakholders.bingo.api.GameType;
import eu.steakholders.bingo.api.Place;
import eu.steakholders.bingo.api.PrimaryCategory;
import eu.steakholders.bingo.api.SecondaryCategory;

public class MainActivity extends AppCompatActivity {

    //Fragments
    private JoinGameFragment joinGameFragment;
    private CreateGameFragment createGameFragment;

    //Layout variable
    private RelativeLayout mainPage;

    //Elements in layout
    private Spinner gameTypeSpinner;
    private Spinner placeSpinner;
    private Spinner primaryCatSpinner;
    private Spinner secondaryCatSpinner;
    private ListView existingGames;

    //ArrayAdapter for spinners
    private ArrayAdapter<String> adapter;

    //Selected game to join
    private String gameName;

    //Lists
    private List<Object> gameTypesList;
    private List<Object> placesList;
    private List<Object> primaryList;
    private List<Object> secondaryList;
    private List<Object> existingGamesList;

    //Spinner arrays
    private List<String> placesNames;
    private List<String> gameNames;
    private List<String> primaryNames;
    private List<String> secondaryNames;
    private List<String> existingGamesNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Init layout variable
        mainPage = (RelativeLayout) findViewById(R.id.main_frame);

        //Init lists
        placesNames = new ArrayList<>();
        gameNames = new ArrayList<>();
        primaryNames = new ArrayList<>();
        secondaryNames = new ArrayList<>();
        existingGamesNames = new ArrayList<>();

        secondaryNames.add("None");

        //Init spinner variables
        gameTypeSpinner = (Spinner) findViewById(R.id.spinner_gt);
        placeSpinner = (Spinner) findViewById(R.id.spinner_place);
        primaryCatSpinner = (Spinner) findViewById(R.id.spinner_pc);
        secondaryCatSpinner = (Spinner) findViewById(R.id.spinner_sc);

        existingGames = (ListView) findViewById(R.id.existingGameList);

        existingGames.setOnItemClickListener(new ExistingGameListListener());

        //Adding stuff to spinners and lists
        addGameTypes(this);
        addPlaces(this);
        addPrimary(this);
        addSecondary(this);
        populateGameList(this);

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
    }



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
/*
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
                        System.out.println(new String(error.networkResponse.data));

                    }
                });*/

    private class ExistingGameListListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            setSelectedGameName((String) existingGames.getItemAtPosition(position));
        }
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
        if(checkFields("join")){
            removeFragment(view);
            // Check that the activity is using the layout version with
            // the fragment_container FrameLayout
            if (findViewById(R.id.fragment_container_main) != null) {
                hideMain();

                // Create a new Fragment to be placed in the activity layout
                joinGameFragment = new JoinGameFragment();



                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                joinGameFragment.setArguments(getIntent().getExtras());

                joinGameFragment.setGameName(gameName);

                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container_main, joinGameFragment).addToBackStack("join").commit();

            }
        }

    }

    public void joinGame(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("GAME_NAME", gameName);
        /* Not sure what other data needed to send
        intent.putExtra();
        intent.putExtra();
        intent.putExtra();
        intent.putExtra();
        */
        startActivity(intent);
    }

    public void goToCreateGame(View view){
        if(checkFields("create")){
            removeFragment(view);
            // Check that the activity is using the layout version with
            // the fragment_container FrameLayout
            if (findViewById(R.id.fragment_container_main) != null) {
                hideMain();

                // Create a new Fragment to be placed in the activity layout
                createGameFragment = new CreateGameFragment();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                createGameFragment.setArguments(getIntent().getExtras());


                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container_main, createGameFragment).addToBackStack("create").commit();

            }
        }

    }

    public void newGame(View view){
        //TODO join and create game to server


        joinGame(view);
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

    public void addGameTypes(final Context context){
        GameType.getAll(this, new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        gameTypesList = (ArrayList<Object>) object;
                        for(Object o: gameTypesList){
                            GameType temp = (GameType) o;
                            gameNames.add(temp.getName());
                            adapter = new ArrayAdapter<String>(context,
                                    android.R.layout.simple_spinner_item, gameNames);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            gameTypeSpinner.setAdapter(adapter);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
    }

    public void addPlaces(final Context context){
        Place.getAll(this, new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        placesList = (ArrayList<Object>) object;
                        for(Object o: placesList){
                            Place temp = (Place) o;
                            placesNames.add(temp.getName());
                            adapter = new ArrayAdapter<String>(context,
                                    android.R.layout.simple_spinner_item, placesNames);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            placeSpinner.setAdapter(adapter);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
    }

    public void addPrimary(final Context context){
        PrimaryCategory.getAll(this, new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        primaryList = (ArrayList<Object>) object;
                        for(Object o: primaryList){
                            PrimaryCategory temp = (PrimaryCategory) o;
                            primaryNames.add(temp.getName());
                            adapter = new ArrayAdapter<String>(context,
                                    android.R.layout.simple_spinner_item, primaryNames);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            primaryCatSpinner.setAdapter(adapter);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
    }

    public void addSecondary(final Context context){
        SecondaryCategory.getAll(this, new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        secondaryList = (ArrayList<Object>) object;
                        for(Object o: secondaryList){
                            SecondaryCategory temp = (SecondaryCategory) o;
                            secondaryNames.add(temp.getName());
                            adapter = new ArrayAdapter<String>(context,
                                    android.R.layout.simple_spinner_item, secondaryNames);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            secondaryCatSpinner.setAdapter(adapter);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
    }

    public void populateGameList(final Context context){
        Game.getAll(this, new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        existingGamesList = (ArrayList<Object>) object;
                        for(Object o: existingGamesList){
                            Game temp = (Game) o;
                            existingGamesNames.add(temp.getName());
                            adapter = new ArrayAdapter<String>(context,
                                    android.R.layout.simple_list_item_1, existingGamesNames);
                            existingGames.setAdapter(adapter);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
    }

    public void setSelectedGameName(String gameName){
        this.gameName = gameName;
    }


    //Check all the spinners if they are empty
    public boolean checkFields(String button){
        if(button.equals("create")){
            return true;
        }else if(button.equals("join")){
            if(gameName != null){
                return true;
            }
        }else{
            System.out.println("Something went wrong");
        }
        Snackbar snackbar = Snackbar
                .make(mainPage, "Did you select a game to join? If none exists consider creating your own!", Snackbar.LENGTH_LONG);

        snackbar.show();
        return false;
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
