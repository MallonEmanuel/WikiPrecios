package unpsjb.wikiprecios.view;

/**
 * Created by emanuel on 18/09/17.
 * Este fragmento permite mostrar las configuraciones del usuario:
 * Si se desea mantener la sesion y la base_url del servidor.
 */

import android.os.Bundle;


import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.view.util.PreferenceFragment;

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