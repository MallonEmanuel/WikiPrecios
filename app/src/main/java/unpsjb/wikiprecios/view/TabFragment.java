package unpsjb.wikiprecios.view;

import android.content.Context;
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

    private Context context;

    FragmentTabHost mTabHost;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_view, container, false);
        context = view.getContext();

        mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator(LIST1_TAB_TAG),
                RegisterFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator(LIST2_TAB_TAG),
                LoginFragment.class, null);

        mTabHost.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTabHost.setCurrentTabByTag("second");
            }
        }, 5000);

        return view;
    }
}
