package unpsjb.wikiprecios.http;

import org.json.JSONException;

/**
 * Created by jorge on 21/09/2015.
 * Permite recibir las consultas HTTP realizadas
 */
public interface HttpResponseHandler {
    void onSuccess(Object data) throws JSONException;

    void onFailure();

}
