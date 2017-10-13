package unpsjb.wikiprecios.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by emanuel on 29/09/17.
 * Esta clase permite guardar la información pertinente para realizar la consulta del
 * precio de un producto.
 */
public class Query implements Parcelable{
    private static String TAG = Query.class.getSimpleName();
    private int id;
    private String barcode;
    private Location location;
    private Double price;
    private Commerce commerce;

    public Query(String barcode, Location location) {
        this.barcode = barcode;
        this.location = location;
    }

    public Query() {

    }

    protected Query(Parcel in) {
        id = in.readInt();
        barcode = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        commerce = in.readParcelable(Commerce.class.getClassLoader());
    }

    public static final Creator<Query> CREATOR = new Creator<Query>() {
        @Override
        public Query createFromParcel(Parcel in) {
            return new Query(in);
        }

        @Override
        public Query[] newArray(int size) {
            return new Query[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Commerce getCommerce() {
        return commerce;
    }

    public void setCommerce(Commerce commerce) {
        this.commerce = commerce;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(barcode);
        parcel.writeParcelable(location, i);
        parcel.writeParcelable(commerce, i);
    }
}
