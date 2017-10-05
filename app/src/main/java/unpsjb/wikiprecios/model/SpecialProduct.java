package unpsjb.wikiprecios.model;

import unpsjb.wikiprecios.R;

/**
 * Created by emanuel on 02/10/17.
 */
public class SpecialProduct implements Listable {
    private static final String TAG = SpecialProduct.class.getSimpleName();

    private int id;
    private int idCategory;
    private String name;
    private String code;
    private String unit;
    private int category;

    public SpecialProduct(int id, int idCategory, String name, String code, String unit) {
        this.id = id;
        this.idCategory = idCategory;
        this.name = name;
        this.code = code;
        this.unit = unit;
        category = R.drawable.ic_space;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSubtitle() {
        return code;
    }

    @Override
    public int getImg() {
        return category;
    }

    @Override
    public boolean isFavourite() {
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
