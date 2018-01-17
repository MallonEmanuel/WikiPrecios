package unpsjb.wikiprecios.model;

import android.os.Parcel;
import android.os.Parcelable;

import unpsjb.wikiprecios.R;

/**
 *
 */
public class Category implements Listable,Parcelable{

    private int id;
    private String initials;
    private String name;
    private int img;

    public Category(int id, String letra, String nombre) {
        this.id = id;
        this.initials = letra;
        this.name = nombre;
        this.img =  R.drawable.ic_space;
    }

    protected Category(Parcel in) {
        id = in.readInt();
        initials = in.readString();
        name = in.readString();
        img = in.readInt();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public String getSubtitle() {
        return "";
    }

    @Override
    public int getImg() {
        return img;
    }

    @Override
    public boolean isFavourite() {
        return false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(initials);
        parcel.writeString(name);
        parcel.writeInt(img);
    }
}
