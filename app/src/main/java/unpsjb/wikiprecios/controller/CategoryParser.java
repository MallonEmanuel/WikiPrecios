package unpsjb.wikiprecios.controller;

import org.json.JSONException;
import org.json.JSONObject;

import unpsjb.wikiprecios.model.Category;

/**
 * Created by emanuel on 02/10/17.
 */
public class CategoryParser implements Parseable {

    public CategoryParser() {
    }

    @Override
    public Object parse(JSONObject object) throws JSONException {
        int id = object.getInt("id");
        String letter = object.getString("letter");
        String name = object.getString("name");
        return new Category(id,letter,name);
    }
}
