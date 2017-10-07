package unpsjb.wikiprecios.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.controller.JsonParser;
import unpsjb.wikiprecios.controller.Parseable;
import unpsjb.wikiprecios.model.Listable;
import unpsjb.wikiprecios.view.util.ListViewController;
import unpsjb.wikiprecios.view.util.Message;


public class ListViewFragment extends MyFragment {

    String data;
    String title = "title";
    AdapterView.OnItemClickListener onItemClickListener;
    Parseable parser;
    ListViewController listViewController;

    boolean onError;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view, container, false);

        if(!getArguments().isEmpty()){
            data = getArguments().getString("data");
            title = getArguments().getString("title");
        }

        TextView title_list = (TextView) view.findViewById(R.id.title_list);
        title_list.setText(title);

        onError = false;
        List lst = new ArrayList<Listable>();

        if(!data.isEmpty()){
            try {
                lst = JsonParser.getList(parser,data);
            } catch (JSONException e) {
                e.printStackTrace();
                Message.show(getContext(),"Error al cargar Json");
                onError = true;
            }
        }

        if(!onError){
            //Instancia del ListView
            ListView listView = (ListView) view.findViewById(R.id.list);
            listViewController = new ListViewController(getContext(),listView,lst);
            if(onItemClickListener != null){
                listViewController.getListView().setOnItemClickListener(onItemClickListener);
            }
        }
        return view;
    }

    public ListViewController getListViewController() {
        return listViewController;
    }
}