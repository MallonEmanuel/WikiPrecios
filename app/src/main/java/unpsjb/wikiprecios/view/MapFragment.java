package unpsjb.wikiprecios.view;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.osmdroid.api.IMapController;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.service.LocationService;
import unpsjb.wikiprecios.view.util.Message;

/**
 * Created by emanuel on 20/10/17.
 */
public class MapFragment extends MyFragment {
    private static final String TAG = MapFragment.class.getSimpleName();

    private MapView map;
    private Button btnSaveCommerce;
    private Button btnCancel;
    private EditText inputNameCommerce;
    private GeoPoint newCommerce;

    //your items
    private ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
    private ItemizedOverlayWithFocus<OverlayItem> capa;
    private Coordinator coordinator;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        coordinator = (Coordinator) getActivity();
        View view = inflater.inflate(R.layout.map_view, container, false);
        map = (MapView) view.findViewById(R.id.mapa);
        inputNameCommerce = (EditText) view.findViewById(R.id.input_commerce_name);
        btnSaveCommerce = (Button) view.findViewById(R.id.btn_save_commerce);
        btnCancel = (Button) view.findViewById(R.id.btn_cancel_commerce);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coordinator.back();
            }
        });

        btnSaveCommerce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getName() == null){
                    Message.show(getContext(),"Por favor ingrese un nombre para el comercion");
                }else {
                    Location location = new Location("map");
                    location.setLatitude(newCommerce.getLatitude());
                    location.setLongitude(newCommerce.getLongitude());
                    coordinator.saveCommerce(location, getName());
                }
            }
        });

        prepararMapa(map);


        return view;
    }

    private String getName(){
        if (inputNameCommerce.getText().toString().isEmpty()) {
            return null;
        }
        return inputNameCommerce.getText().toString();
    }


    private void prepararMapa(final MapView map) {
        map.setTileSource(TileSourceFactory.MAPNIK); // por defecto utiliza el almacenamiento externo como cache
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(13);

        GeoPoint ubicacionMapa = new GeoPoint(-42.77000141404137, -65.0339126586914);
        mapController.setCenter(ubicacionMapa);

        LocationService locationService = LocationService.getInstance(getContext());
        Location location = null;
        if(locationService.isCanGetLocation()){
            location = locationService.getLocation();
            Log.e(TAG,"Location ("+ location.getProvider()+" ) "+ location.getLatitude()+" "+ location.getLongitude());
            Message.show(getContext(),location.getProvider());

            GeoPoint miUbicacion = new GeoPoint(location.getLatitude(), location.getLongitude());
            newCommerce = miUbicacion;
            OverlayItem overlayItem = new OverlayItem("Title", "Description", miUbicacion);
            items.add(overlayItem); // Lat/Lon decimal degrees
        }

        capa = new ItemizedOverlayWithFocus<OverlayItem>(
                getActivity(),
                items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        Message.show(getContext(),"Itemized Overlay");
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(int index, OverlayItem item) {
                        return true;
                    }
                });

        map.getOverlays().add(capa);


        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                OverlayItem overlayItem = new OverlayItem("Title", "Description", new GeoPoint(p.getLatitude(),p.getLongitude()));
                newCommerce = p;
                capa.removeAllItems();
                map.invalidate();

                capa.addItem(overlayItem);
                map.invalidate();

                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };

        MapEventsOverlay overlayEvents = new MapEventsOverlay(getContext(), mReceive);
        map.getOverlays().add(overlayEvents);


    }


}
