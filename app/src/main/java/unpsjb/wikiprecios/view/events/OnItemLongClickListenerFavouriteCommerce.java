package unpsjb.wikiprecios.view.events;

import unpsjb.wikiprecios.view.Coordinator;

/**
 * Esta clase se encarga de manejar los eventos que surgen en la lista de Comercios Favoritos
 */
public class OnItemLongClickListenerFavouriteCommerce extends OnItemLongClickListenerCommerce {


    public OnItemLongClickListenerFavouriteCommerce(Coordinator coordinator) {
        super(coordinator);
    }

    public void posiviteResult() {
        if(commerce.isFavourite()){
            commerce.setFavourite(false);
            this.arrayAdapter.remove(commerce);
        }else {
            commerce.setFavourite(true);
        }
        arrayAdapter.notifyDataSetChanged();
    }


}
