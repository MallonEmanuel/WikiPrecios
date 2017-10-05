package unpsjb.wikiprecios.model;

import android.location.Location;

/**
 * Created by emanuel on 29/09/17.
 * Esta clase permite guardar la información pertinente para realizar la consulta del
 * precio de un producto.
 */
public class Query {
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
}
