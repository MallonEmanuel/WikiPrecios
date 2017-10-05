package unpsjb.wikiprecios.view.util;

import android.view.View;
import android.widget.AdapterView;

import unpsjb.wikiprecios.model.SpecialProduct;
import unpsjb.wikiprecios.view.Coordinator;

/**
 * Created by emanuel on 05/10/17.
 */
public class OnClickListenerSpecialProduct implements AdapterView.OnItemClickListener {
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
