package unpsjb.wikiprecios.view.events;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import unpsjb.wikiprecios.model.Commerce;
import unpsjb.wikiprecios.view.Coordinator;
import unpsjb.wikiprecios.view.listview.ListViewFavouriteCommerceFragment;
import unpsjb.wikiprecios.view.listview.ListViewCommerceFragment;
import unpsjb.wikiprecios.view.util.DialogListener;
import unpsjb.wikiprecios.view.util.Message;

/**
 * Esta clase se encarga de manejar los eventos que surgen en la lista de Comercios.
 */
public class OnItemLongClickListenerCommerce implements AdapterView.OnItemLongClickListener, DialogListener {

    Coordinator coordinator;
    Commerce commerce;
    ArrayAdapter arrayAdapter;

    public OnItemLongClickListenerCommerce(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        arrayAdapter = ((ArrayAdapter)adapterView.getAdapter());
        if(i == -1){
            return false;
        }
        commerce = (Commerce) adapterView.getAdapter().getItem(i);
        if(commerce.isFavourite()){
            coordinator.viewAlertDialog("Desea quitar de la lista de favoritos?",this);
        }else {
            coordinator.viewAlertDialog("Desea agregar a lista de favoritos?",this);
        }
        return true;
    }

    @Override
    public void posiviteResult() {
        if(commerce.isFavourite()){
            commerce.setFavourite(false);
        }else {
            commerce.setFavourite(true);
        }
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void negativeResult() {

    }
}
