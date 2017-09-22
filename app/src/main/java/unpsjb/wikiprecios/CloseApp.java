package unpsjb.wikiprecios;

import android.content.Context;

import com.facebook.login.LoginManager;

/**
 * Created by emanuel on 21/09/17.
 * Esta clase permite cerrar la aplicación, manteniendo un estado logico de esta.
 */
public class CloseApp implements DialogListener {

    private OnClickBtnListener coordinator;
    private Context context;

    public CloseApp(OnClickBtnListener coordinator) {
        this.coordinator = coordinator;
        this.context = coordinator.getContext();
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
