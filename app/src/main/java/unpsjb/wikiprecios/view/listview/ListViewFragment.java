package unpsjb.wikiprecios.view.listview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.controller.Filter;
import unpsjb.wikiprecios.model.Listable;
import unpsjb.wikiprecios.view.MyFragment;
import unpsjb.wikiprecios.view.util.ListViewController;

/**
 * Esta clase permite mostrar en pantalla una lista de Listable. Listable es una interface, que
 * solicita a las clases que la implementen algunos parametos para ser mostrados.
 */
public class ListViewFragment extends MyFragment {

    String title = "title";
    AdapterView.OnItemClickListener onItemClickListener;
    AdapterView.OnItemLongClickListener onItemLongClickListener;
    protected ListViewController listViewController;
    protected List list;
    Filter filter;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view, container, false);

        list = new ArrayList<Listable>();

        if(getArguments() != null || !getArguments().isEmpty()){
            title = getArguments().getString("title");
            list = getArguments().getParcelableArrayList("list");
        }

        TextView title_list = (TextView) view.findViewById(R.id.title_list);
        title_list.setText(title);

        if (filter != null){
            list = filter.filter(list);
        }
            // TODO sacar eventos a clase hija
            //Instancia del ListView
            ListView listView = (ListView) view.findViewById(R.id.list);
            listViewController = new ListViewController(getContext(),listView,list);
            if(onItemClickListener != null){
                listViewController.getListView().setOnItemClickListener(onItemClickListener);
            }
            if(onItemLongClickListener!= null){
                listViewController.getListView().setOnItemLongClickListener(onItemLongClickListener);
            }

        return view;
    }

    public ListViewController getListViewController() {
        return listViewController;
    }
}