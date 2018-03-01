package unpsjb.wikiprecios.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.FacebookSdk;

import java.util.ArrayList;
import java.util.List;

import unpsjb.wikiprecios.controller.parser.CategoryParser;
import unpsjb.wikiprecios.controller.parser.CommerceParser;
import unpsjb.wikiprecios.controller.parser.JsonParser;
import unpsjb.wikiprecios.controller.parser.PriceParser;
import unpsjb.wikiprecios.controller.parser.SpecialProductParser;
import unpsjb.wikiprecios.controller.parser.SuggestedPriceParser;
import unpsjb.wikiprecios.http.CategoryHttpClient;
import unpsjb.wikiprecios.http.LoginFacebookHttpClient;
import unpsjb.wikiprecios.http.LoginHttpClient;
import unpsjb.wikiprecios.http.RegisterHttpClient;
import unpsjb.wikiprecios.http.SaveCommerceHttpClient;
import unpsjb.wikiprecios.http.SaveFavouriteCommerceHttpClient;
import unpsjb.wikiprecios.http.SavePriceHttpClient;
import unpsjb.wikiprecios.model.Price;
import unpsjb.wikiprecios.model.User;
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
import unpsjb.wikiprecios.view.listview.ListViewPriceFragment;
import unpsjb.wikiprecios.view.listview.ListViewSpecialProductFragment;
import unpsjb.wikiprecios.view.listview.ListViewSuggestedPriceFragment;
import unpsjb.wikiprecios.view.util.CloseApp;
import unpsjb.wikiprecios.view.util.CloseSession;
import unpsjb.wikiprecios.view.util.DialogListener;
import unpsjb.wikiprecios.view.util.DialogManager;
import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.controller.SessionManager;
import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.view.util.Message;

/**
 * Esta clase se ocupa de generar todos los fragmentos y de coordinador los mensajes
 * entre ellos. Implementa todos los metodos de Coordinator.
 * @see Coordinator
 */
public class MainActivity extends AppCompatActivity implements Coordinator {
    private static final String TAG = MainActivity.class.getSimpleName();
//  Fragmentos
    SettingsFragment settingFragment;
    LoginFragment loginFragment;
    RegisterFragment registerFragment;
    MenuFragment menuFragment;
    BarcodeScannerFragment barcodeScannerFragment;
    ScannerFragment scannerFragment;

    ListViewFragment listViewNearbyCommerceFragment;
    ListViewFragment listViewCategoryFragment;
    ListViewFragment listViewSpecialProductFragment;
    ListViewFragment listViewSuggestedPriceFragment;
    ListViewFragment listViewPriceFragment;

    PriceFragment priceFragment;
    TabFragment tabFragment;

    Context context;//Referencia al contexto
    SessionManager sessionManager;// Se ocupa de la sesion de usuario
    DialogManager dialogManager; // Se encarga del control de dialogo
    CloseApp closeApp; // Se ocupa de cerrar la App
    CloseSession closeSession; // Se ocupa de cerrar la cesion
    LocationService locationService;
    ProgressDialog pDialog;
    Toolbar toolbar;

    MenuItem menuItemQualification;
    MenuItem menuItemAccumulated;
    private Query query;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app);
        context = getApplicationContext();

        // Se inicializa el API de facebook
        FacebookSdk.sdkInitialize(context);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Declaracion de fragmentos
        loginFragment = (LoginFragment) LoginFragment.getInstance();
        settingFragment = new SettingsFragment();
        registerFragment = new RegisterFragment();
        menuFragment = new MenuFragment();
        barcodeScannerFragment = new BarcodeScannerFragment();
        scannerFragment = new ScannerFragment();

        // TODO refactorizar todas las clases cn getInstance
        listViewNearbyCommerceFragment = new ListViewNearbyCommerceFragment();
        listViewCategoryFragment = new ListViewCategoryFragment();
        listViewSpecialProductFragment = new ListViewSpecialProductFragment();
        listViewSuggestedPriceFragment = new ListViewSuggestedPriceFragment();
        listViewPriceFragment = new ListViewPriceFragment();

        priceFragment = new PriceFragment();
        tabFragment = new TabFragment();

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
            String mail = SessionManager.getInstance(context).getUserLoged();
            String password = SessionManager.getInstance(context).getUserPassword();
            checkLogin(mail,password,true);
            addFragment(menuFragment);
        }else {
            // En otro caso vamos a la vista de login
            addFragment(loginFragment);
        }

    }


    @Override
    public void checkLogin(String mail, String password,boolean background) {
        LoginHttpClient client = new LoginHttpClient(this,context);
        client.setEmail(mail);
        client.setPassword(password);
        client.setBackground(background);
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
    public void showDialog(String message) {
        if (!pDialog.isShowing()){
            pDialog.setMessage(message);
            pDialog.show();
        }
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

        menuItemAccumulated = menu.findItem(R.id.actionAccumulated);
        //View view = buildView(R.layout.counter_menuitem_accumulated_layout);
        menuItemAccumulated.setIcon(buildCounterDrawable(0,  R.drawable.ic_camera,R.id.count_accumulated,R.layout.counter_menuitem_accumulated_layout));

        menuItemQualification = menu.findItem(R.id.actionQualification);
        //View view2 = buildView(R.layout.counter_menuitem_qualification_layout);
        menuItemQualification.setIcon(buildCounterDrawable(0,  R.drawable.ic_star3,R.id.count_qualification,R.layout.counter_menuitem_qualification_layout));

        if(user == null){
            menuItemAccumulated.setVisible(false);
            menuItemQualification.setVisible(false);
        }else{
            setAccumulatedIcon(user.getAccumulated());
            setQualificationIcon(user.getQualification());
        }

        return true;
    }

    private void setAccumulatedIcon(int count){
        menuItemAccumulated.setIcon(buildCounterDrawable(count,  R.drawable.ic_camera,R.id.count_accumulated,R.layout.counter_menuitem_accumulated_layout));
    }

    private void setQualificationIcon(int count){
        menuItemQualification.setIcon(buildCounterDrawable(count,  R.drawable.ic_star3,R.id.count_qualification,R.layout.counter_menuitem_qualification_layout));
    }

    private View buildView(int layout){
        LayoutInflater inflater = LayoutInflater.from(this);
        return inflater.inflate(layout, null);
    }

    private Drawable buildCounterDrawable(int count, int backgroundImageId, int i,int layoutId) {

        View view = buildView(layoutId);
        view.setBackgroundResource(backgroundImageId);

        TextView textView = (TextView) view.findViewById(i);
        textView.setText("" + count);


        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
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
    public void viewLogin() {
        addFragment(loginFragment);
    }

    @Override
    public void viewRegister() {
        addFragment(registerFragment);
    }

    @Override
    public void viewMenu() { addFragment(menuFragment); }

    @Override
    public void viewBarcodeScanner() {
        if(!locationService.isCanGetLocation()){
             Message.show(context,context.getString(R.string.msg_services_disable));
        }else {
            query = new Query();
            query.setLocation(locationService.getLocation());
            //addFragment(barcodeScannerFragment);
            addFragment(scannerFragment);
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
//        Commerce addCommerce = new Commerce(-1,"Agregar comercio","Si el comercio no se encuentra en la lista, por favor agreguelo",R.drawable.ic_add2);
//        list.add(addCommerce );
        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        bundle.putString("title",context.getText(R.string.title_commerce).toString());
        setArguments(listViewNearbyCommerceFragment,bundle);
        addFragment(listViewNearbyCommerceFragment);
    }

    @Override
    public void findSuggestedPrices(Commerce commerce) {
        query.setCommerce(commerce);
        SuggestedPriceHttpClient client = new SuggestedPriceHttpClient(this,context);
        client.setQuery(query);
        client.sendRequest();
    }

    @Override
    public void viewSuggestedPrices(Object data){
        Bundle bundle = new Bundle();
        List<Parcelable> list = JsonParser.parseList(new SuggestedPriceParser(),data.toString());
        for(int i = 0; i < list.size();i++){
            ((Price)list.get(i)).setCommerce(query.getCommerce().getName());
        }
        Price addPrice = new Price(-1,"Ingresar manualmente","Si el precio no se encuentra en la lista, ingreselo manualmente");
        addPrice.setCategory(R.drawable.ic_add2);
        list.add(addPrice );
        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        bundle.putString("title",context.getText(R.string.title_suggested_price).toString());
        setArguments(listViewSuggestedPriceFragment,bundle);
        addFragment(listViewSuggestedPriceFragment);
    }

    @Override
    public void viewPrice() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("query",query);
        setArguments(priceFragment,bundle);
        addFragment(priceFragment);
    }


    @Override
    public void savePrice() {
        SavePriceHttpClient client = new SavePriceHttpClient(this,context);
        client.setQuery(query);
        client.sendRequest();
    }

    @Override
    public void savePrice(Price price){
        query.setPrice(Double.parseDouble(price.getPrice()));
        savePrice();
    }

    @Override
    public void viewPrices(Object data) {
        Bundle bundle = new Bundle();
        List<Parcelable> list = JsonParser.parseList(new PriceParser(),data.toString());
        Price price = new Price(R.drawable.ic_exit,"Volver al menu principal","Salir",-1);
        list.add(price);

        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        bundle.putString("title",context.getText(R.string.title_result_price).toString());
        setArguments(listViewPriceFragment,bundle);
        addFragment(listViewPriceFragment);

    }

    @Override
    public void login_facebook(String id, String name, String surname, String email) {
        LoginFacebookHttpClient client= new LoginFacebookHttpClient(this,context);
        client.setEmail(email);
        client.setName(name);
        client.setSurname(surname);
        client.setFacebookId(id);
        client.sendRequest();

    }

    @Override
    public void setUser(User user) {
        this.user = user;
        if(user != null){
        setAccumulatedIcon(user.getAccumulated());
        setQualificationIcon(user.getQualification());
        menuItemQualification.setVisible(true);
        menuItemAccumulated.setVisible(true);
        }else {
            menuItemQualification.setVisible(false);
            menuItemAccumulated.setVisible(false);
        }
    }


    @Override
    public void viewMap() {
//        List list = listViewNearbyCommerceFragment.getArguments().getParcelableArrayList("list");
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        MapFragment mapFragment = new MapFragment();
//        setArguments(mapFragment,bundle);
        addFragment(mapFragment);
    }

    @Override
    public void saveCommerce(Location location, String name) {
        SaveCommerceHttpClient client = new SaveCommerceHttpClient(this,context);
        client.setName(name);
        client.setLocation(location);
        client.setInProcess(true);
        client.sendRequest();
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
        setArguments(listViewCategoryFragment,bundle);
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
        setArguments(listViewSpecialProductFragment,bundle);
        addFragment(listViewSpecialProductFragment);
    }

    @Override
    public void findCommerces() {
        CommerceHttpClient finder = new CommerceHttpClient(this,context);
        finder.sendRequest();
    }

    @Override
    public void viewCommerces(Object data) {
        Bundle bundle = new Bundle();
        List<Parcelable> list = JsonParser.parseList(new CommerceParser(),data.toString());
        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        bundle.putString("title","");
        setArguments(tabFragment,bundle);
        addFragment(tabFragment);
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
        }else{
            commerces = commerces.substring(0,commerces.length()-1);
        }
        client.setCommerces(commerces);
        client.sendRequest();
    }

    @Override
    public void viewAlertDialog(String question, DialogListener dialogListener) {
        dialogManager.viewAlertDialog(this,question,dialogListener);
    }


    @Override
    public void closeView() {
        confirmClose("Esta seguro que desea salir?",closeApp);
    }

    @Override
    public void back() { getSupportFragmentManager().popBackStack(); }

    @Override
    public void logout() {
        confirmClose("Esta seguro que desea cerrar la sesión",closeSession);
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
            }
            if(listViewPriceFragment.isVisible()){
                viewMenu();
                return false;
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

    public void setArguments(Fragment fragment, Bundle bundle){
        if(fragment.getArguments() != null){
            fragment.getArguments().clear();
            fragment.getArguments().putAll(bundle);
        }else{
            fragment.setArguments(bundle);
        }
    }
}
