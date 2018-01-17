package unpsjb.wikiprecios.http;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.config.Routes;
import unpsjb.wikiprecios.controller.SessionManager;
import unpsjb.wikiprecios.controller.parser.CommerceParser;
import unpsjb.wikiprecios.controller.parser.JsonParser;
import unpsjb.wikiprecios.model.Commerce;
import unpsjb.wikiprecios.view.Coordinator;
import unpsjb.wikiprecios.view.util.Message;

/**
 * Esta clase permite guardar un nuevo comercio.
 */
public class SaveCommerceHttpClient extends HttpClient implements HttpResponseHandler {


    private String name ;
    private Location location;
    private boolean inProcess;

    public SaveCommerceHttpClient(Coordinator coordinator, Context context) {
        super(coordinator, context);
    }

    @Override
    public void sendRequest() {
        String base_url = AppPreference.getPrefBaseUrl(context);
        HttpHandler http = new HttpHandler(base_url + Routes.URL_SAVE_COMMERCE, HttpHandler.POST_REQUEST);
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

        coordinator.hideDialog();
        Commerce commerce = null;
        try {
            JSONObject json = (JSONObject) data;
            JSONObject json2 = (JSONObject) json.get("commerce");

            CommerceParser parser = new CommerceParser();
            commerce = (Commerce) parser.parse(json2);
            if(inProcess){
                coordinator.findSuggestedPrices(commerce);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("SAVE:COMMERCE","ERROR");
        }


        //TODO En onSuccess devolver el id del commercio. que luego sera necesario(BACKEND)
    }

    public boolean isInProcess() {
        return inProcess;
    }

    public void setInProcess(boolean inProcess) {
        this.inProcess = inProcess;
    }
}
