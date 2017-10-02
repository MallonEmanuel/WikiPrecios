package unpsjb.wikiprecios.view;

import android.content.Context;
import android.hardware.Camera;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import unpsjb.wikiprecios.controller.CameraPreview;
import unpsjb.wikiprecios.controller.HttpHandler;
import unpsjb.wikiprecios.controller.HttpResponseHandler;
import unpsjb.wikiprecios.controller.LocationService;
import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.config.Routes;
import unpsjb.wikiprecios.controller.SessionManager;
import unpsjb.wikiprecios.config.AppPreference;
import unpsjb.wikiprecios.model.Query;

/**
 * Created by emanuel on 23/09/17.
 * En este fragmento se obtiene el barcode(codigo de barras) de un producto, la localización
 * del usuario, y se realiza una petición para obtener los comercios mas cercanos al usuario
 * para mostrar en el proximo fragmento.
 */
public class BarcodeScannerFragment extends MyFragment implements HttpResponseHandler {
    private static final String TAG = BarcodeScannerFragment.class.getSimpleName();

    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;

    private TextView scanText;
    private Button scanButton;
    private Button continueButton;
    private ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;

    private Context context;

    private String barcode;
    private LocationService locationService;

    private Coordinator coordinator;

    static {
        System.loadLibrary("iconv");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.barcode_scanner_view, container, false);
        context = view.getContext();
        initControls(view);
        locationService = new LocationService(context);

        return view;
    }

    private void initControls(View view) {
        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        // Instance barcode scanner
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(context, mCamera, previewCb,
                autoFocusCB);
        FrameLayout preview = (FrameLayout) view.findViewById(R.id.cameraPreview);
        preview.addView(mPreview);

        scanText = (TextView) view.findViewById(R.id.scan_text);
        continueButton = (Button) view.findViewById(R.id.continueButton);
        scanButton = (Button) view.findViewById(R.id.scanButton);

        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (barcodeScanned) {
                    barcodeScanned = false;
                    mCamera.setPreviewCallback(previewCb);
                    mCamera.startPreview();
                    previewing = true;
                    mCamera.autoFocus(autoFocusCB);
                    scanText.setText(R.string.title_scanning);
                }
            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!locationService.isCanGetLocation()){
                    message(context.getString(R.string.msg_services_disable));
                    return;
                }
                if(!barcodeScanned){
                    message(context.getString(R.string.msg_scan_a_product));
                    return;
                }
                sendRequest();
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        releaseCamera();
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
        }
        return c;
    }

    public void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };

    Camera.PreviewCallback previewCb = new Camera.PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();

            Image barcodeImg = new Image(size.width, size.height, "Y800");
            barcodeImg.setData(data);

            int result = scanner.scanImage(barcodeImg);

            if (result != 0) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();

                SymbolSet syms = scanner.getResults();
                for (Symbol sym : syms) {

                    Log.i("<<<<<<Asset Code>>>>> ",
                            "<<<<Bar Code>>> " + sym.getData());
                    barcode = sym.getData().trim();

                    scanText.setText(context.getString(R.string.title_barcode) + barcode);
                    barcodeScanned = true;
                    break;
                }
            }
        }
    };

    // Mimic continuous auto-focusing
    Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };

    /**
     * Se ocupa de realizar una consulta, con la ubicacion actual para recibir los comercios mas
     * cercanos al usuario
     */
    public void sendRequest() {
        String base_url = AppPreference.getPrefBaseUrl(context);
        HttpHandler http = new HttpHandler(base_url + Routes.URL_NEARBY_COMMERCES,HttpHandler.GET_REQUEST);
        Location location = locationService.getLocation();

        coordinator.setQuery(new Query(barcode,location));

        http.addParams("latitud", String.valueOf(location.getLatitude()));
        http.addParams("longitud", String.valueOf(location.getLongitude()));
        http.addParams("usuario", SessionManager.getInstance(context).getUserLoged());
        http.setListener(this);
        http.sendRequest();
    }


    private void message(String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(Object commerces) {
        coordinator.viewNearbyCommerces(commerces);
    }

    public void setCoordinator(Coordinator coordinator) {
        this.coordinator = coordinator;
    }
}