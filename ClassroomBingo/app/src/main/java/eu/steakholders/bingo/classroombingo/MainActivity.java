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

import java.util.List;

//https://steakholders.eu/api/v1/?format=api
//https://steakholders.eu/api-docs/v1/api-docs

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

    //ArrayAdapter for spinners
    private ArrayAdapter<String> adapter;

    //Selected game to join
    private String gameName;

    //Spinner arrays
    private List<String> gameTypesList;
    private List<String> placesList;
    private List<String> primaryList;
    private List<String> secondaryList;


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
        placeSpinner = (Spinner) findViewById(R.id.spinner_place);
        primaryCatSpinner = (Spinner) findViewById(R.id.spinner_pc);
        secondaryCatSpinner = (Spinner) findViewById(R.id.spinner_sc);

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

            // Create a new Fragment to be placed in the activity layout
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
        //TODO add places to spinner
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
