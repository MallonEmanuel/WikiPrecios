package unpsjb.wikiprecios.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.controller.SessionManager;
import unpsjb.wikiprecios.view.util.Message;

/**
 * Created by emanuel on 20/09/17.
 * Este fragmento permite al usuario logearce en el sistema.
 */
public class LoginFragment  extends MyFragment{
    private static final String TAG = LoginFragment.class.getSimpleName();

//  Componentes Visuales
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;

    private SessionManager session;
    private Context context;

    private Coordinator coordinator;

    private LoginButton btnLoginFacebook;
    CallbackManager callbackManager;

    public static MyFragment getInstance(){
        return getInstance(new LoginFragment());
    }

    //TODO registrar un usuario de facebook, al logearce. Para que dicho usuario pueda usar la app

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_view, container, false);
        context = view.getContext();
        coordinator = (Coordinator) getActivity();

        inputEmail = (EditText) view.findViewById(R.id.login_email);
        inputPassword = (EditText) view.findViewById(R.id.login_password);
        btnLogin = (Button) view.findViewById(R.id.login_btn);
        btnLinkToRegister = (Button) view.findViewById(R.id.link_to_register_screen_btn);

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
                        Message.show(context,"LoginFacebook.callback.onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Message.show(context,"LoginFacebook.callback.exception");
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
                    Message.show(context,context.getString(R.string.msg_logging_enter_credential));
                } else {
                    // login user
                    coordinator.checkLogin(mail, password);
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

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }
}
