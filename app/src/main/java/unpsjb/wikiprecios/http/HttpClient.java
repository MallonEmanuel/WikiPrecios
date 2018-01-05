package unpsjb.wikiprecios.http;

import android.content.Context;

import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.view.Coordinator;

/**
 * Created by emanuel on 05/10/17.
 */
public abstract class HttpClient {

    Coordinator coordinator;
    Context context;
    String base_url;

    public HttpClient(Coordinator coordinator, Context context) {
        this.coordinator = coordinator;
        this.context = context;
        this.base_url = AppPreference.getPrefBaseUrl(context);
    }

    public abstract void sendRequest();

}