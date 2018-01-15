package unpsjb.wikiprecios.http;

import android.content.Context;

import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.view.Coordinator;
import unpsjb.wikiprecios.view.util.Message;

/**
 * Created by emanuel on 05/10/17.
 */
public abstract class HttpClient implements HttpResponseHandler{

    Coordinator coordinator;
    Context context;
    String base_url;

    public HttpClient(Coordinator coordinator, Context context) {
        this.coordinator = coordinator;
        this.context = context;
        this.base_url = AppPreference.getPrefBaseUrl(context);
    }

    public abstract void sendRequest();


    @Override
    public void onFailure() {
        coordinator.hideDialog();
        Message.show(context,"Lo sentimos, en este momento wikiprecios no esta disponible, por favor, intente mas tarde");
    }
}
