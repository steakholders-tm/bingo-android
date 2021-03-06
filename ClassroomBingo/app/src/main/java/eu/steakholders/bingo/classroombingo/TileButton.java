package eu.steakholders.bingo.classroombingo;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TileButton extends Button implements OnClickListener{

    private boolean isClicked = false;
    private int pressed_color = Color.MAGENTA;
    private TileButton otherTile;
    private GameActivity gameActivity;
    private int y;
    private int x;

    public TileButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TileButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TileButton(Context context) {
        super(context);
        init();
    }

    private void init(){
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.otherTile.click();
        click();
    }

    public void click(){

        if(isClicked){
            this.setBackgroundResource(R.drawable.button_tile);
            isClicked = false;
        }
        else{
            this.setBackgroundResource(R.drawable.button_tile_clicked);
            isClicked = true;
        }
        if(gameActivity != null){

            gameActivity.tileButtonPressed(this.x, this.y, isClicked);
        }
    }


    public void linkOtherTile(TileButton otherTile) {
        this.otherTile = otherTile;
    }

    public void setActivity(GameActivity activity) {
        this.gameActivity = activity;
    }

    public void setXY(int i, int j) {
        x = i;
        y = j;
    }
}
