package unpsjb.wikiprecios.view.util;

import android.content.Context;

import com.facebook.login.LoginManager;

import unpsjb.wikiprecios.controller.SessionManager;
import unpsjb.wikiprecios.view.Coordinator;

/**
 * Esta clase permite cerrar la cesión
 */
public class CloseSession extends Question implements DialogListener {

    public CloseSession(Coordinator coordinator, Context context) {
        super(coordinator, context);
    }

    @Override
    public void posiviteResult() {
        // Cierra la sesion local, la de facebook y vuelve a la vista de login
        SessionManager.getInstance(context).setIsLoggedIn(false);
        LoginManager.getInstance().logOut();
        coordinator.setUser(null);
        coordinator.viewLogin();
    }

    @Override
    public void negativeResult() {
    }
}
