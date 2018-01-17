package unpsjb.wikiprecios.view.listview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import unpsjb.wikiprecios.view.Coordinator;
import unpsjb.wikiprecios.view.events.OnClickListenerPrice;


/**
 * Esta clase permite ver la lista de precios resultados.
 */
public class ListViewPriceFragment extends ListViewFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        onItemClickListener = new OnClickListenerPrice((Coordinator) getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
