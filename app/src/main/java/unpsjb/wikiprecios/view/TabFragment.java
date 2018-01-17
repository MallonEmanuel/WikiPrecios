package unpsjb.wikiprecios.view;

import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.view.listview.ListViewFavouriteCommerceFragment;
import unpsjb.wikiprecios.view.listview.ListViewCommerceFragment;


/**
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

        Log.e(TAG,getArguments().getParcelableArrayList("list").toString());

        mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator(LIST1_TAB_TAG),
                ListViewCommerceFragment.class, getArguments());

        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator(LIST2_TAB_TAG),
                ListViewFavouriteCommerceFragment.class, getArguments() );

        return view;
    }

    @Override
    public void onResume() {
        TabHost tabhost = mTabHost;
        for(int i=0;i<tabhost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabhost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.WHITE);
        }
        super.onResume();
    }
}
