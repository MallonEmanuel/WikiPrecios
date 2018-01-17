package unpsjb.wikiprecios.view.events;

import android.view.View;
import android.widget.AdapterView;

import java.io.Serializable;

import unpsjb.wikiprecios.model.SpecialProduct;
import unpsjb.wikiprecios.view.Coordinator;

/**
 * Esta clase se encarga de manejar los eventos que surgen en la lista de Productos especiales
 */
public class OnClickListenerSpecialProduct implements AdapterView.OnItemClickListener,Serializable {
    private Coordinator coordinator;

    public OnClickListenerSpecialProduct(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SpecialProduct specialProduct = (SpecialProduct) adapterView.getAdapter().getItem(i);
        coordinator.findNearbyCommerces(specialProduct.getCode());
    }
}
