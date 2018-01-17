package unpsjb.wikiprecios.controller.parser;

import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Permite parsear una lista de objetos Json, siempre y cuando los objetos sean de la misma entidad
 */
public class JsonParser {

    /**
     * Este metodo se encarga de parsear la lista de objetos, que vienen en un json a entidades, para poder ser
     * utilizadas por la aplicación.
     * @param p Clase encargada de parsear un JsonObject a la vez
     * @param data El JsonArray recibido en forma de String
     * @return la lista de entidades parseadas
     */
    public static List<Parcelable> parseList(Parselable p , String data ){
        List<Parcelable> list = new ArrayList<Parcelable>();
        try {
            list = JsonParser.getList( p,data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Parcelable> getList(Parselable p, String data) throws JSONException {
        JSONArray json = new JSONArray(data);
        List lst = new ArrayList<>();
        for (int n = 0; n < json.length(); n++) {
            JSONObject object = json.getJSONObject(n);
            Object elem = p.parse(object);

            if(elem != null) {
                lst.add(elem);
            }
        }
        return lst;
    }

}
