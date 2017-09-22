package unpsjb.wikiprecios;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

/**
 * Created by emanuel on 20/09/17.
 */
public class LoginFragment  extends Fragment implements HttpResponseHandler {
    private static final String TAG = LoginFragment.class.getSimpleName();

//  Componentes Visuales
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;

    private SessionManager session;
    private HttpHandler http;
    private Context context;

    private OnClickBtnListener coordinator;

    private LoginButton btnLoginFacebook;
    CallbackManager callbackManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_view, container, false);
        context = view.getContext();

        inputEmail = (EditText) view.findViewById(R.id.login_email);
        inputPassword = (EditText) view.findViewById(R.id.login_password);
        btnLogin = (Button) view.findViewById(R.id.login_btn);
        btnLinkToRegister = (Button) view.findViewById(R.id.link_to_register_screen_btn);

        // Progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);

        // Session manager
        session = SessionManager.getInstance(context);

        // Facebook
        btnLoginFacebook = (LoginButton) view.findViewById(R.id.login_button_facebook);
        btnLoginFacebook.setReadPermissions("email");

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, 
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        coordinator.viewMenu();
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.e(TAG, response.toString());
                                        try {
                                            String email = response.getJSONObject().getString("email");
                                            String password = response.getJSONObject().getString("id");
                                            session.setLogin(true, email,password);
                                        }catch(Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,email,first_name,last_name");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        message("LoginFacebook.callback.onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        message("LoginFacebook.callback.exception");
                    }
                });// Fin Facebook callback


        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String mail = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (mail.isEmpty() || password.isEmpty()) {
                    // Prompt user to enter credentials
                    message(context.getString(R.string.msg_logging_enter_credential));
                } else {
                    // login user
                    checkLogin(mail, password);
                }
            }
        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                coordinator.viewRegister();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
        inputPassword.setText("");
    }

    /**
     * function to verify login details in mysql db
     */
    private void checkLogin(final String mail, final String password) {

        String base_url = AppPreference.getPrefBaseUrl(context);
        http = new HttpHandler(base_url + RoutesURL.URL_LOGIN, HttpHandler.GET_REQUEST);
        http.addParams("mail",mail);
        http.addParams("password",password);
        http.setListener(this);
        http.sendRequest();

        pDialog.setMessage(context.getString(R.string.msg_logging));
        showDialog();
    }


    @Override
    public void onSuccess(Object data) {
        hideDialog();
        try {
            JSONObject json = (JSONObject) data;
            if (json.has("noUser")) {
                message(context.getString(R.string.msg_logging_error));
            } else {
                session.setLogin(true, json.get("mail").toString(),json.get("password").toString());
                coordinator.viewMenu();
            }
        } catch (Exception e) {
            e.printStackTrace();
            message(context.getString(R.string.msg_logging_json_error));
        }
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void message(String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public void setCoordinator(OnClickBtnListener coordinator){
        this.coordinator = coordinator;
    }

}
