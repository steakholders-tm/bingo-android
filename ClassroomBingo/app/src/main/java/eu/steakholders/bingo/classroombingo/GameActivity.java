package eu.steakholders.bingo.classroombingo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import eu.steakholders.bingo.api.Game;
import eu.steakholders.bingo.api.Tile;
import eu.steakholders.bingo.api.Winner;

public class GameActivity extends AppCompatActivity {

    private boolean overviewActive = false;
    private Animation openOverviewAnim;
    private Animation closeOverviewAnim;
    private FrameLayout overview;
    private String nickname;
    private Game game;
    private ArrayList<TileButton> buttonTiles;
    private ArrayList<ArrayList<Boolean>> clickedTiles;
    private boolean showTileNumbers = true;
    private Integer numberOfWinners = null;

    /**
     * Loads the board view, inflates the toolbar and starts the setup of the fab
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupFab();


        Intent intent = getIntent();
        String nickname = intent.getStringExtra(MainActivity.NICKNAME);
        Game game  = (Game) intent.getSerializableExtra(MainActivity.GAME_OBJECT);
        populateTiles(game);
        init(game, nickname);
        startGetWinners();

    }

    private void startGetWinners() {
        final Handler h = new Handler();
        final int delay_in_ms = 5000;

        h.postDelayed(new Runnable(){
            public void run(){
                getWinners();
                h.postDelayed(this, delay_in_ms);
            }
        }, delay_in_ms);
    }

    private void getWinners() {
        game.getWinners(this,  new Response.Listener<ArrayList<Winner>>() {
                    @Override
                    public void onResponse(ArrayList<Winner> winners) {
                        winnersCallBack(winners);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
    }

    private void winnersCallBack(ArrayList<Winner> winners) {
        if(numberOfWinners == null){
            numberOfWinners = winners.size();
        }
        if(numberOfWinners < winners.size()){
            Winner win = winners.get(winners.size() - 1);
            numberOfWinners = winners.size();
            if( win.getName() != nickname){
                Snackbar snackbar = Snackbar.make(overview, win.getName() + " got Bingo!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }


    /**
     * Shuffles game tiles and set gameTileButton texts to tile texts
     * @param game
     */
    private void populateTiles(Game game){
        buttonTiles = getTiles();
        ArrayList<Tile> tiles = game.getTiles();
        tiles = shuffleTiles(tiles);
        String prefix = "";

        for(int j = 0; j < buttonTiles.size(); j++){
            if(showTileNumbers){
                prefix = "" + (j + 1) + ". ";
            }
            buttonTiles.get(j).setText( prefix + tiles.get(j).getName());
        }
    }

    /**
     * Takes in game and nickname and sets it up for further
     * use in initializing the game
     * @param game
     * @param nickname
     */
    public void init( Game game, String nickname) {
        this.nickname = nickname;
        this.game = game;


    }

    /**
     *
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
     *
     * @param item
     * @return
     */
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

    /**
     * Maked the fab open and close an overview of the game
     */
    private void setupFab(){
        openOverviewAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.open_board_overview);
        closeOverviewAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.close_board_overview);
        overview = (FrameLayout) findViewById(R.id.overview);

        overview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                closeOverview();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (overviewActive) {
                    closeOverview();
                }
                else {
                    openOverview();
                }
            }
        });

    }

    /**
     * Handles the animation and visibility of the overview
     */
    private void openOverview (){
        overview.setVisibility(View.VISIBLE);
        overview.startAnimation(openOverviewAnim);
        overviewActive = true;
    }


    private void closeOverview (){
        overview.startAnimation(closeOverviewAnim);
        overview.setVisibility(View.GONE);
        overviewActive = false;
    }

    private ArrayList<Tile> shuffleTiles(ArrayList<Tile> tileList) {
        Collections.shuffle(tileList);
        return tileList;
    }

    /**
     *
     * @return
     */
    private ArrayList<TileButton> getTiles(){
        ArrayList<TileButton> tiles = new ArrayList<TileButton>();
        clickedTiles = new ArrayList<ArrayList<Boolean>>();
        for(int y = 0; y < 5; y++){
            clickedTiles.add(new ArrayList<Boolean>());
            for(int x = 0; x < 5; x++){
                String id = "tile" + y + x;
                String idMini = "tileSmall" + y + x;
                int resID = getResources().getIdentifier(id, "id", "eu.steakholders.bingo.classroombingo");
                int resIDMini = getResources().getIdentifier(idMini, "id", "eu.steakholders.bingo.classroombingo");
                TileButton otherTile = (TileButton) findViewById(resIDMini);
                TileButton bigTile = (TileButton) findViewById(resID);
                bigTile.setXY(x,y);
                bigTile.linkOtherTile(otherTile);
                otherTile.linkOtherTile(bigTile);
                if(showTileNumbers){
                    otherTile.setText("" + ((y*5)+(x+1)));
                }
                tiles.add(bigTile);
                clickedTiles.get(y).add(false);
            }
        }
        connectButtonTilesToGameLogic(tiles);
        return tiles;
    }

    /**
     * Gives tiles a reference to game activity so that tiles can call
     * @param tileButtons
     */
    private void connectButtonTilesToGameLogic(ArrayList<TileButton> tileButtons) {
        for(int i = 0; i < tileButtons.size(); i++){
            tileButtons.get(i).setActivity(this);
        }
    }

    public void tileButtonPressed(int x, int y, Boolean isClicked) {
        clickedTiles.get(y).set(x, isClicked);
        boolean incolumn = true;
        boolean inrow = true;

        ArrayList<Boolean> row = clickedTiles.get(y);
        for(int i = 0; i < 5; i++){
            if( !row.get(i)){
                inrow= false;
            }
            if(!clickedTiles.get(i).get(x)){
                incolumn = false;
            }
        }
        if( incolumn || inrow ){
            Snackbar snackbar = Snackbar.make(overview, "You ("+nickname+ ") won!", Snackbar.LENGTH_LONG);
            snackbar.show();
            Winner you = new Winner(nickname,game.getId());
            you.save(this,  new Response.Listener<Object>() {
                        @Override
                        public void onResponse(Object object) {


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
        }
    }

    /**
     * Override back button to work with fragments
     */
    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setTitle("Exit game?")
                .setMessage("Are you sure you want to exit the game?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .show();
    }
}
