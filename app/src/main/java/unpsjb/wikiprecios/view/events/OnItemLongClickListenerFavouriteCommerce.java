package unpsjb.wikiprecios.view.events;

import unpsjb.wikiprecios.view.Coordinator;

/**
 * Created by emanuel on 13/10/17.
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
