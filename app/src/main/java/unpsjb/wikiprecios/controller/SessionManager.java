package unpsjb.wikiprecios.controller;

/**
 * Created by Ema on 27/10/2015.
 * Permite mantener la sesion de un usuario, mantiene sus datos basicos para logearce
 * Al iniciar la sesion con un usuario, este se guarda en el Sesion Manager, y
 * determina isLoggedIn en true, para luego consultar si existe algun usuario logeado
 * y la prefencia de mantener sesion esta es verdadera, se inicia la sesion automaticamente.
 * Al cerrar la sesion o la app tambien se debe verificar estos valores.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();
    private static SessionManager instance;

    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context contex;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "AndroidHiveLogin";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_USER_LOGGED = "userLoged";
    private static final String KEY_USER_PASSWORD = "userPassword";


    public static SessionManager getInstance(Context context){
        if( instance == null){
            instance = new SessionManager(context);
        }
        return instance;
    }

    public SessionManager(Context context) {
        this.contex = context;
        pref = contex.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn,String userLoged,String userPassword) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.putString(KEY_USER_LOGGED, userLoged);
        editor.putString(KEY_USER_PASSWORD, userPassword);
        // commit changes
        editor.commit();
    }
    public String getUserLoged(){
        return pref.getString(KEY_USER_LOGGED,null);
    }

    public String getUserPassword(){
        return pref.getString(KEY_USER_PASSWORD,null);
    }
    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void setIsLoggedIn(boolean isLoggedIn){ editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn).commit();}
}