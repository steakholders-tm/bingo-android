package eu.steakholders.bingo.classroombingo;

import android.app.Activity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by jieli on 20.04.16.
 */
public class UtilityClass {

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        try{
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
        catch(NullPointerException n){
            Log.e("DEBUG", n.toString());
        }

    }

}
