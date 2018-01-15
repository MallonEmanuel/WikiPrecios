package unpsjb.wikiprecios.model;

import android.os.Parcel;
import android.os.Parcelable;

import unpsjb.wikiprecios.R;

/**
 * Created by Ema on 06/10/2015.
 * Esta clase representa al Precio de un producto
 * Implementa parceable para poder ser compartido entre activies
 */
public class Price implements Parcelable, Listable {
    private static final String TAG = Price.class.getSimpleName();
    private int id;
    private String price;
    private String commerce;
    private String distance;
    private String address;

    private int category;
    private boolean favourite;

    public Price(int id, String price, String commerce) {
        this.id = id;
        this.price = price;
        this.commerce = commerce;
        this.category = R.drawable.ic_price2;
        this.favourite = false;
    }
    public Price(String price) {
        this.price = price;
        this.category = R.drawable.ic_price2;
        this.favourite = false;
    }

    public Price(int category, String commerce, String price, int id) {
        this.category = category;
        this.commerce = commerce;
        this.price = price;
        this.id = id;
    }

    public Price(int id, String price, String commerce, String distance, String address, boolean favourite) {
        this.id = id;
        this.price = price;
        this.commerce = commerce;
        this.distance = distance;
        this.category = R.drawable.ic_price2;
        this.favourite = favourite;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getCategory() {
        return category;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCommerce() {
        return commerce;
    }

    public void setCommerce(String commerce) {
        this.commerce = commerce;
    }

    @Override
    public String getTitle() {
        return "" + String.valueOf(price);
    }

    @Override
    public String getSubtitle() {
        String s = commerce;

        if(address != null && !address.isEmpty()){
            s+= " - "+ address ;
        }
        if(distance != null && !distance.isEmpty()){
            s+= "\n ("+ distance +" km)";
        }

        return s;
    }

    public int getImg() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.price);
        dest.writeString(this.commerce);
        dest.writeString(this.distance);
        dest.writeString(this.address);
        dest.writeInt(this.category);
        dest.writeByte(favourite ? (byte) 1 : (byte) 0);
    }

    protected Price(Parcel in) {
        this.id = in.readInt();
        this.price = in.readString();
        this.commerce = in.readString();
        this.distance = in.readString();
        this.address = in.readString();
        this.category = in.readInt();
        this.favourite = in.readByte() != 0;
    }

    public static final Creator<Price> CREATOR = new Creator<Price>() {
        public Price createFromParcel(Parcel source) {
            return new Price(source);
        }

        public Price[] newArray(int size) {
            return new Price[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}