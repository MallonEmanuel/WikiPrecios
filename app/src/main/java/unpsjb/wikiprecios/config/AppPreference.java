package unpsjb.wikiprecios.config;

import android.content.Context;
import android.preference.PreferenceManager;

import unpsjb.wikiprecios.R;

/**
 * Esta clase se ocupa de leer las preferencias de las configuraciones del usuario.
 * isPrefSessionLoged : si es preferible mantener la sesion de usuario.
 * pref_base_url_key : la base url para la conexion con el servidor,
 * generalmente http://localhost durante desarrollo.
 */
public class AppPreference {

    /** Indica si se desea mantener la sesión**/
    public static Boolean isPrefSessionLoged(Context context){
        String key = context.getString(R.string.pref_sesion_loged_key);
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key,false);
    }

    /** Obtiene la base URL del servidor, solo usado durante desarrollo **/
    public static String getPrefBaseUrl(Context context){
        String key = context.getString(R.string.pref_base_url_key);
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key,null);
    }
}
