package eu.steakholders.bingo.classroombingo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class JoinGameFragment extends Fragment {

    private static boolean JOIN_FLAG = false;

    //Fields
    private TextView gameNameView;
    private EditText nickNameView;
    private RelativeLayout parent;

    //Variables
    private String gameName;
    private String nickName;

    public JoinGameFragment() {
        // Required empty public constructor
    }

    /**
     * Creates and inflates fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Get view
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_join_game, null);

        gameNameView = (TextView) view.findViewById(R.id.selected_game_name);
        nickNameView = (EditText) view.findViewById(R.id.nickname_input);
        parent = (RelativeLayout) view.findViewById(R.id.parent);

        parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                UtilityClass.hideSoftKeyboard(getActivity());
                return false;
            }
        });

        gameNameView.setText(gameName);

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Sets game name from selected or created game
     * @param gameName
     */
    public void setGameName(String gameName){
        this.gameName = gameName;
    }

    /**
     * Sets nickname for the user
     */
    public void setNickName(){
        if(nickNameView.getText().toString().matches("")){
            JOIN_FLAG = false;
        }else{
            this.nickName = nickNameView.getText().toString();
            JOIN_FLAG = true;
        }

    }

    /**
     * Gets the game name
     * @return
     */
    public String getGameName(){
        return gameName;
    }

    /**
     * Returns nickname
     * @return nickname
     */
    public String getNickName(){
        return nickName;
    }

    public boolean getFlag(){
        return JOIN_FLAG;
    }

}
