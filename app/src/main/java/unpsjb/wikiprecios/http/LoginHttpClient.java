package unpsjb.wikiprecios.http;

import android.content.Context;

import org.json.JSONObject;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.config.Routes;
import unpsjb.wikiprecios.controller.SessionManager;
import unpsjb.wikiprecios.view.Coordinator;
import unpsjb.wikiprecios.view.util.Message;

/**
 * Created by emanuel on 18/10/17.
 */
public class LoginHttpClient extends HttpClient implements HttpResponseHandler {

    private String email;
    private String password;

    public LoginHttpClient(Coordinator coordinator, Context context) {
        super(coordinator, context);
    }

    @Override
    public void sendRequest() {
        String base_url = AppPreference.getPrefBaseUrl(context);
        HttpHandler http = new HttpHandler(base_url + Routes.URL_LOGIN, HttpHandler.GET_REQUEST);
        http.addParams("mail",email);
        http.addParams("password",password);
        http.setListener(this);
        http.sendRequest();
        coordinator.showDialog(context.getString(R.string.msg_logging));
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void onSuccess(Object data) {
        coordinator.hideDialog();
        try {
            JSONObject json = (JSONObject) data;
            if (json.has("noUser")) {
                Message.show(context,context.getString(R.string.msg_logging_error));
            } else {
                SessionManager.getInstance(context).setLogin(true, json.get("mail").toString(),json.get("password").toString());
                coordinator.viewMenu();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message.show(context,context.getString(R.string.msg_logging_json_error));
        }
    }
}
