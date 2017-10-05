package unpsjb.wikiprecios.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.config.Routes;
import unpsjb.wikiprecios.controller.HttpHandler;
import unpsjb.wikiprecios.controller.HttpResponseHandler;
import unpsjb.wikiprecios.controller.SessionManager;
import unpsjb.wikiprecios.model.Query;

/**
 * Created by emanuel on 29/09/17.
 * Este fragmento permite al usuario ingresar manualmente el precio del producto,
 * este fragmento se mostrara si el producto no existe en el sistema, o cuando
 * el usuario quiera introducir el precio manualmente.
 */
public class PriceFragment extends MyFragment implements HttpResponseHandler {
    private static final String TAG = PriceFragment.class.getSimpleName();

    private Context context;
    private EditText inputPrice;
    private TextView titleQuery;
    private Button continueButton;
    private Button cancelButton;


    private Coordinator coordinator;
    private Query query;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.price_view, container, false);
        context = view.getContext();

        inputPrice = (EditText) view.findViewById(R.id.price);
        titleQuery = (TextView) view.findViewById(R.id.title_query_price);
        inputPrice.requestFocus();
        titleQuery.setText(context.getString(R.string.title_name_commerce) + query.getCommerce().getName()  + "\n" + context.getString(R.string.title_name_barcode) + query.getBarcode());

        continueButton = (Button) view.findViewById(R.id.btn_continue_price);
        cancelButton = (Button) view.findViewById(R.id.btn_cancel_price);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getPrice() == null) {
                    Toast notificacion = Toast.makeText(context,context.getString(R.string.msg_enter_price), Toast.LENGTH_LONG);
                    notificacion.show();
                } else {
                    sendRequest();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coordinator.back();
            }
        });

        return view;
    }

    /**
     * Se ocupa de realizar la consulta, que permite al usuario ver los precios del producto buscado en los demas comercios.
     */
    private void sendRequest() {

        String base_url = AppPreference.getPrefBaseUrl(context);

        HttpHandler http = new HttpHandler(base_url + Routes.URL_SAVE_PRICE, HttpHandler.GET_REQUEST);
        http.addParams("commerce", String.valueOf(query.getCommerce().getId()));
        http.addParams("price", String.valueOf(getPrice()));
        http.addParams("product", String.valueOf(query.getBarcode()));
        http.addParams("user", "'" + SessionManager.getInstance(context).getUserLoged());
        http.setListener(this);
        http.sendRequest();
    }


    /**
     * Obtiene el precio ingresado por el usuario, si es invalido retorna null
     * @return
     */
    private Double getPrice() {
        if (inputPrice.getText().toString().isEmpty()) {
            return null;
        }
        Double p;
        try {
            p=Double.parseDouble(inputPrice.getText().toString());
        }catch (Exception e){
            return null;
        }
        return p;
    }


    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public void onSuccess(Object data) {
        try {
            JSONObject json = (JSONObject) data;
            if(json.has("newProduct")){
                Toast.makeText(context,context.getString(R.string.msg_new_product),Toast.LENGTH_LONG).show();
                coordinator.viewMenu();
            }
        }catch (Exception e){
            Log.e(TAG,e.getStackTrace().toString());
        }
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}