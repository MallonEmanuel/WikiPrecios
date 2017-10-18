package unpsjb.wikiprecios.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.FacebookSdk;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import unpsjb.wikiprecios.controller.parser.CategoryParser;
import unpsjb.wikiprecios.controller.parser.CommerceParser;
import unpsjb.wikiprecios.controller.parser.JsonParser;
import unpsjb.wikiprecios.controller.parser.SpecialProductParser;
import unpsjb.wikiprecios.http.CategoryHttpClient;
import unpsjb.wikiprecios.http.LoginHttpClient;
import unpsjb.wikiprecios.http.RegisterHttpClient;
import unpsjb.wikiprecios.http.SaveFavouriteCommerceHttpClient;
import unpsjb.wikiprecios.service.LocationService;
import unpsjb.wikiprecios.http.CommerceHttpClient;
import unpsjb.wikiprecios.http.NearbyCommerceHttpClient;
import unpsjb.wikiprecios.http.SpecialProductHttpClient;
import unpsjb.wikiprecios.http.SuggestedPriceHttpClient;
import unpsjb.wikiprecios.model.Category;
import unpsjb.wikiprecios.model.Commerce;
import unpsjb.wikiprecios.model.Query;
import unpsjb.wikiprecios.view.listview.ListViewCategoryFragment;
import unpsjb.wikiprecios.view.listview.ListViewFragment;
import unpsjb.wikiprecios.view.listview.ListViewNearbyCommerceFragment;
import unpsjb.wikiprecios.view.listview.ListViewSpecialProductFragment;
import unpsjb.wikiprecios.view.util.CloseApp;
import unpsjb.wikiprecios.view.util.CloseSession;
import unpsjb.wikiprecios.view.util.DialogListener;
import unpsjb.wikiprecios.view.util.DialogManager;
import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.controller.SessionManager;
import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.view.util.Message;
import unpsjb.wikiprecios.view.util.SaveFavourite;

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
    ListViewFragment listViewNearbyCommerceFragment;
    ListViewFragment listViewCategoryFragment;
    ListViewFragment listViewSpecialProductFragment;


    PriceFragment priceFragment;
    TabFragment tabFragment;

    Context context;//Referencia al contexto
    SessionManager sessionManager;// Se ocupa de la sesion de usuario
    DialogManager dialogManager; // Se encarga del control de dialogo
    CloseApp closeApp; // Se ocupa de cerrar la App
    CloseSession closeSession; // Se ocupa de cerrar la cesion
    LocationService locationService;
    private Query query;

    private ProgressDialog pDialog;


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
        // TODO refactorizar todas las clases cn getInstance
        listViewNearbyCommerceFragment = new ListViewNearbyCommerceFragment();
        listViewCategoryFragment = new ListViewCategoryFragment();
        listViewSpecialProductFragment = new ListViewSpecialProductFragment();

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

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        // Si existe un usuario logeado y preferio dejar la sesion iniciada, vamos al menu
        if (sessionManager.isLoggedIn() && AppPreference.isPrefSessionLoged(context)) {
            addFragment(menuFragment);
        }else {
            // En otro caso vamos a la vista de login
            addFragment(loginFragment);
        }

    }

    @Override
    public void showDialog(String message) {
        if (!pDialog.isShowing()){
            pDialog.setMessage(message);
            pDialog.show();
        }
    }

    @Override
    public void checkLogin(String mail, String password) {
        LoginHttpClient client = new LoginHttpClient(this,context);
        client.setEmail(mail);
        client.setPassword(password);
        client.sendRequest();
    }

    @Override
    public void registerUser(String surname, String name, String email, String password) {
        RegisterHttpClient client = new RegisterHttpClient(this,context);
        client.setName(name);
        client.setSurname(surname);
        client.setEmail(email);
        client.setPassword(password);
        client.sendRequest();
    }

    @Override
    public void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    // Aqui se obtiene el resultado del login de facebook, que es enviado al callbackManager
    // en loginFragment
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        loginFragment.getCallbackManager().onActivityResult(requestCode, resultCode, data);
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
        CommerceHttpClient finder = new NearbyCommerceHttpClient(this,context);
        finder.sendRequest();
    }

    @Override
    public void viewNearbyCommerces(Object data) {
        Bundle bundle = new Bundle();
        List<Parcelable> list = JsonParser.parseList(new CommerceParser(),data.toString());
        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        bundle.putString("title",context.getText(R.string.title_commerce).toString());
        listViewNearbyCommerceFragment.setArguments(bundle);
        addFragment(listViewNearbyCommerceFragment);
    }

    @Override
    public void findSuggestedPrices(Commerce commerce) {
        query.setCommerce(commerce);
        SuggestedPriceHttpClient finder = new SuggestedPriceHttpClient(this,context);
        finder.setQuery(query);
        finder.sendRequest();
    }

    @Override
    public void findCategories() {
        if(!locationService.isCanGetLocation()){
            Message.show(context,context.getString(R.string.msg_services_disable));
        }else {
            query = new Query();
            query.setLocation(locationService.getLocation());
            CategoryHttpClient finder = new CategoryHttpClient(this,context);
            finder.sendRequest();
        }
    }

    @Override
    public void viewCategories(Object data) {
        Bundle bundle = new Bundle();
        List<Parcelable> list = JsonParser.parseList(new CategoryParser(),data.toString());
        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        bundle.putString("title",context.getText(R.string.title_category).toString());
        listViewCategoryFragment.setArguments(bundle);
        addFragment(listViewCategoryFragment);

    }

    @Override
    public void findSpecialProducts(Category category) {
        SpecialProductHttpClient finder = new SpecialProductHttpClient(this,context);
        finder.setCategory(category);
        finder.sendRequest();
    }

    @Override
    public void viewSpecialProducts(Object data) {
        Bundle bundle = new Bundle();
        List<Parcelable> list = JsonParser.parseList(new SpecialProductParser(),data.toString());
        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        bundle.putString("title",context.getText(R.string.title_special_product).toString());
        listViewSpecialProductFragment.setArguments(bundle);
        addFragment(listViewSpecialProductFragment);
    }

    @Override
    public void findCommerces() {
        CommerceHttpClient finder = new CommerceHttpClient(this,context);
        finder.sendRequest();
    }

    @Override
    public void viewAlertDialog(String question, DialogListener dialogListener) {
        dialogManager.viewAlertDialog(this,question,dialogListener);
    }


    @Override
    public void viewCommerces(Object data) {
        Bundle bundle = new Bundle();
        List<Parcelable> list = JsonParser.parseList(new CommerceParser(),data.toString());
        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        bundle.putString("title","");

        tabFragment.setArguments(bundle);
        addFragment(tabFragment);
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
    public void saveFavourites() {
        SaveFavouriteCommerceHttpClient client = new SaveFavouriteCommerceHttpClient(this,context);
        client.setEmail(sessionManager.getUserLoged());
        String commerces = "";
        List list = tabFragment.getArguments().getParcelableArrayList("list");
        for (int i = 0 ;  i < list.size() ;i++){
            Commerce commerce = (Commerce) list.get(i);
            if(commerce.isFavourite()){
                commerces+=""+commerce.getId()+",";
            }
        }
        if(commerces.isEmpty()){
            commerces = "-1";
        }
        client.setCommerces(commerces);
        client.sendRequest();
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
            if(tabFragment.isVisible()){
                saveFavourites();
                back();
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    // Realiza la confirmación de algo (ej: cerrar cesion, cerrar App)
    public void confirmClose(String question, DialogListener dialogListener){
        if(menuFragment.isVisible() || loginFragment.isVisible()){
            viewAlertDialog(question,dialogListener);
        }
    }

}
