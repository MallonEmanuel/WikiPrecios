package unpsjb.wikiprecios;

/**
 * Created by emanuel on 18/09/17.
 */

import android.os.Bundle;
import android.preference.PreferenceFragment;


//@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SettingsFragment extends PreferenceFragment {

    public SettingsFragment() {
        // Constructor Por Defecto
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }


}