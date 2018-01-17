package unpsjb.wikiprecios.view.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Esta clase permite mostrar un Toast "Mensaje", al usuario
 */
public class Message {

    public static void show(Context context, String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
