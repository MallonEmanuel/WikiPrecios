package unpsjb.wikiprecios.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.view.util.Message;


/**
 * Este fragmento permite al usuario registrarce en el sistema.
 */
public class RegisterFragment extends MyFragment {
    private static final String TAG = RegisterFragment.class.getSimpleName();


    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputName;
    private EditText inputSurname;
    private EditText inputEmail;
    private EditText inputPassword;

    private Context context;
    private Coordinator coordinator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_view, container, false);
        context = view.getContext();
        coordinator = (Coordinator) getActivity();

        inputName = (EditText) view.findViewById(R.id.register_name);
        inputSurname = (EditText) view.findViewById(R.id.register_surname);
        inputEmail = (EditText) view.findViewById(R.id.register_email);
        inputPassword = (EditText) view.findViewById(R.id.register_password);
        btnRegister = (Button) view.findViewById(R.id.register_btn);
        btnLinkToLogin = (Button) view.findViewById(R.id.link_to_login_screen_btn);

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputName.getText().toString().trim();
                String surname = inputSurname.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (!name.isEmpty() && !surname.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    coordinator.registerUser(surname,name, email, password);
                } else {
                    Message.show(context,context.getString(R.string.msg_register_enter_credential));
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

}
