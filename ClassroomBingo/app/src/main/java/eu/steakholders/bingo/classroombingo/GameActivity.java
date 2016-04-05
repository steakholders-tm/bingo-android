package eu.steakholders.bingo.classroombingo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

public class GameActivity extends AppCompatActivity {

    private boolean overviewActive = false;
    private Animation openOverviewAnim;
    private Animation closeOverviewAnim;
    private FrameLayout overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupFab();

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

    private void openOverview (){
        overview.startAnimation(openOverviewAnim);
        overviewActive = true;
        overview.setClickable(true);
    }

    private void closeOverview (){
        overview.startAnimation(closeOverviewAnim);
        //overview.setVisibility(View.INVISIBLE);
        overviewActive = false;
        overview.setClickable(false);
    }

}
