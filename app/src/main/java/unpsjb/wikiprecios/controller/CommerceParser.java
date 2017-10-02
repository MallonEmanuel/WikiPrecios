package unpsjb.wikiprecios.controller;

import org.json.JSONException;
import org.json.JSONObject;

import unpsjb.wikiprecios.model.Commerce;

/**
 * Created by emanuel on 29/09/17.
 */
public class CommerceParser implements Parseable {

    public CommerceParser() {
    }

    @Override
    public Object parse(JSONObject object) throws JSONException {

        int id = object.getInt("id");
        String name = object.getString("name");
        String address = object.getString("address");
        Double latitude = object.getDouble("latitude");
        Double longitude = object.getDouble("longitude");
        String city = object.getString("city");
        String province = object.getString("province");
        String country = object.getString("country");
        Double distance = object.getDouble("distance");


        int favourite = object.getInt("favorite");
        boolean f;
        if(favourite == 1)f = true; else f = false;

        return new Commerce(id,name,address,latitude,longitude,city,province,country,distance,f);
    }
}
