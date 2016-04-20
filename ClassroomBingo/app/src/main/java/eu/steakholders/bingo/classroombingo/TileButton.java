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

        if(isClicked){
            this.setBackgroundResource(R.drawable.button_tile);
            //this.setbac
            isClicked = false;
        }
        else{
            this.setBackgroundResource(R.drawable.button_tile_clicked);
            //this.setBackgroundColor(pressed_color);
            isClicked = true;
        }
    }


}
