package unpsjb.wikiprecios.controller.parser;

import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emanuel on 29/09/17.
 * permite pasrsear una lista de objetos Json
 */
public class JsonParser {

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
