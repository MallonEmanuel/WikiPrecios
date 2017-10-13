package unpsjb.wikiprecios.view.listview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import unpsjb.wikiprecios.view.Coordinator;
import unpsjb.wikiprecios.view.events.OnItemLongClickListenerCommerce;

/**
 * Created by emanuel on 06/10/17.
 */
public class ListViewCommerceFragment extends ListViewFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        onItemLongClickListener = new OnItemLongClickListenerCommerce((Coordinator) getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
