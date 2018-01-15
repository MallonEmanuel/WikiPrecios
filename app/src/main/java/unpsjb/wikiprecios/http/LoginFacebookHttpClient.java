package unpsjb.wikiprecios.http;

import android.content.Context;

import org.json.JSONObject;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.config.Routes;
import unpsjb.wikiprecios.controller.SessionManager;
import unpsjb.wikiprecios.controller.parser.UserParser;
import unpsjb.wikiprecios.model.User;
import unpsjb.wikiprecios.view.Coordinator;
import unpsjb.wikiprecios.view.util.Message;

/**
 * Created by emanuel on 09/01/18.
 */
public class LoginFacebookHttpClient extends HttpClient implements HttpResponseHandler {

    private String email;
    private String facebookId;
    private String name;
    private String surname;

    public LoginFacebookHttpClient(Coordinator coordinator, Context context) {
        super(coordinator, context);
    }

    @Override
    public void sendRequest() {
        String base_url = AppPreference.getPrefBaseUrl(context);
        HttpHandler http = new HttpHandler(base_url + Routes.URL_FACEBOOK_LOGIN, HttpHandler.POST_REQUEST);
        http.addParams("mail", email);
        http.addParams("name", name);
        http.addParams("surname", surname);
        http.addParams("facebook_id", facebookId);
        http.setListener(this);
        http.sendRequest();
        //coordinator.showDialog(context.getString(R.string.msg_logging));
    }


    @Override
    public void onSuccess(Object data) {
        //coordinator.hideDialog();
        try {
            JSONObject json = (JSONObject) data;
            User user = (User) new UserParser().parse(json);
            coordinator.setUser(user);
            SessionManager.getInstance(context).setLogin(true, json.get("mail").toString(), json.get("facebook_id").toString());
        } catch (Exception e) {
            e.printStackTrace();
            Message.show(context, context.getString(R.string.msg_logging_json_error));
        }
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}