package eu.steakholders.bingo.classroombingo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.util.HashMap;
import java.util.List;

//https://steakholders.eu/api/v1/?format=api
//https://steakholders.eu/api-docs/v1/api-docs

import java.util.ArrayList;
import java.util.Map;

import eu.steakholders.bingo.api.Game;
import eu.steakholders.bingo.api.GameType;
import eu.steakholders.bingo.api.Place;
import eu.steakholders.bingo.api.PrimaryCategory;
import eu.steakholders.bingo.api.SecondaryCategory;

public class MainActivity extends AppCompatActivity {
    //Debug TAG for log
    private static final String TAG = "DEBUG";

    //Fragments
    private JoinGameFragment joinGameFragment;
    private CreateGameFragment createGameFragment;

    public static final String  GAME_OBJECT = "gameobject";
    public static final String NICKNAME = "nickname";

    //Layout variable
    private RelativeLayout mainPage;

    //Game object selected
    private Game gameObject;
    private String nickname;

    //Elements in layout
    private Spinner gameTypeSpinner;
    private Spinner placeSpinner;
    private Spinner primaryCatSpinner;
    private Spinner secondaryCatSpinner;
    private ListView existingGamesListView;

    //ArrayAdapter for spinners
    private ArrayAdapter<String> gameTypeAdapter;
    private ArrayAdapter<String> placeAdapter;
    private ArrayAdapter<String> primaryAdapter;
    private ArrayAdapter<String> secondaryAdapter;
    private ArrayAdapter<String> gameListAdapter;

    //Selected game to join
    private String gameName;

    //Lists
    private List<Object> gameTypesList;
    private List<Object> placesList;
    private List<Object> primaryList;
    private List<Object> secondaryList;
    private List<Object> existingGameList;

    //Spinner arrays
    private List<String> placesNames;
    private List<String> gameNames;
    private List<String> primaryNames;
    private List<String> secondaryNames;
    private List<Game> existingGames;
    private List<String> filteredGameNames;

    //Mapping from name to id
    private Map<String, Integer> gameTypeMap;
    private Map<String, Integer> placesMap;
    private Map<String, Integer> primaryMap;
    private Map<String, Integer> secondaryMap;
    private Map<String, Game> existingGamesMap;

    //Selected items in spinners
    private String selectedGameType;
    private String selectedPlace;
    private String selectedPrimary;
    private String selectedSecondary;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_splash);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                init();
            }
        }, 3000);
    }

    /**
     * Initializes all dropdowns, lists, maps and gets info from the server
     */
    private void init()  {


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
        existingGames = new ArrayList<>();
        //filteredGameNames = new ArrayList<>();

        //Init maps
        gameTypeMap = new HashMap<>();
        placesMap = new HashMap<>();
        primaryMap = new HashMap<>();
        secondaryMap = new HashMap<>();
        existingGamesMap = new HashMap<>();

        //Init spinner variables
        gameTypeSpinner = (Spinner) findViewById(R.id.spinner_gt);
        placeSpinner = (Spinner) findViewById(R.id.spinner_place);
        primaryCatSpinner = (Spinner) findViewById(R.id.spinner_pc);
        secondaryCatSpinner = (Spinner) findViewById(R.id.spinner_sc);


        gameTypeSpinner.setOnItemSelectedListener(new UpdateGameFilterListener());
        placeSpinner.setOnItemSelectedListener(new UpdateGameFilterListener());
        primaryCatSpinner.setOnItemSelectedListener(new UpdateGameFilterListener());
        secondaryCatSpinner.setOnItemSelectedListener(new UpdateGameFilterListener());

        existingGamesListView = (ListView) findViewById(R.id.existingGameList);

        existingGamesListView.setOnItemClickListener(new ExistingGameListListener());

        //Adding stuff to spinners and lists
        addGameTypes(this);
        addPlaces(this);
        addPrimary(this);
        addSecondary(this);
        getGameList(this);

    }


    /**
     * Private listener class for game listView
     */
    private class ExistingGameListListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            setSelectedGameInfo((String) existingGamesListView.getItemAtPosition(position));
        }
    }

    /**
     * Private listener class for spinners to update gameList
     */
    private class UpdateGameFilterListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(gameTypeSpinner != null && gameTypeSpinner.getSelectedItem() != null &&
                    placeSpinner != null && placeSpinner.getSelectedItem() != null &&
                    primaryCatSpinner != null && primaryCatSpinner.getSelectedItem() != null &&
                    secondaryCatSpinner != null && secondaryCatSpinner.getSelectedItem() != null){
                filterGames();
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            filterGames();
        }
    }

    /**
     * Inflates menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button,
     * so long as you specify a parent activity in AndroidManifest.xml.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Initialize joinGameFragment and hides main
     * @param view
     */
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

    /**
     * Handles click for joinGameFragment JOIN GAME button and moves to GameActivity
     * @param view
     */
    public void joinGame(View view){
        joinGameFragment.setNickName();
        if(joinGameFragment.getFlag()){
            nickname = joinGameFragment.getNickName();
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra(MainActivity.GAME_OBJECT, gameObject);
            intent.putExtra(MainActivity.NICKNAME, nickname);
            startActivity(intent);
        }else{
            Snackbar snackbar = Snackbar
                    .make(mainPage, "Did you add a nickname?", Snackbar.LENGTH_LONG);

            snackbar.show();
        }

    }

    /**
     * Initialize createGameFragment and hides main
     * @param view
     */
    public void goToCreateGame(View view){
        if(checkFields("create")){
            removeFragment(view);
            // Check that the activity is using the layout version with
            // the fragment_container FrameLayout
            if (findViewById(R.id.fragment_container_main) != null) {
                hideMain();

                // Create a new Fragment to be placed in the activity layout
                createGameFragment = new CreateGameFragment();

                //Transfer data
                createGameFragment.setFlag();
                createGameFragment.setGameNames(gameNames);
                createGameFragment.setPlacesNames(placesNames);
                createGameFragment.setPrimaryNames(primaryNames);
                createGameFragment.setSecondaryNames(secondaryNames);
                createGameFragment.setGameTypeMap(gameTypeMap);
                createGameFragment.setPlacesMap(placesMap);
                createGameFragment.setPrimaryMap(primaryMap);
                createGameFragment.setSecondaryMap(secondaryMap);

                createGameFragment.setSelectedGameTypePos(gameTypeAdapter.getPosition(gameTypeSpinner.getSelectedItem().toString()));
                createGameFragment.setSelectedPlacePos(placeAdapter.getPosition(placeSpinner.getSelectedItem().toString()));
                createGameFragment.setSelectedPrimaryPos(primaryAdapter.getPosition(primaryCatSpinner.getSelectedItem().toString()));
                createGameFragment.setSelectedSecondaryPos(secondaryAdapter.getPosition(secondaryCatSpinner.getSelectedItem().toString()));

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                createGameFragment.setArguments(getIntent().getExtras());


                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container_main, createGameFragment).addToBackStack("create").commit();

            }
        }

    }

    /**
     * Creates new game and sends to server, then runs joinGame()
     * @param view
     */
    public void newGame(final View view){

        createGameFragment.makeGame();

        if(createGameFragment.getFlag()){
            gameObject = createGameFragment.getGame();
            gameName = gameObject.getName();
            gameObject.save(this,  new Response.Listener<Object>() {
                        @Override
                        public void onResponse(Object object) {
                            gameObject = (Game) object;
                            Log.d(TAG, object.toString());
                            goToJoinGame(view);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, error.toString());
                            Snackbar snackbar = Snackbar
                                    .make(mainPage, "Please fill in all the fields correctly!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    });
            getGameList(this);

        }

    }

    /**
     * Removes a fragment and shows main
     * @param view
     */
    public void removeFragment(View view){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_main);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            showMain();
        }
    }

    /**
     * Method to hide main
     */
    public void hideMain(){
        if (mainPage.getVisibility() == View.VISIBLE){
            mainPage.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Method to show main
     */
    public void showMain(){
        if(mainPage.getVisibility() == View.INVISIBLE){
            mainPage.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Get and Add gameTypes to dropdown
     * @param context
     */
    @SuppressWarnings("unchecked")
    public void addGameTypes(final Context context){
        GameType.getAll(this, new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        gameTypesList = (ArrayList<Object>) object;
                        for(Object o: gameTypesList){
                            GameType temp = (GameType) o;
                            gameNames.add(temp.getName());
                            gameTypeMap.put(temp.getName(), temp.getId());
                            gameTypeAdapter = new ArrayAdapter<>(context,
                                    android.R.layout.simple_spinner_item, gameNames);
                            gameTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            gameTypeSpinner.setAdapter(gameTypeAdapter);
                            //Log.d(TAG, gameTypeMap.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                    }
                });
    }

    /**
     * Get and Add places to dropdown
     * @param context
     */
    @SuppressWarnings("unchecked")
    public void addPlaces(final Context context){
        Place.getAll(this, new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        placesList = (ArrayList<Object>) object;
                        for(Object o: placesList){
                            Place temp = (Place) o;
                            placesNames.add(temp.getName());
                            placesMap.put(temp.getName(), temp.getId());
                            placeAdapter = new ArrayAdapter<>(context,
                                    android.R.layout.simple_spinner_item, placesNames);
                            placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            placeSpinner.setAdapter(placeAdapter);
                            //Log.d(TAG, placesMap.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                    }
                });
    }

    /**
     * Get and Add primary category to dropdown
     * @param context
     */
    @SuppressWarnings("unchecked")
    public void addPrimary(final Context context){
        PrimaryCategory.getAll(this, new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        primaryList = (ArrayList<Object>) object;
                        for(Object o: primaryList){
                            PrimaryCategory temp = (PrimaryCategory) o;
                            primaryNames.add(temp.getName());
                            primaryMap.put(temp.getName(), temp.getId());
                            primaryAdapter = new ArrayAdapter<>(context,
                                    android.R.layout.simple_spinner_item, primaryNames);
                            primaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            primaryCatSpinner.setAdapter(primaryAdapter);
                            //Log.d(TAG, primaryMap.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                    }
                });
    }

    /**
     * Get and Add secondary category to dropdown
     * @param context
     */
    @SuppressWarnings("unchecked")
    public void addSecondary(final Context context){
        SecondaryCategory.getAll(this, new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        secondaryList = (ArrayList<Object>) object;
                        for(Object o: secondaryList){
                            SecondaryCategory temp = (SecondaryCategory) o;
                            secondaryNames.add(temp.getName());
                            secondaryMap.put(temp.getName(), temp.getId());
                            secondaryAdapter = new ArrayAdapter<>(context,
                                    android.R.layout.simple_spinner_item, secondaryNames);
                            secondaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            secondaryCatSpinner.setAdapter(secondaryAdapter);
                            //Log.d(TAG, secondaryMap.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                    }
                });

    }

    /**
     * Get and Add games to list
     * @param context
     */
    @SuppressWarnings("unchecked")
    public void getGameList(final Context context){
        existingGameList = new ArrayList<>();
        existingGames = new ArrayList<>();
        existingGamesMap = new HashMap<>();
        Game.getAll(this, new Response.Listener<Object>() {
                    @Override
                    public void onResponse(Object object) {
                        existingGameList = (ArrayList<Object>) object;
                        for(Object o: existingGameList){
                            Game temp = (Game) o;
                            existingGames.add(temp);
                            existingGamesMap.put(temp.getName(), temp);
                            //Log.d(TAG, existingGames.toString());
                            if(gameTypeSpinner != null && gameTypeSpinner.getSelectedItem() != null &&
                                    placeSpinner != null && placeSpinner.getSelectedItem() != null &&
                                    primaryCatSpinner != null && primaryCatSpinner.getSelectedItem() != null &&
                                    secondaryCatSpinner != null && secondaryCatSpinner.getSelectedItem() != null){
                                filterGames();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                    }
                });
    }

    /**
     *Gets all values from spinners and updates game list based on those
     */
    public void filterGames(){
        filteredGameNames = new ArrayList<>();
        selectedGameType = gameTypeSpinner.getSelectedItem().toString();
        selectedPlace = placeSpinner.getSelectedItem().toString();
        selectedPrimary = primaryCatSpinner.getSelectedItem().toString();
        selectedSecondary = secondaryCatSpinner.getSelectedItem().toString();

        int gameTypeID = gameTypeMap.get(selectedGameType);
        int placeID = placesMap.get(selectedPlace);
        int primaryID = primaryMap.get(selectedPrimary);
        int secondaryID = secondaryMap.get(selectedSecondary);

        if (secondaryID == 1){
            for(Game g: existingGames){
                if(g.getPlaceId() == placeID &&
                        g.getGameTypeId() == gameTypeID &&
                        g.getPrimaryCategoryId() == primaryID){
                    filteredGameNames.add(g.getName());
                }
            }
        }else{
            for(Game g: existingGames){
                if(g.getPlaceId() == placeID &&
                        g.getGameTypeId() == gameTypeID &&
                        g.getPrimaryCategoryId() == primaryID &&
                        g.getSecondaryCategoryId() == secondaryID ){
                    filteredGameNames.add(g.getName());
                }
            }
        }


        gameListAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, filteredGameNames);
        existingGamesListView.setAdapter(gameListAdapter);
    }

    /**
     * Get game info from selected game in game list
     * @param gameName
     */
    public void setSelectedGameInfo(String gameName){
        this.gameName = gameName;
        this.gameObject = existingGamesMap.get(gameName);
    }

    /**
     * Check if any info is missing
     * @param button = string on buttons
     * @return
     */
    public boolean checkFields(String button){
        switch (button) {
            case "create":
                return true;
            case "join":
                if (gameName != null) {
                    return true;
                }
                break;
            default:
                System.out.println("Something went wrong");
                break;
        }
        Snackbar snackbar = Snackbar
                .make(mainPage, "Did you select a game to join? If none exists consider creating your own!", Snackbar.LENGTH_LONG);

        snackbar.show();
        return false;
    }

    /**
     * Override back button to work with fragments
     */
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
