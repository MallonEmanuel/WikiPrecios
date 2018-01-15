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
 * Created by emanuel on 18/10/17.
 */
public class LoginHttpClient extends HttpClient implements HttpResponseHandler {

    private String email;
    private String password;
    private boolean background;

    public LoginHttpClient(Coordinator coordinator, Context context) {
        super(coordinator, context);
    }

    @Override
    public void sendRequest() {
        String base_url = AppPreference.getPrefBaseUrl(context);
        HttpHandler http = new HttpHandler(base_url + Routes.URL_LOGIN, HttpHandler.POST_REQUEST);
        http.addParams("mail",email);
        http.addParams("password",password);
        http.setListener(this);
        http.sendRequest();
        if(background == false){
            coordinator.showDialog(context.getString(R.string.msg_logging));
        }

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
                User user = (User) new UserParser().parse(json);
                coordinator.setUser(user);
                if(background == false){
                    coordinator.viewMenu();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message.show(context,context.getString(R.string.msg_logging_json_error));
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isBackground() {
        return background;
    }

    public void setBackground(boolean background) {
        this.background = background;
    }
}
