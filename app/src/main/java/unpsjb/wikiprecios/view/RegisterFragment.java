package unpsjb.wikiprecios.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.config.Routes;
import unpsjb.wikiprecios.controller.SessionManager;
import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.http.HttpHandler;
import unpsjb.wikiprecios.http.HttpResponseHandler;


/**
 * Created by emanuel on 20/09/17.
 * Este fragmento permite al usuario registrarce en el sistema.
 */
public class RegisterFragment extends MyFragment implements HttpResponseHandler {
    private static final String TAG = RegisterFragment.class.getSimpleName();


    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputName;
    private EditText inputSurname;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;

    private SessionManager session;
    private Context context;
    private HttpHandler http;
    private Coordinator coordinator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_view, container, false);
        context = view.getContext();

        inputName = (EditText) view.findViewById(R.id.register_name);
        inputSurname = (EditText) view.findViewById(R.id.register_surname);
        inputEmail = (EditText) view.findViewById(R.id.register_email);
        inputPassword = (EditText) view.findViewById(R.id.register_password);
        btnRegister = (Button) view.findViewById(R.id.register_btn);
        btnLinkToLogin = (Button) view.findViewById(R.id.link_to_login_screen_btn);

        // Progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);

        // Session manager
        session = SessionManager.getInstance(context);

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputName.getText().toString().trim();
                String surname = inputSurname.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (!name.isEmpty() && !surname.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    registerUser(surname,name, email, password);
                } else {
                    Toast.makeText(context,context.getString(R.string.msg_register_enter_credential), Toast.LENGTH_LONG).show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                coordinator.viewLogin();
            }
        });
        return view;
    }


    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(String surname,String name,String email,String password) {

        String base_url = AppPreference.getPrefBaseUrl(context);

        http = new HttpHandler(base_url + Routes.URL_REGISTER, HttpHandler.GET_REQUEST);
        http.addParams("name", name);
        http.addParams("surname",surname);
        http.addParams("mail",email);
        http.addParams("password",password);
        http.setListener(this);
        http.sendRequest();
        pDialog.setMessage(context.getString(R.string.msg_registering));
        showDialog();

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    /**
     * En caso de que la consulta sea exitosa, se procesa la info recibida.
     * @param data informacion recibida
     */
    @Override
    public void onSuccess(Object data) {
        hideDialog();
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

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }
}
