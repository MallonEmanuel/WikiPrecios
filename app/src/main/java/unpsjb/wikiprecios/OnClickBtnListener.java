package unpsjb.wikiprecios;

import android.content.Context;

/**
 * Created by emanuel on 20/09/17.
 */
public interface OnClickBtnListener {

    public Context getContext();

    public void viewLogin();
    public void viewRegister();
    public void viewMenu();
    public void closeView(); // Consulta si desea cerrar la App

    public void back(); // Vuelve hacia atras en la pila de fragments
    public void logout(); // Consulta si desea cerrar sesión
    public void finish(); // Cierra la App
}
