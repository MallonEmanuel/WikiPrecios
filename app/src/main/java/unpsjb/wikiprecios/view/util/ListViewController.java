package unpsjb.wikiprecios.view.util;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by emanuel on 04/10/17.
 */
public class ListViewController {

    private ListView listView;
    private ArrayAdapter adapter;

    public ListViewController(final Context context, ListView listView, List list) {
        this.listView = listView;
        this.adapter = new ItemArrayAdapter(context, list);
        this.listView.setAdapter(adapter);
    }

    public ListView getListView() {
        return listView;
    }


    public ArrayAdapter getAdapter() {
        return adapter;
    }
}
