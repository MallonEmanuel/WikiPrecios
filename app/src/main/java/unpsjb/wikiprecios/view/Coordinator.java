package unpsjb.wikiprecios.view;

import android.location.Location;

import unpsjb.wikiprecios.model.Category;
import unpsjb.wikiprecios.model.Commerce;
import unpsjb.wikiprecios.model.Price;
import unpsjb.wikiprecios.view.util.DialogListener;

/**
 * Created by emanuel on 20/09/17.
 * Esta clase permite coordinar todas las acciones entre los fragmentos, tambien permite compartir
 * una Query.
 */
public interface Coordinator {

    void viewLogin();
    void viewRegister();
    void viewMenu();
    void viewBarcodeScanner();

    void closeView(); // Consulta si desea cerrar la App

    void back(); // Vuelve hacia atras en la pila de fragments
    void logout(); // Consulta si desea cerrar sesión

    void finish(); // Cierra la App

    void viewNearbyCommerces(Object data);

    void viewPrice();



    void viewCommerces(Object data);

    void viewSpecialProducts(Object data);

    void viewCategories(Object data);

    void findNearbyCommerces(String code);

    void findSuggestedPrices(Commerce commerce);

    void viewSuggestedPrices(Object data);

    void findCategories();

    void findSpecialProducts(Category category);

    void findCommerces();

    void viewAlertDialog(String question,DialogListener dialogListener);

    void saveFavourites();

    void hideDialog();

    void showDialog(String string);

    void checkLogin(String mail, String password);

    void registerUser(String surname, String name, String email, String password);

    void savePrice();

    void savePrice(Price price);

    void viewMap();

    void saveCommerce(Location location, String name);

    void viewPrices(Object data);
}
