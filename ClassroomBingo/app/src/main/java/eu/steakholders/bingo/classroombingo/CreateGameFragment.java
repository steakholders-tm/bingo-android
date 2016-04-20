package eu.steakholders.bingo.classroombingo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import eu.steakholders.bingo.api.Game;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGameFragment extends Fragment {

    //UI components
    private EditText gameNameInput;
    private Spinner gameTypeSpinner;
    private Spinner placeSpinner;
    private Spinner primarySpinner;
    private Spinner secondarySpinner;
    private EditText dateInput;
    private EditText startTime;
    private EditText durationInput;

    //Variables
    private Game gameObject;
    private int id = -1;
    private String name;
    private String date;
    private String time;
    private int duration;
    private int gameTypeId;
    private int placeId;
    private int primaryCategoryId;
    private int secondaryCategoryId;
    private List<Integer> tileIds = null;


    public CreateGameFragment() {
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
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_create_game, null);



        // Inflate the layout for this fragment
        return view;
    }

    public void setSpinners(){
        //TODO set all spinner values to the ones from the mainpage

    }

    public void createGame(){

    }

    public Game getGame(){
        return gameObject;
    }


}
