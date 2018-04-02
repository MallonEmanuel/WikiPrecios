package unpsjb.wikiprecios.view.listview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.controller.comparator.DistanceComparator;
import unpsjb.wikiprecios.controller.comparator.FavouriteComparator;
import unpsjb.wikiprecios.controller.comparator.PriceComparator;
import unpsjb.wikiprecios.model.Price;
import unpsjb.wikiprecios.view.Coordinator;
import unpsjb.wikiprecios.view.events.OnClickListenerPrice;


/**
 * Esta clase permite ver la lista de precios resultados.
 */
public class ListViewPriceFragment extends ListViewFragment {
    private String TAG = ListViewPriceFragment.class.getSimpleName();

    private MultiStateToggleButton button;
    private int visibility;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        onItemClickListener = new OnClickListenerPrice((Coordinator) getActivity());
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initView(view);
        Log.e(TAG," "+getArguments().getBoolean("empty"));
        if(getArguments().getBoolean("empty")) {
            button.setVisibility(View.GONE);
            visibility = View.GONE;
        }else {
            button.setVisibility(View.VISIBLE);
            visibility = View.VISIBLE;
        }
        return view;
    }

    private void initView(View view) {
        button = (MultiStateToggleButton) view.findViewById(R.id.mstb_multi_id);
        //button.setVisibility(View.VISIBLE);
        button.setValue(2);
        ordenar(list,new DistanceComparator());

        button.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                Log.e(TAG, "Position: " + position);
                switch (position){
                    case 0 : ordenar(list,new PriceComparator());
                             break;
                    case 1 : ordenar(list,new FavouriteComparator());
                             break;
                    default : ordenar(list,new DistanceComparator());
                             break;
                }
            }
        });
    }


    private void ordenar(List list, Comparator comparator) {
        Collections.sort(list, comparator);
        listViewController.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        button.setValue(2);
        button.setVisibility(visibility);
    }

    @Override
    public void onStop() {
        super.onStop();
        button.setVisibility(View.GONE);
    }
}
