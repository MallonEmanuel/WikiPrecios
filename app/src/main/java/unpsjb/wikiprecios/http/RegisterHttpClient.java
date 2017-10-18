package unpsjb.wikiprecios.http;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.config.Routes;
import unpsjb.wikiprecios.view.Coordinator;

/**
 * Created by emanuel on 18/10/17.
 */
public class RegisterHttpClient extends HttpClient implements HttpResponseHandler  {


    private String name;
    private String surname;
    private String email;
    private String password;

    public RegisterHttpClient(Coordinator coordinator, Context context) {
        super(coordinator, context);
    }

    @Override
    public void sendRequest() {

        String base_url = AppPreference.getPrefBaseUrl(context);

        HttpHandler http = new HttpHandler(base_url + Routes.URL_REGISTER, HttpHandler.GET_REQUEST);
        http.addParams("name", name);
        http.addParams("surname",surname);
        http.addParams("mail",email);
        http.addParams("password",password);
        http.setListener(this);
        http.sendRequest();
        coordinator.showDialog(context.getString(R.string.msg_registering));
    }

    @Override
    public void onSuccess(Object data) {
        coordinator.hideDialog();
        try {
            JSONObject json = (JSONObject) data;
            boolean isRegister = json.getBoolean("registrado");
            if (!isRegister) {
                Toast.makeText(context, json.getString("mensaje"), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context,json.getString("mensaje")+". "+ context.getString(R.string.msg_register_try_login),Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, context.getString(R.string.msg_register_json_error) + e.getMessage(), Toast.LENGTH_LONG).show();
        }
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

    public void setPassword(String password) {
        this.password = password;
    }
}
