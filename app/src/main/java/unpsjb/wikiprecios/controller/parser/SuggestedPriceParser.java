package unpsjb.wikiprecios.controller.parser;

import org.json.JSONException;
import org.json.JSONObject;

import unpsjb.wikiprecios.model.Price;

/**
 * Created by emanuel on 23/12/17.
 */
public class SuggestedPriceParser implements Parselable {
    public SuggestedPriceParser() {
    }

    @Override
    public Object parse(JSONObject object) throws JSONException {
        return new Price(object.getString("price"));
    }
}
