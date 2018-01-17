package unpsjb.wikiprecios.view.listview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import unpsjb.wikiprecios.controller.parser.SpecialProductParser;
import unpsjb.wikiprecios.view.Coordinator;
import unpsjb.wikiprecios.view.events.OnClickListenerSpecialProduct;

/**
 * Esta clase permite ver la lista de productos especiales.
 */
public class ListViewSpecialProductFragment extends ListViewFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        onItemClickListener = new OnClickListenerSpecialProduct((Coordinator) getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
