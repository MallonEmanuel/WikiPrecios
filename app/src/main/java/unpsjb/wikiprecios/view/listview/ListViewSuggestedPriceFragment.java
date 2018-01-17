package unpsjb.wikiprecios.view.listview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import unpsjb.wikiprecios.view.Coordinator;
import unpsjb.wikiprecios.view.events.OnClickListenerNearbyCommerce;
import unpsjb.wikiprecios.view.events.OnClickListenerSuggestedPrice;

/**
 * Esta clase permite ver la lista de precios sugeridos.
 */
public class ListViewSuggestedPriceFragment extends ListViewFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        onItemClickListener = new OnClickListenerSuggestedPrice((Coordinator) getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
