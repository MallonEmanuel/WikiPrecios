package unpsjb.wikiprecios.view;

import android.location.Location;

import unpsjb.wikiprecios.model.Category;
import unpsjb.wikiprecios.model.Commerce;
import unpsjb.wikiprecios.model.Price;
import unpsjb.wikiprecios.model.User;
import unpsjb.wikiprecios.view.util.DialogListener;

/**
 * Esta clase permite coordinar todas las acciones entre los fragmentos, tambien permite compartir
 * una Query y actualizar la información del usuario
 */
public interface Coordinator {

    /** Permite ver la vista de login**/
    void viewLogin();
    /** Permite ver la vista de registro de usuario**/
    void viewRegister();
    /** Permite ver el menu principal **/
    void viewMenu();
    /** Permite ver el escaner de codigos de barras **/
    void viewBarcodeScanner();
    /** Consulta si desea cerrar la app **/
    void closeView(); // Consulta si desea cerrar la App
    /** Vuelve al fragmento anterior **/
    void back(); // Vuelve hacia atras en la pila de fragments
    /** Consulta si desea cerrar la sesion de usuario **/
    void logout(); // Consulta si desea cerrar sesión
    /** Cierra la aplicacion**/
    void finish(); // Cierra la App
    /** Muestra los comercios cercanos en una lista **/
    void viewNearbyCommerces(Object data);
    /** Muestra el formulario para ingresar el precio manualmente **/
    void viewPrice();
    /** Muestra los comercios en una lista **/
    void viewCommerces(Object data);
    /** Muestra los productos especiales en una lista. **/
    void viewSpecialProducts(Object data);
    /** Muestra las categorias en una lista.**/
    void viewCategories(Object data);
    /** Busca los comercios cercanos a un usuario.**/
    void findNearbyCommerces(String code);
    /** Busca los precios sugeridos **/
    void findSuggestedPrices(Commerce commerce);
    /** muestra los comercios cercanos en una lista**/
    void viewSuggestedPrices(Object data);
    /** Busca las categorias.**/
    void findCategories();
    /** Busca los productos especiales pertenecientes a una categoria.**/
    void findSpecialProducts(Category category);
    /** Busca los comercios.**/
    void findCommerces();
    /** Muestra un Dialogo de alerta, que tiene dos posibles resultados.**/
    void viewAlertDialog(String question,DialogListener dialogListener);
    /** Guarda los favoritos de un usuario.**/
    void saveFavourites();
    /** Oculta el dialogo, que indica que algo se esta cargando.**/
    void hideDialog();
    /** Muestra un dialogo, indicando que algo se esta cargando**/
    void showDialog(String string);
    /** Chequea un usuario.**/
    void checkLogin(String mail, String password,boolean background);
    /** Intenta registrar a un usuario.**/
    void registerUser(String surname, String name, String email, String password);
    /** Guarda el precio de un producto.**/
    void savePrice();
    /** Guarda el precio de un producto.**/
    void savePrice(Price price);
    /** Muestra un mapa iterativo.**/
    void viewMap();
    /** Guarda un nuevo comercio. **/
    void saveCommerce(Location location, String name);
    /** Muestra los precios resultantes.**/
    void viewPrices(Object data);
    /** Permite logear un usuario de facebook**/
    void login_facebook(String id, String name, String surname, String email);
    /** Setea el usuario.**/
    void setUser(User user);
}
