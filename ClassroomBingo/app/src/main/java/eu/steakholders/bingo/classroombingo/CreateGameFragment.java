package eu.steakholders.bingo.classroombingo;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import java.util.List;
import java.util.Map;

import eu.steakholders.bingo.api.Game;
import eu.steakholders.bingo.util.UtilityClass;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGameFragment extends Fragment {

    private static String TAG = "DEBUG";
    private static boolean CREATE_FLAG = false;

    //UI components
    private EditText gameNameInput;
    private Spinner gameTypeSpinner;
    private Spinner placeSpinner;
    private Spinner primarySpinner;
    private Spinner secondarySpinner;
    private EditText dateInput;
    private EditText startTimeInput;
    private EditText durationInput;
    private ScrollView parent;

    //Variables fo game creation
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

    //Spinner Lists
    private List<String> placesNames;
    private List<String> gameNames;
    private List<String> primaryNames;
    private List<String> secondaryNames;

    //Mapping from name to id
    private Map<String, Integer> gameTypeMap;
    private Map<String, Integer> placesMap;
    private Map<String, Integer> primaryMap;
    private Map<String, Integer> secondaryMap;

    //Adapters for spinners
    private ArrayAdapter<String> gameTypeAdapter;
    private ArrayAdapter<String> placeAdapter;
    private ArrayAdapter<String> primaryAdapter;
    private ArrayAdapter<String> secondaryAdapter;

    //Context
    private Context context;

    //Default values for spinners
    private int selectedGameTypePos;
    private int selectedPlacePos;
    private int selectedPrimaryPos;
    private int selectedSecondaryPos;


    public CreateGameFragment() {
        // Required empty public constructor
    }

    /**
     * Creates and inflates fragment, initializes widgets and gets info from activity
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

        //Get context
        context = container.getContext();

        parent = (ScrollView) view.findViewById(R.id.parent);

        parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                UtilityClass.hideSoftKeyboard(getActivity());
                return false;
            }
        });

        gameNameInput = (EditText) view.findViewById(R.id.game_name_input);
        gameTypeSpinner = (Spinner) view.findViewById(R.id.create_spinner_gt);
        placeSpinner = (Spinner) view.findViewById(R.id.create_spinner_place);
        primarySpinner = (Spinner) view.findViewById(R.id.create_spinner_pc);
        secondarySpinner = (Spinner) view.findViewById(R.id.create_spinner_sc);
        dateInput = (EditText) view.findViewById(R.id.create_game_date_field);
        startTimeInput = (EditText) view.findViewById(R.id.create_start_time_field);
        durationInput = (EditText) view.findViewById(R.id.create_duration);

        //Setting values to spinners
        setGameTypeSpinnerValues(context);
        setPlaceSpinnerValues(context);
        setPrimarySpinnerValues(context);
        setSecondarySpinnerValues(context);




        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Sets names of places
     * @param placesNames
     */
    public void setPlacesNames(List<String> placesNames) {
        this.placesNames = placesNames;
    }

    /**
     * Sets game names
     * @param gameNames
     */
    public void setGameNames(List<String> gameNames) {
        this.gameNames = gameNames;
    }

    /**
     * Sets primary category names
     * @param primaryNames
     */
    public void setPrimaryNames(List<String> primaryNames) {
        this.primaryNames = primaryNames;
    }

    /**
     * Sets secondary category names
     * @param secondaryNames
     */
    public void setSecondaryNames(List<String> secondaryNames) {
        this.secondaryNames = secondaryNames;
    }

    /**
     * Map for game types <name, id>
     * @param gameTypeMap
     */
    public void setGameTypeMap(Map<String, Integer> gameTypeMap) {
        this.gameTypeMap = gameTypeMap;
    }

    /**
     * Map for places <name, id>
     * @param placesMap
     */
    public void setPlacesMap(Map<String, Integer> placesMap) {
        this.placesMap = placesMap;
    }

    /**
     * Map for primary category <name, id>
     * @param primaryMap
     */
    public void setPrimaryMap(Map<String, Integer> primaryMap) {
        this.primaryMap = primaryMap;
    }

    /**
     * Map for secondary category <name, id>
     * @param secondaryMap
     */
    public void setSecondaryMap(Map<String, Integer> secondaryMap) {
        this.secondaryMap = secondaryMap;
    }

    /**
     * return list of gametypes
     * @return
     */
    public List<String> getGameTypes(){
        return gameNames;
    }

    /**
     * return list of places
     * @return
     */
    public List<String> getPlaces(){
        return placesNames;
    }

    /**
     * return list of primary categories
     * @return
     */
    public List<String> getPrimaryCats(){
        return primaryNames;
    }

    /**
     * return list of secondary categories
     * @return
     */
    public List<String> getSecondaryCats(){
        return secondaryNames;
    }

    /**
     * return map of game types
     * @return
     */
    public Map<String, Integer> getGameTypeMap(){
        return gameTypeMap;
    }

    /**
     * return map of places
     * @return
     */
    public Map<String, Integer> getPlacesMap(){
        return placesMap;
    }

    /**
     * return map of primary categories
     * @return
     */
    public Map<String, Integer> getPrimaryMap(){
        return primaryMap;
    }

    /**
     * return map of secondary categories
     * @return
     */
    public Map<String, Integer> getSecondaryMap(){
        return secondaryMap;
    }

    /**
     * Sets the spinner values for game types
     * @param context
     */
    public void setGameTypeSpinnerValues(Context context) {
        gameTypeAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, gameNames);
        gameTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameTypeSpinner.setAdapter(gameTypeAdapter);
        gameTypeSpinner.setSelection(getSelectedGameTypePos());
    }

    /**
     * Sets the spinner values for places
     * @param context
     */
    public void setPlaceSpinnerValues(Context context) {
        placeAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, placesNames);
        placeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeSpinner.setAdapter(placeAdapter);
        placeSpinner.setSelection(getSelectedPlacePos());
    }

    /**
     * Sets the spinner values for primary categories
     * @param context
     */
    public void setPrimarySpinnerValues(Context context) {
        primaryAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, primaryNames);
        primaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        primarySpinner.setAdapter(primaryAdapter);
        primarySpinner.setSelection(getSelectedPrimaryPos());
    }

    /**
     * Sets the spinner values for secondary categories
     * @param context
     */
    public void setSecondarySpinnerValues(Context context){
        secondaryAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, secondaryNames);
        secondaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        secondarySpinner.setAdapter(secondaryAdapter);
        secondarySpinner.setSelection(getSelectedSecondaryPos());
    }

    /**
     *
     * @return selected position from mainActivity
     */
    public int getSelectedSecondaryPos() {
        return selectedSecondaryPos;
    }

    /**
     * Sets selected pos for spinner
     * @param selectedSecondaryPos
     */
    public void setSelectedSecondaryPos(int selectedSecondaryPos) {
        this.selectedSecondaryPos = selectedSecondaryPos;
    }

    /**
     *
     * @return selected position from mainActivity
     */
    public int getSelectedPrimaryPos() {
        return selectedPrimaryPos;
    }

    /**
     * Sets selected pos for spinner
     * @param selectedPrimaryPos
     */
    public void setSelectedPrimaryPos(int selectedPrimaryPos) {
        this.selectedPrimaryPos = selectedPrimaryPos;
    }

    /**
     *
     * @return selected position from mainActivity
     */
    public int getSelectedPlacePos() {
        return selectedPlacePos;
    }

    /**
     * Sets selected pos for spinner
     * @param selectedPlacePos
     */
    public void setSelectedPlacePos(int selectedPlacePos) {
        this.selectedPlacePos = selectedPlacePos;
    }

    /**
     *
     * @return selected position from mainActivity
     */
    public int getSelectedGameTypePos() {
        return selectedGameTypePos;
    }

    /**
     * Sets selected pos for spinner
     * @param selectedGameTypePos
     */
    public void setSelectedGameTypePos(int selectedGameTypePos) {
        this.selectedGameTypePos = selectedGameTypePos;
    }

    /**
     * Makes the game object based on user input
     */
    public void makeGame(){
        if(!gameNameInput.getText().toString().matches("") && !dateInput.getText().toString().matches("") && !startTimeInput.getText().toString().matches("") && !durationInput.getText().toString().matches("")){
            name = gameNameInput.getText().toString();
            date = dateInput.getText().toString().replace('/','-');
            time = startTimeInput.getText().toString();
            duration = Integer.parseInt(durationInput.getText().toString());


            String selectedGameType = gameTypeSpinner.getSelectedItem().toString();
            String selectedPlace = placeSpinner.getSelectedItem().toString();
            String selectedPrimary = primarySpinner.getSelectedItem().toString();
            String selectedSecondary = secondarySpinner.getSelectedItem().toString();

            gameTypeId = gameTypeMap.get(selectedGameType);
            placeId = placesMap.get(selectedPlace);
            primaryCategoryId = primaryMap.get(selectedPrimary);

            secondaryCategoryId = secondaryMap.get(selectedSecondary);


            gameObject = new Game(id, name, date, time, duration, gameTypeId, placeId, primaryCategoryId, secondaryCategoryId, null);

            CREATE_FLAG = true;

        }else{
            Snackbar snackbar = Snackbar
                    .make(parent, "Did you remember to fill out all the fields?", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    /**
     * sets game object
     * @param gameObject
     */
    public void setGame(Game gameObject){
        this.gameObject = gameObject;
    }

    /**
     * method to get game
     * @return Game
     */
    public Game getGame(){
        return gameObject;
    }

    /**
     * Flag to see if all the fields were filled in
     * @return boolean
     */
    public boolean getFlag(){
        return CREATE_FLAG;
    }

    public void setFlag(){
        CREATE_FLAG = false;
    }

}
