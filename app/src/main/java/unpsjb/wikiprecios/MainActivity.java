package unpsjb.wikiprecios;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.FacebookSdk;

/**
 * Esta clase se ocupa de generar todos los fragmentos y de coordinador los mensajes
 * entre ellos.
 */
public class MainActivity extends AppCompatActivity implements OnClickBtnListener{
    private static final String TAG = MainActivity.class.getSimpleName();

//  Fragmentos
    SettingsFragment settingFragment;
    LoginFragment loginFragment;
    RegisterFragment registerFragment;
    MenuFragment menuFragment;

    Context context;//Referencia al contexto
    SessionManager sessionManager;// Se ocupa de la sesion de usuario local
    DialogManager dialogManager; // Se encarga del control de dialogo
    CloseApp closeApp; // Se ocupa de cerrar la App
    CloseSession closeSession; // Se ocupa de cerrar la cesion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app);
        context = getApplicationContext();

        // Se inicializa el API de facebook
        FacebookSdk.sdkInitialize(context);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Declaracion de fragmentos
        loginFragment = new LoginFragment();
        settingFragment = new SettingsFragment();
        registerFragment = new RegisterFragment();
        menuFragment = new MenuFragment();

        // Nombrar a MenuActivity como coordinador
        loginFragment.setCoordinator(this);
        registerFragment.setCoordinator(this);
        menuFragment.setCoordinator(this);

        // Mas inicializaciones
        sessionManager = SessionManager.getInstance(context);
        dialogManager = new DialogManager();
        closeApp = new CloseApp(this);
        closeSession = new CloseSession(this);

        // Si existe un usuario logeado y preferio dejar la sesion iniciada, vamos al menu
        if (sessionManager.isLoggedIn() && AppPreference.isPrefSessionLoged(context)) {
            addFragment(menuFragment);
        }else {
            // En otro caso vamos a la vista de login
            addFragment(loginFragment);
        }

    }

    @Override
    // Aqui se obtiene el resultado del login de facebook, que es enviado al callbackManager
    // en loginFragment
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        loginFragment.callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings  && !settingFragment.isVisible()) {
            addFragment(settingFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public Context getContext() { return context; }

    @Override
    public void viewLogin() {
        addFragment(loginFragment);
    }

    @Override
    public void viewRegister() {
        addFragment(registerFragment);
    }

    @Override
    public void viewMenu() {
        addFragment(menuFragment);
    }

    @Override
    public void closeView() {
        confirmClose("Esta seguro que desea salir?",closeApp);
    }

    @Override
    public void back() { getFragmentManager().popBackStack(); }

    @Override
    public void logout() {
        confirmClose("Esta seguro que desea cerrar la sesión",closeSession);
    }


    // agrega el fragmento indicado a la vista.
    private void addFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                .addToBackStack(null).commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            confirmClose("Esta seguro que desea salir?", closeApp);
        }
        return super.onKeyDown(keyCode, event);
    }

    // Realiza la confirmación de algo (ej: cerrar cesion, cerrar App)
    public void confirmClose(String question, DialogListener dialogListener){
        if(menuFragment.isVisible() || loginFragment.isVisible()){
            dialogManager.viewAlertDialog(MainActivity.this,question,dialogListener );
        }
    }

}
