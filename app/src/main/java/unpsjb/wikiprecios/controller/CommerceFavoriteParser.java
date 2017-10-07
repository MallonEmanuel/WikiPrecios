package unpsjb.wikiprecios.controller;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by emanuel on 06/10/17.
 */
public class CommerceFavoriteParser extends CommerceParser {
    public CommerceFavoriteParser() {
    }

    @Override
    public Object parse(JSONObject object) throws JSONException {
        int favourite = object.getInt("favorite");
        if(favourite == 0){
            return null;
        }
        return super.parse(object);
    }

}