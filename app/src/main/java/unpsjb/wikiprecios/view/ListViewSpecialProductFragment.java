package unpsjb.wikiprecios.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import unpsjb.wikiprecios.controller.SpecialProductParser;
import unpsjb.wikiprecios.view.util.OnClickListenerSpecialProduct;

/**
 * Created by emanuel on 06/10/17.
 */
public class ListViewSpecialProductFragment extends ListViewFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        parser = new SpecialProductParser();
        onItemClickListener = new OnClickListenerSpecialProduct((Coordinator) getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
