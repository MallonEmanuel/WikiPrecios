package unpsjb.wikiprecios.view.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by emanuel on 04/10/17.
 */
public class Message {

    public static void show(Context context, String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
