package unpsjb.wikiprecios.view.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by emanuel on 20/09/17.
 * Permite realizar una consulta al usuario, con dos posibles resultados.
 */
public class DialogManager {

    public DialogManager() {
    }

    //code
    public void viewAlertDialog(Activity activity, String question, final DialogListener dialogListener) {

        AlertDialog.Builder showPlace = new AlertDialog.Builder(
                activity);
        showPlace.setMessage(question);
        showPlace.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialogListener.posiviteResult();
            }
        });
        showPlace.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialogListener.negativeResult();
            }
        });
        showPlace.show();
    }

}