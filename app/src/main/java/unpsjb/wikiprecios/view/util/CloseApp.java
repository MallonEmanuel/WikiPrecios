package unpsjb.wikiprecios.view.util;

import android.content.Context;

import com.facebook.login.LoginManager;

import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.controller.SessionManager;
import unpsjb.wikiprecios.view.Coordinator;

/**
 * Created by emanuel on 21/09/17.
 * Esta clase permite cerrar la aplicación, manteniendo un estado logico de esta.
 */
public class CloseApp extends Question implements DialogListener {

    public CloseApp(Coordinator coordinator, Context context) {
        super(coordinator, context);
    }

    @Override
    public void posiviteResult() {
        // Si no es preferente tener la sesion iniciada, cierra la sesion local y la de facebook
        if(!AppPreference.isPrefSessionLoged(context)){
            SessionManager.getInstance(context).setIsLoggedIn(false);
            LoginManager.getInstance().logOut();
        }
        // Finaliza la app
        coordinator.finish();
    }

    @Override
    public void negativeResult() {

    }
}
