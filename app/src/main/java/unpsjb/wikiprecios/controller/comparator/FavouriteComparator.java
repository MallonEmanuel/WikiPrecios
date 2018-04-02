package unpsjb.wikiprecios.controller.comparator;

import java.util.Comparator;

import unpsjb.wikiprecios.model.Price;

/**
 * Created by emanuel on 28/02/18.
 */
public class FavouriteComparator implements Comparator {
    @Override
    public int compare(Object o, Object t1) {
        Price p1 = (Price) o;
        Price p2 = (Price) t1;
        if(p1.getId() == -1){
            return 1;
        }
        if(p2.getId() == -1){
            return -1;
        }
        if(p1.isFavourite() && p2.isFavourite()) {
            return 1;
        }
        if(p1.isFavourite()){
            return -1;
        }else
            return 1;
    }
}
