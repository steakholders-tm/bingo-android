package eu.steakholders.bingo.classroombingo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;


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
    private EditText endTime;

    //Variables



    public CreateGameFragment() {
        // Required empty public constructor
    }


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

}
