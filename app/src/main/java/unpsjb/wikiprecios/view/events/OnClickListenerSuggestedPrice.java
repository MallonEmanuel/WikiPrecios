package unpsjb.wikiprecios.view.events;

import android.view.View;
import android.widget.AdapterView;

import java.io.Serializable;

import unpsjb.wikiprecios.model.Price;
import unpsjb.wikiprecios.view.Coordinator;

/**
 * Esta clase se encarga de manejar los eventos que surgen en la lista de Precios sugeridos
 */
public class OnClickListenerSuggestedPrice implements AdapterView.OnItemClickListener,Serializable {
    private Coordinator coordinator;

    public OnClickListenerSuggestedPrice(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Price price = (Price) adapterView.getAdapter().getItem(i);
        if(price.getId() == -1){
            coordinator.viewPrice();
        }else {
            coordinator.savePrice(price);
        }
    }
}