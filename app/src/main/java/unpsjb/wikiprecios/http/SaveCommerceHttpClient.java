package unpsjb.wikiprecios.http;

import android.content.Context;
import android.location.Location;

import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.config.Routes;
import unpsjb.wikiprecios.controller.SessionManager;
import unpsjb.wikiprecios.view.Coordinator;
import unpsjb.wikiprecios.view.util.Message;

/**
 * Created by emanuel on 01/11/17.
 */
public class SaveCommerceHttpClient extends HttpClient implements HttpResponseHandler {

    private String name ;
    private Location location;
    public SaveCommerceHttpClient(Coordinator coordinator, Context context) {
        super(coordinator, context);
    }

    @Override
    public void sendRequest() {
        String base_url = AppPreference.getPrefBaseUrl(context);
        HttpHandler http = new HttpHandler(base_url + Routes.URL_SAVE_COMMERCE, HttpHandler.GET_REQUEST);
        http.addParams("name", name);
        http.addParams("latitude", String.valueOf(location.getLatitude()));
        http.addParams("longitude", String.valueOf(location.getLongitude()));
        http.setListener(this);
        coordinator.showDialog("Guardando comercio...");
        http.sendRequest();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public void onSuccess(Object data) {
        Message.show(context,"comercio guardado");
        coordinator.hideDialog();
        //TODO En onSuccess devolver el id del commercio. que luego sera necesario(BACKEND)
    }
}
