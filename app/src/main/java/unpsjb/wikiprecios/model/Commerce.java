package unpsjb.wikiprecios.model;

import android.os.Parcel;
import android.os.Parcelable;

import unpsjb.wikiprecios.R;

/**
 * Created by emanuel on 29/09/17.
 * Esta clase permite recibir la información de los distintos comercios.
 */
public class Commerce implements Listable, Parcelable {
    private static final String TAG = Commerce.class.getSimpleName();

    private int id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String city;
    private String province;
    private String country;

    private Double distance;
    private boolean favourite;
    private int category;

    public Commerce(int id, String name, String address, Double latitude, Double longitude, String city, String province, String country, Double distance, boolean favourite) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.province = province;
        this.country = country;
        this.distance = distance;
        this.favourite = favourite;
        this.category = R.drawable.ic_commerce;
    }

    public Commerce(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.category = R.drawable.ic_commerce;
    }

    public Commerce(int id, String name, String address, int category) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.category = category;
    }

    protected Commerce(Parcel in) {
        id = in.readInt();
        name = in.readString();
        address = in.readString();
        city = in.readString();
        province = in.readString();
        country = in.readString();
        favourite = in.readByte() != 0;
        category = in.readInt();
    }

    public static final Creator<Commerce> CREATOR = new Creator<Commerce>() {
        @Override
        public Commerce createFromParcel(Parcel in) {
            return new Commerce(in);
        }

        @Override
        public Commerce[] newArray(int size) {
            return new Commerce[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
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
        if (distance == null) {
            return address;
        } else {
            return address + "( " + distance + "km )";
        }
    }

    @Override
    public int getImg() {
        return category;
    }

    @Override
    public boolean isFavourite() {
        return favourite;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(city);
        parcel.writeString(province);
        parcel.writeString(country);
        parcel.writeByte((byte) (favourite ? 1 : 0));
        parcel.writeInt(category);
    }

    @Override
    public String toString() {
        return name+" "+favourite;
    }
}
