package unpsjb.wikiprecios.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.FacebookSdk;

import unpsjb.wikiprecios.controller.CategoryFinder;
import unpsjb.wikiprecios.controller.CategoryParser;
import unpsjb.wikiprecios.controller.CommerceParser;
import unpsjb.wikiprecios.controller.LocationService;
import unpsjb.wikiprecios.controller.CommerceFinder;
import unpsjb.wikiprecios.controller.SpecialProductFinder;
import unpsjb.wikiprecios.controller.SpecialProductParser;
import unpsjb.wikiprecios.controller.SuggestedPriceFinder;
import unpsjb.wikiprecios.model.Category;
import unpsjb.wikiprecios.model.Commerce;
import unpsjb.wikiprecios.model.Query;
import unpsjb.wikiprecios.model.SpecialProduct;
import unpsjb.wikiprecios.view.util.CloseApp;
import unpsjb.wikiprecios.view.util.CloseSession;
import unpsjb.wikiprecios.view.util.DialogListener;
import unpsjb.wikiprecios.view.util.DialogManager;
import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.controller.SessionManager;
import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.view.util.OnClickListenerCategory;
import unpsjb.wikiprecios.view.util.OnClickListenerCommerce;
import unpsjb.wikiprecios.view.util.Message;
import unpsjb.wikiprecios.view.util.OnClickListenerSpecialProduct;

/**
 * Esta clase se ocupa de generar todos los fragmentos y de coordinador los mensajes
 * entre ellos.
 */
public class MainActivity extends AppCompatActivity implements Coordinator {
    private static final String TAG = MainActivity.class.getSimpleName();

//  Fragmentos
    SettingsFragment settingFragment;
    LoginFragment loginFragment;
    RegisterFragment registerFragment;
    MenuFragment menuFragment;
    BarcodeScannerFragment barcodeScannerFragment;
    ListViewFragment listViewFragment;
    PriceFragment priceFragment;
    TabFragment tabFragment;

    Context context;//Referencia al contexto
    SessionManager sessionManager;// Se ocupa de la sesion de usuario
    DialogManager dialogManager; // Se encarga del control de dialogo
    CloseApp closeApp; // Se ocupa de cerrar la App
    CloseSession closeSession; // Se ocupa de cerrar la cesion
    LocationService locationService;
    private Query query;

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
        loginFragment = (LoginFragment) LoginFragment.getInstance();
        settingFragment = new SettingsFragment();
        registerFragment = new RegisterFragment();
        menuFragment = new MenuFragment();
        barcodeScannerFragment = new BarcodeScannerFragment();
        listViewFragment = new ListViewFragment();
        priceFragment = new PriceFragment();
        tabFragment = new TabFragment();



        // Nombrar a MenuActivity como coordinador
        loginFragment.setCoordinator(this);
        registerFragment.setCoordinator(this);
        menuFragment.setCoordinator(this);
        barcodeScannerFragment.setCoordinator(this);
        priceFragment.setCoordinator(this);

        // Mas inicializaciones
        locationService = LocationService.getInstance(context);
        sessionManager = SessionManager.getInstance(context);
        dialogManager = new DialogManager();
        closeApp = new CloseApp(this,context);
        closeSession = new CloseSession(this,context);

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
    public void viewBarcodeScanner() {
        if(!locationService.isCanGetLocation()){
             Message.show(context,context.getString(R.string.msg_services_disable));
             return;
        }else {
            query = new Query();
            query.setLocation(locationService.getLocation());
            addFragment(barcodeScannerFragment);
        }
    }

    @Override
    public void findNearbyCommerces(String code) {
        query.setBarcode(code);
        CommerceFinder finder = new CommerceFinder(this,context);
        finder.sendRequest();
    }

    @Override
    public void viewNearbyCommerces(Object data) {
        listViewFragment.setData(data);
        listViewFragment.setTitle(context.getText(R.string.title_commerce).toString());
        listViewFragment.setOnItemClickListener(new OnClickListenerCommerce(this));
        listViewFragment.setParser(new CommerceParser());
        addFragment(listViewFragment);
    }

    @Override
    public void findSuggestedPrices(Commerce commerce) {
        query.setCommerce(commerce);
        SuggestedPriceFinder finder = new SuggestedPriceFinder(this,context);
        finder.setQuery(query);
        finder.sendRequest();
    }

    @Override
    public void findCategories() {
        if(!locationService.isCanGetLocation()){
            Message.show(context,context.getString(R.string.msg_services_disable));
            return;
        }else {
            query = new Query();
            query.setLocation(locationService.getLocation());
            CategoryFinder finder = new CategoryFinder(this,context);
            finder.sendRequest();
        }
    }

    @Override
    public void viewCategories(Object data) {
        listViewFragment.setData(data);
        listViewFragment.setTitle(context.getText(R.string.title_category).toString());
        listViewFragment.setOnItemClickListener(new OnClickListenerCategory(this));
        listViewFragment.setParser(new CategoryParser());
        addFragment(listViewFragment);
    }

    @Override
    public void findSpecialProducts(Category category) {
        SpecialProductFinder finder = new SpecialProductFinder(this,context);
        finder.setCategory(category);
        finder.sendRequest();
    }

    @Override
    public void viewSpecialProducts(Object data) {
        ListViewFragment listViewFragment1 = new ListViewFragment();
        listViewFragment1.setData(data);
        listViewFragment1.setTitle(context.getText(R.string.title_special_product).toString());
        listViewFragment1.setOnItemClickListener(new OnClickListenerSpecialProduct(this));
        listViewFragment1.setParser(new SpecialProductParser());
        addFragment(listViewFragment1);
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


    @Override
    public void viewPrice() {
        priceFragment.setQuery(query);
        addFragment(priceFragment);
    }

    @Override
    public void viewCommercesFavorites() {
        addFragment(tabFragment);
    }



    // agrega el fragmento indicado a la vista.
    private void addFragment(Fragment fragment) {
//        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
//                .addToBackStack(null).commit();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

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
            dialogManager.viewAlertDialog(this,question,dialogListener );
        }
    }

}
