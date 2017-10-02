package unpsjb.wikiprecios.view.util;

import com.facebook.login.LoginManager;

import unpsjb.wikiprecios.controller.SessionManager;
import unpsjb.wikiprecios.view.Coordinator;

/**
 * Created by emanuel on 21/09/17.
 * Esta clase permite cerrar la cesión
 */
public class CloseSession implements DialogListener {

    private Coordinator coordinator;

    public CloseSession(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public void posiviteResult() {
        // Cierra la sesion local, la de facebook y vuelve a la vista de login
        SessionManager.getInstance(coordinator.getContext()).setIsLoggedIn(false);
        LoginManager.getInstance().logOut();
        coordinator.viewLogin();
    }

    @Override
    public void negativeResult() {
    }
}
