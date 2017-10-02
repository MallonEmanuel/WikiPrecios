package unpsjb.wikiprecios.model;

import unpsjb.wikiprecios.R;

/**
 * Created by emanuel on 29/09/17.
 * Esta clase permite recibir la información de los distintos comercios.
 */
public class Commerce implements Listable {
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

    public String toString() {
        return name + ", " + address ;
    }


}
