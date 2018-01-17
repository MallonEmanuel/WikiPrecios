package unpsjb.wikiprecios.view;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import unpsjb.wikiprecios.R;
import unpsjb.wikiprecios.controller.CameraPreview;
import unpsjb.wikiprecios.view.util.Message;

/**
 * En este fragmento se obtiene el barcode(codigo de barras) de un producto.
 */
public class BarcodeScannerFragment extends MyFragment {
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

    private String barcode;

    private Coordinator coordinator;

    static {
        System.loadLibrary("iconv");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        coordinator = (Coordinator) getActivity();
        View view = inflater.inflate(R.layout.barcode_scanner_view, container, false);
        initControls(view);

        return view;
    }

    private void initControls(View view) {
        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        // Instance barcode scanner
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(getContext(), mCamera, previewCb,
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
                if(!barcodeScanned){
                    Message.show(getContext(),getContext().getString(R.string.msg_scan_a_product));
                    return;
                }
                coordinator.findNearbyCommerces(barcode);
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

                    scanText.setText(getContext().getString(R.string.title_barcode) + barcode);
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

}