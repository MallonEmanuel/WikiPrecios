package unpsjb.wikiprecios.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.config.Routes;
import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.controller.CommerceParser;
import unpsjb.wikiprecios.controller.HttpHandler;
import unpsjb.wikiprecios.controller.HttpResponseHandler;
import unpsjb.wikiprecios.controller.JsonParser;
import unpsjb.wikiprecios.model.Commerce;

/**
 * Created by emanuel on 29/09/17.
 * Este fragmento permite mostrar los comercios cercanos a un usuario, para que pueda
 * el producto quede registrado en dicho comercio, luego solicita los precios sugeridos
 * (se entiende por precio sugerido, a los posibles precios que toma el producto en el comercio)
 * del producto y se muestran. Si el producto no existe en el sistema, se le solicita al usuario que
 * ingrese un nuevo precio.
 */
public class ListViewCommerceFragment extends MyFragment implements HttpResponseHandler {

    ListView listView;
    ArrayAdapter adapter;

    private Context context;
    private Object data;
    private Coordinator coordinator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.commerce_list_view, container, false);
        context = view.getContext();

        JSONArray json = null;
        List lst = new ArrayList();
        // intenta leer la lista de comercios recibidos, del activity padre.
        try {
            json = new JSONArray(data.toString());
            lst = JsonParser.getList(new CommerceParser(), json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Instancia del ListView
        listView = (ListView) view.findViewById(R.id.list_commerce);

        //Inicializar el adapter con la fuente de datos
        adapter = new ItemArrayAdapter(context, lst);

        //Relacionando la list con el adapter
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Commerce commerce = (Commerce) adapter.getItem(position);
                message(commerce.toString());
                sendRequest(commerce);
            }

            
        });
        return view;
    }

    private void sendRequest(Commerce commerce) {
        String base_url = AppPreference.getPrefBaseUrl(context);
        HttpHandler http = new HttpHandler(base_url + Routes.URL_SUGGESTED_PRICE, HttpHandler.GET_REQUEST);
        coordinator.getQuery().setCommerce(commerce);
        http.addParams("commerce", String.valueOf(commerce.getId()));
        http.addParams("product", coordinator.getQuery().getBarcode());
        http.setListener(this);
        http.sendRequest();

    }

    private void message(String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public void onSuccess(Object data) {
        coordinator.viewPrice();
        message(data.toString());
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }
}