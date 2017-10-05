package unpsjb.wikiprecios.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.controller.JsonParser;
import unpsjb.wikiprecios.controller.Parseable;
import unpsjb.wikiprecios.view.util.ListViewController;
import unpsjb.wikiprecios.view.util.Message;


public class ListViewFragment extends MyFragment {

    private Object data;
    private String title;
    private AdapterView.OnItemClickListener onItemClickListener;
    private Parseable parser;
    private ListViewController listViewController;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view, container, false);

        TextView title_list = (TextView) view.findViewById(R.id.title_list);
        title_list.setText(title);

        List lst = getList();

        //Instancia del ListView
        ListView listView = (ListView) view.findViewById(R.id.list);
        listViewController = new ListViewController(getContext(),listView,lst);
        listViewController.getListView().setOnItemClickListener(onItemClickListener);

        return view;
    }

    private List getList(){
        JSONArray json;
        List lst = null;
        try {
            json = JsonParser.parseToJSONArray(data);
            lst = JsonParser.getList(parser, json);
        } catch (JSONException e) {
            e.printStackTrace();
            Message.show(getContext(),e.getStackTrace().toString());
        }
        return lst;
    }

    public void setData(Object data) {
        this.data= data;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setParser(Parseable parser) {
        this.parser = parser;
    }
}