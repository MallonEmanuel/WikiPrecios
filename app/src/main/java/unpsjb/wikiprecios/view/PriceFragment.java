package unpsjb.wikiprecios.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.config.Routes;
import unpsjb.wikiprecios.http.HttpHandler;
import unpsjb.wikiprecios.http.HttpResponseHandler;
import unpsjb.wikiprecios.controller.SessionManager;
import unpsjb.wikiprecios.model.Query;
import unpsjb.wikiprecios.view.util.Message;

/**
 * Este fragmento permite al usuario ingresar manualmente el precio del producto,
 * este fragmento se mostrara si el producto no existe en el sistema, o cuando
 * el usuario quiera introducir el precio manualmente.
 */
public class PriceFragment extends MyFragment{
    private static final String TAG = PriceFragment.class.getSimpleName();

    private Context context;
    private EditText inputPrice;
    private TextView titleQuery;

    private Coordinator coordinator;
    private Query query;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.price_view, container, false);
        context = view.getContext();
        coordinator = (Coordinator) getActivity();

        query = getArguments().getParcelable("query");

        inputPrice = (EditText) view.findViewById(R.id.price);
        titleQuery = (TextView) view.findViewById(R.id.title_query_price);
        inputPrice.requestFocus();
        titleQuery.setText(context.getString(R.string.title_name_commerce) + query.getCommerce().getName()  + "\n" + context.getString(R.string.title_name_barcode) + query.getBarcode());

        inputPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() || actionId == EditorInfo.IME_ACTION_DONE) {
                    next();
                }

                return false;
            }
        });

        return view;
    }

    private void next(){
        if (getPrice() == null) {
            Message.show(context,context.getString(R.string.msg_enter_price));
        } else {
            query.setPrice(getPrice());
            coordinator.savePrice();
        }
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
            p = Double.parseDouble(inputPrice.getText().toString());
        }catch (Exception e){
            return null;
        }
        return p;
    }

    @Override
    public void onResume() {
        inputPrice.setText("");
        super.onResume();
    }
}