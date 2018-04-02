package unpsjb.wikiprecios.controller.parser;

import org.json.JSONException;
import org.json.JSONObject;

import unpsjb.wikiprecios.model.Price;

/**
 * Se encarga de parsear un Precio de JSONObject a Entidad.
 */
public class PriceParser implements Parselable {

    public PriceParser() {
    }

    @Override
    public Object parse(JSONObject object) throws JSONException {
        int id = object.getInt("id");
        String price = object.getString("price");
        String name = object.getString("name");
        String distance = object.getString("distance");
        String address = object.getString("address");
        boolean favourite = object.getBoolean("favorite");
        Double price1 = object.getDouble("price_1");
        Double price2 = object.getDouble("price_2");
        return new Price(id,price,name,distance,address,favourite,price1,price2);
    }
}
