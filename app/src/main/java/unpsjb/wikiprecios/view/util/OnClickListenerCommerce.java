package unpsjb.wikiprecios.view.util;

import android.view.View;
import android.widget.AdapterView;

import unpsjb.wikiprecios.model.Commerce;
import unpsjb.wikiprecios.view.Coordinator;

/**
 * Created by emanuel on 06/10/17.
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