package unpsjb.wikiprecios.http;

import android.content.Context;

import unpsjb.wikiprecios.config.Routes;
import unpsjb.wikiprecios.view.Coordinator;
import unpsjb.wikiprecios.view.util.Message;

/**
 * Created by emanuel on 17/10/17.
 */
public class SaveFavouriteCommerceHttpClient extends HttpClient implements HttpResponseHandler {

    private String commerces;
    private String email;

    public SaveFavouriteCommerceHttpClient(Coordinator coordinator, Context context) {
        super(coordinator, context);
    }

    @Override
    public void sendRequest() {
        HttpHandler http = new HttpHandler(base_url + Routes.ULR_SAVE_FAVOURITES_COMMERCES, HttpHandler.GET_REQUEST);
        http.addParams("commerce", commerces);
        http.addParams("user", email);
        http.setListener(this);
        http.sendRequest();
    }

    @Override
    public void onSuccess(Object data) {
        Message.show(context,"Comercios guardados correctamente.");
    }

    public void setCommerces(String commerces) {
        this.commerces = commerces;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
