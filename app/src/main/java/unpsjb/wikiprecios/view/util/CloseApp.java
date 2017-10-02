package unpsjb.wikiprecios.view.util;

import com.facebook.login.LoginManager;

import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.controller.SessionManager;
import unpsjb.wikiprecios.view.Coordinator;

/**
 * Created by emanuel on 21/09/17.
 * Esta clase permite cerrar la aplicación, manteniendo un estado logico de esta.
 */
public class CloseApp implements DialogListener {

    private Coordinator coordinator;

    public CloseApp(Coordinator coordinator) {
        this.coordinator = coordinator;

    }

    @Override
    public void posiviteResult() {
        // Si no es preferente tener la sesion iniciada, cierra la sesion local y la de facebook
        if(!AppPreference.isPrefSessionLoged(coordinator.getContext())){
            SessionManager.getInstance(coordinator.getContext()).setIsLoggedIn(false);
            LoginManager.getInstance().logOut();
        }
        // Finaliza la app
        coordinator.finish();
    }

    @Override
    public void negativeResult() {

    }
}
