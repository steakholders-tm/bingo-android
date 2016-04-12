package eu.steakholders.bingo.classroombingo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class JoinGameFragment extends Fragment {

    //Fields
    private TextView gameNameView;
    private EditText nickNameView;

    //Variables
    private String gameName;
    private String nickName;

    public JoinGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Get view
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_join_game, null);

        gameNameView = (TextView) view.findViewById(R.id.selected_game_name);
        nickNameView = (EditText) view.findViewById(R.id.nickname_input);

        gameNameView.setText(gameName);

        // Inflate the layout for this fragment
        return view;
    }

    public void setGameName(String gameName){
        this.gameName = gameName;
    }

    public void setNickName(String nickName){
        this.nickName = nickName;
    }

    public String getGameName(){
        return gameName;
    }

    public String getNickName(){
        return nickName;
    }

}
