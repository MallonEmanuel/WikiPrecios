package unpsjb.wikiprecios.view.listview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import unpsjb.wikiprecios.controller.parser.CommerceParser;
import unpsjb.wikiprecios.view.Coordinator;
import unpsjb.wikiprecios.view.events.OnClickListenerNearbyCommerce;

/**
 * Esta clase permite ver la lista de comercios cercanos.
 */
public class ListViewNearbyCommerceFragment extends ListViewFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        onItemClickListener = new OnClickListenerNearbyCommerce((Coordinator) getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}