package unpsjb.wikiprecios.controller.parser;

import org.json.JSONException;
import org.json.JSONObject;

import unpsjb.wikiprecios.model.Price;
import unpsjb.wikiprecios.model.User;

/**
 * Se encarga de parsear un usuario de JSONObject a Entidad.
 */
public class UserParser implements Parselable {

    public UserParser() {
    }

    @Override
    public Object parse(JSONObject object) throws JSONException {
        int id = object.getInt("id");
        String name = object.getString("name");
        String surname = object.getString("surname");
        String mail = object.getString("mail");
        String password = object.getString("password");
        int qualification = object.getInt("qualification");
        int accumulated = object.getInt("accumulated");
        String facebookId = object.getString("facebook_id");
        int activeAccount = object.getInt("active_account");
        return new User(id,name,surname,mail,password,qualification,accumulated,facebookId,activeAccount);
    }
}
