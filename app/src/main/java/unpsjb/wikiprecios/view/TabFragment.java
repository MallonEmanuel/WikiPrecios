package unpsjb.wikiprecios.view;

import android.os.Bundle;

import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import unpsjb.wikiprecios.R;


/**
 * Created by emanuel on 02/10/17.
 * Este fragmento permite mostrar los comercios y seleccionar los preferibles por el usuario.
 */
public class TabFragment extends MyFragment {
    private static final String TAG = TabFragment.class.getSimpleName();
    private static final String LIST1_TAB_TAG = "Todos";
    private static final String LIST2_TAB_TAG = "Favoritos";

    FragmentTabHost mTabHost;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_view, container, false);

        mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator(LIST1_TAB_TAG),
                ListViewCommerceFragment.class, getArguments());

        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator(LIST2_TAB_TAG),
                ListViewCommerceFavoriteFragment.class, getArguments() );

        return view;
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        listViewCommerceFragment.getListViewController().getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int i, long l) {
//
//                AlertDialog.Builder showPlace = new AlertDialog.Builder(
//                        getActivity());
//                showPlace.setMessage(context.getString(R.string.msg_add_to_favourites));
//                showPlace.setPositiveButton(context.getString(R.string.btn_add), new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Commerce commerce = (Commerce) adapterView.getAdapter().getItem(i);
//                        ArrayAdapter adapterCommerce = listViewCommerceFragment.getListViewController().getAdapter();
//                        ArrayAdapter adapterFavorite = listViewCommerceFavoriteFragment.getListViewController().getAdapter();
//                        int p = adapterCommerce.getPosition(commerce);
//                        if (p == -1) {
//                            commerce.setFavourite(true);
//                            adapterCommerce.notifyDataSetChanged();
//                            adapterFavorite.add(commerce);
//                        }
//                    }
//                });
//                showPlace.setNegativeButton(context.getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//                showPlace.show();
//                return false;
//            }
//        });
//
//    }

}
