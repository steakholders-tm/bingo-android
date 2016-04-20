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
            this.setBackgroundResource(android.R.drawable.btn_default);
            isClicked = false;
        }
        else {
            this.setBackgroundColor(pressed_color);
            isClicked = true;
        }
    }


    public void linkOtherTile(TileButton otherTile) {
        this.otherTile = otherTile;
    }
}
