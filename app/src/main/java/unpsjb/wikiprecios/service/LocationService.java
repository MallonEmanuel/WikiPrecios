package unpsjb.wikiprecios.service;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by ema on 16/09/2015.
 * Esta clase permite obtener la ubicacion del usuario al momento de realizar la
 * consulta de un precio de un producto.
 * Esta clase obtiene la localizacion del usuario, pero se encuentra en estado de
 * desarrollo, debido a que suele no ser exacta...
 */
public class LocationService implements LocationListener {
    private static final String TAG = LocationService.class.getSimpleName();

    private Context context;

    private LocationManager locationManager;
    private LocationComparator locationComparator;


    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private boolean canGetLocation;
    private long MIN_TIME_BW_UPDATES = 60000;
    private float MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    private Location gpsLocation ;
    private Location networkLocation ;

    //@TODO Para refactorizar
    //@TODO Agregar manejo de excepciones
    private static LocationService instance;

    public static LocationService getInstance(Context context){
        if( instance == null){
            instance = new LocationService(context);
        }
        return instance;
    }

    @SuppressWarnings("MissingPermission")
    public LocationService(Context context) {
        this.locationComparator = new LocationComparator();
        this.context = context;

        try {
            locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                canGetLocation = false;
            } else {
                canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * GetLocation v2 : esta implementacion obtiene la ubicacion del usuario pero a diferencia de la v1
     * permite obtener la ubicacion a travez del servicio de internet.
     * @return la localizacion del usuario
     */

    public Location getLocation() {

        if (gpsLocation != null && networkLocation != null) {
            return locationComparator.getBetterLocation(gpsLocation, networkLocation);
        }
        if (gpsLocation != null) {
            return gpsLocation;
        }
        if (networkLocation != null) {
            return networkLocation;
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e(TAG,"onLocationChanged " + location.getProvider());
        if(location.getProvider() == LocationManager.NETWORK_PROVIDER ){
            networkLocation = location;
        }else {
            gpsLocation = location;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.e(TAG,"OnStatusChanged "+provider+ " "+status+ " " );
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.e(TAG,provider + " enable");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.e(TAG,provider+ "disable");
    }

    public boolean isCanGetLocation() {
        return canGetLocation;
    }
}