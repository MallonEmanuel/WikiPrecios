package unpsjb.wikiprecios.controller;

import android.content.Context;
import android.location.Location;

import unpsjb.wikiprecios.config.Routes;
import unpsjb.wikiprecios.view.Coordinator;

/**
 * Created by emanuel on 05/10/17.
 */
public class CommerceFinder extends Finder implements HttpResponseHandler {

    public CommerceFinder(Coordinator coordinator, Context context) {
        super(coordinator,context);
    }

    /**
     * Se ocupa de realizar una consulta, con la ubicacion actual para recibir los comercios mas
     * cercanos al usuario
     */
    public void sendRequest() {
        HttpHandler http = new HttpHandler(base_url + Routes.URL_NEARBY_COMMERCES,HttpHandler.GET_REQUEST);
        Location location = LocationService.getInstance(context).getLocation();

        http.addParams("latitud", String.valueOf(location.getLatitude()));
        http.addParams("longitud", String.valueOf(location.getLongitude()));
        http.addParams("usuario", SessionManager.getInstance(context).getUserLoged());
        http.setListener(this);
        http.sendRequest();
    }

    @Override
    public void onSuccess(Object commerces) {
        coordinator.viewCommerces(commerces);
    }
}
