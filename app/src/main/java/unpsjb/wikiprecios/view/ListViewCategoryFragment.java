package unpsjb.wikiprecios.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import unpsjb.wikiprecios.controller.CategoryParser;
import unpsjb.wikiprecios.view.util.OnClickListenerCategory;

/**
 * Created by emanuel on 06/10/17.
 */
public class ListViewCategoryFragment extends ListViewFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        parser = new CategoryParser();
        onItemClickListener = new OnClickListenerCategory((Coordinator) getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
