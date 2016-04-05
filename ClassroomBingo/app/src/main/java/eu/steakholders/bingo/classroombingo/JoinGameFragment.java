package eu.steakholders.bingo.classroombingo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class JoinGameFragment extends Fragment {


    public JoinGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Get view
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_join_game, null);


        // Inflate the layout for this fragment
        return view;
    }

}
