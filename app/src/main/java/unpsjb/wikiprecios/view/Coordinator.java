package unpsjb.wikiprecios.view;

import android.content.Context;

import unpsjb.wikiprecios.model.Category;
import unpsjb.wikiprecios.model.Commerce;
import unpsjb.wikiprecios.model.Query;

/**
 * Created by emanuel on 20/09/17.
 * Esta clase permite coordinar todas las acciones entre los fragmentos, tambien permite compartir
 * una Query.
 */
public interface Coordinator {

//    Context getContext(); // TODO si es posible quitar el getCOntext()
//    Query getQuery();
//    void setQuery(Query query);
    // TODO Crear otra interface para getQuery y set Query e extender de ella
    // TODO Tambien crear OnClickListener y extender de ella

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

    void viewCommercesFavorites();

    void viewSpecialProducts(Object data);

    void viewCategories(Object data);

    void findNearbyCommerces(String code);

    void findSuggestedPrices(Commerce commerce);

    void findCategories();

    void findSpecialProducts(Category category);
}
