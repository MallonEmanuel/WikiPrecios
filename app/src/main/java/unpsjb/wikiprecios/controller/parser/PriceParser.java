package unpsjb.wikiprecios.controller.parser;

import org.json.JSONException;
import org.json.JSONObject;

import unpsjb.wikiprecios.model.Price;

/**
 * Created by emanuel on 05/01/18.
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
        return new Price(id,price,name,distance,address,favourite);
    }
}
