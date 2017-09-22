package unpsjb.wikiprecios;

import android.content.Context;

import com.facebook.login.LoginManager;

/**
 * Created by emanuel on 21/09/17.
 * Esta clase permite cerrar la cesión
 */
public class CloseSession implements DialogListener {

    private OnClickBtnListener coordinator;
    private Context context;

    public CloseSession(OnClickBtnListener coordinator) {
        this.coordinator = coordinator;
        this.context = coordinator.getContext();
    }

    @Override
    public void posiviteResult() {
        // Cierra la sesion local, la de facebook y vuelve a la vista de login
        SessionManager.getInstance(context).setIsLoggedIn(false);
        LoginManager.getInstance().logOut();
        coordinator.viewLogin();
    }

    @Override
    public void negativeResult() {

    }
}
