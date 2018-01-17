package unpsjb.wikiprecios.controller.parser;

import org.json.JSONException;
import org.json.JSONObject;

import unpsjb.wikiprecios.model.Price;

/**
 * Se encarga de parsear un Precio Sugerido de JSONObject a Entidad.
 */
public class SuggestedPriceParser implements Parselable {
    public SuggestedPriceParser() {
    }

    @Override
    public Object parse(JSONObject object) throws JSONException {
        return new Price(object.getString("price"));
    }
}
