package unpsjb.wikiprecios.view.events;

import android.view.View;
import android.widget.AdapterView;

import unpsjb.wikiprecios.model.Commerce;
import unpsjb.wikiprecios.view.Coordinator;

/**
 * Created by emanuel on 05/10/17.
 */
public class OnClickListenerNearbyCommerce implements AdapterView.OnItemClickListener {

    private Coordinator coordinator;

    public OnClickListenerNearbyCommerce(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Commerce commerce = (Commerce) adapterView.getAdapter().getItem(i);
        if(commerce.getId() == -1){
            coordinator.viewMap();
        }else{
            coordinator.findSuggestedPrices(commerce);
        }

    }
}
