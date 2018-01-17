package unpsjb.wikiprecios.http;

import org.json.JSONException;

/**
 * Created by jorge on 21/09/2015.
 * Permite recibir las consultas HTTP realizadas, permite a quien lo implemente controlar las
 * acciones a tomar en caso de exito o error en las consultas.
 */
public interface HttpResponseHandler {
    void onSuccess(Object data) throws JSONException;

    void onFailure();

}
