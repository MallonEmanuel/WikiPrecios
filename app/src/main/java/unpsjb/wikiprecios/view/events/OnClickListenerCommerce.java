package unpsjb.wikiprecios.view.events;

import android.view.View;
import android.widget.AdapterView;

import unpsjb.wikiprecios.model.Commerce;
import unpsjb.wikiprecios.view.Coordinator;

/**
 * Esta clase se encarga de manejar los eventos que surgen en la lista de comercios.
 */
public class OnClickListenerCommerce implements AdapterView.OnItemClickListener {

    private Coordinator coordinator;

    public OnClickListenerCommerce(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Commerce commerce = (Commerce) adapterView.getAdapter().getItem(i);
        coordinator.findSuggestedPrices(commerce);
    }
}