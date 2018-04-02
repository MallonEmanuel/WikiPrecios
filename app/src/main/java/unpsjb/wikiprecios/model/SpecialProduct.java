package unpsjb.wikiprecios.model;

import android.os.Parcel;
import android.os.Parcelable;

import unpsjb.wikiprecios.R;

/**
 * Esta clase permite guardar la informacion de un producto especial.
 */
public class SpecialProduct implements Listable,Parcelable {
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

    protected SpecialProduct(Parcel in) {
        id = in.readInt();
        idCategory = in.readInt();
        name = in.readString();
        code = in.readString();
        unit = in.readString();
        category = in.readInt();
    }

    public static final Creator<SpecialProduct> CREATOR = new Creator<SpecialProduct>() {
        @Override
        public SpecialProduct createFromParcel(Parcel in) {
            return new SpecialProduct(in);
        }

        @Override
        public SpecialProduct[] newArray(int size) {
            return new SpecialProduct[size];
        }
    };

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
        return code +" ( "+ unit+" )";
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(idCategory);
        parcel.writeString(name);
        parcel.writeString(code);
        parcel.writeString(unit);
        parcel.writeInt(category);
    }
}
