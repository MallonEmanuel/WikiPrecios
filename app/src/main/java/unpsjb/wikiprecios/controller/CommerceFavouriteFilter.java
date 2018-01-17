package unpsjb.wikiprecios.controller;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import unpsjb.wikiprecios.model.Commerce;

/**
 * Esta clase se encarga de filtrar los comercios favoritos de una lista de comercios.
 */
public class CommerceFavouriteFilter implements Filter {

    @Override
    public List filter(List list) {

        List lst = new ArrayList<>();
        for (int n = 0; n < list.size(); n++) {
            Commerce commerce = (Commerce) list.get(n);
            if(commerce.isFavourite()) {
                lst.add(commerce);
            }
        }
        return lst;
    }
}
