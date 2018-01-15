package unpsjb.wikiprecios.http;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.config.Routes;
import unpsjb.wikiprecios.controller.SessionManager;
import unpsjb.wikiprecios.controller.parser.UserParser;
import unpsjb.wikiprecios.model.Query;
import unpsjb.wikiprecios.model.User;
import unpsjb.wikiprecios.view.Coordinator;
import unpsjb.wikiprecios.view.util.Message;

/**
 * Created by emanuel on 19/10/17.
 */
public class SavePriceHttpClient extends HttpClient implements HttpResponseHandler  {

    private Query query;

    public SavePriceHttpClient(Coordinator coordinator, Context context) {
        super(coordinator, context);
    }

    @Override
    public void sendRequest() {
        String base_url = AppPreference.getPrefBaseUrl(context);
        HttpHandler http = new HttpHandler(base_url + Routes.URL_SAVE_PRICE, HttpHandler.POST_REQUEST);
        http.addParams("commerce", String.valueOf(query.getCommerce().getId()));
        http.addParams("price", String.valueOf(query.getPrice()));
        http.addParams("product", String.valueOf(query.getBarcode()));
        http.addParams("user", SessionManager.getInstance(context).getUserLoged());
        http.setListener(this);
        http.sendRequest();
    }

    @Override
    public void onSuccess(Object data) {
        Log.e("SAVEPRICE",data.toString());

            try {
                JSONObject json = (JSONObject) data;

                if(json.getJSONArray("result").equals("[]")){
                    Message.show(context,context.getString(R.string.msg_new_product));
                    coordinator.viewMenu();
                }else{
                    coordinator.viewPrices(json.getJSONArray("result"));
                }
                User user = (User) new UserParser().parse(json.getJSONObject("user"));
                coordinator.setUser(user);

            } catch (JSONException e) {
                e.printStackTrace();
            }


    }

    public void setQuery(Query query) {
        this.query = query;
    }
}
