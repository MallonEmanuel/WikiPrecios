package unpsjb.wikiprecios.http;

/**
 * Created by jorge on 21/09/2015.
 * Permite recibir las consultas HTTP realizadas
 */
public interface HttpResponseHandler {
    void onSuccess(Object data);
    // TODO agregar sendRequest(), onFailure()
}
