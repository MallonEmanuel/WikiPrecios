package unpsjb.wikiprecios.view;

import android.support.v4.app.Fragment;

/**
 * My fragment se creo para que todos los fragmentos de la app lo extiendan
 * y si se desea cambiar por ej el Fragment del que
 * extiende (android.support.v4.app.Fragment o android.app.Fragment) se realize el cambio en un solo lugar.
 * Se modifico a android.support.v4.app.Fragment para poder implementar TabFragment
 * Es un momento, usar android.app.fragment no permitia usar un tab dentro de un fragmento
 * Al implementar android.support.v4.app.Fragment tambien se debio Cambiar la extencion de SettiingFragment
 */
public class MyFragment extends Fragment {
    // TODO mejorar con getInstace()

    private static MyFragment instance;

    public static MyFragment getInstance(MyFragment object){
        if( instance == null){
            instance = object;
        }
        return instance;
    }


}
