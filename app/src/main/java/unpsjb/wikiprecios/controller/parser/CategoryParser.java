package unpsjb.wikiprecios.controller.parser;

import org.json.JSONException;
import org.json.JSONObject;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.model.Category;

/**
 * Se encarga de parsear una Categoria de JSONObject a Entidad.
 */
public class CategoryParser implements Parselable {

    public CategoryParser() {
    }

    @Override
    public Object parse(JSONObject object) throws JSONException {
        Category category ;
        int id = object.getInt("id");
        String letter = object.getString("letter");
        String name = object.getString("name");
        category = new Category(id,letter,name);

        switch (name.toLowerCase()) {
            case "carnes":
                category.setImg(R.drawable.ic_filete);
                break;
            case "verduras":
                category.setImg(R.drawable.ic_brocoli);
                break;
            case "panaderia":
                category.setImg(R.drawable.ic_baguette);
                break;
            case "frutas":
                category.setImg(R.drawable.ic_manzana);
                break;
            default:
                category.setImg(R.drawable.ic_space);
                break;
        }
        return category;
    }
}
