package unpsjb.wikiprecios.controller;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by emanuel on 29/09/17.
 * Permite a quien lo implemente crear un objeto a partir del JsonObject recibido
 */
public interface Parseable {
    Object parse(JSONObject object) throws JSONException;
}
