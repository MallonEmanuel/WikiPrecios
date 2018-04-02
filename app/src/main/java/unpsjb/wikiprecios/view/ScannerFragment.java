package unpsjb.wikiprecios.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import unpsjb.wikiprecios.R;

public class ScannerFragment extends MyFragment implements ZBarScannerView.ResultHandler {

    private ZBarScannerView mScannerView;
    private TextView scanText;
    private Button scanButton;
    private Button continueButton;

    private String barcode;
    private boolean barcodeScanned = false;

    private Coordinator coordinator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        coordinator = (Coordinator) getActivity();

        View view = inflater.inflate(R.layout.activity_simple_scanner, container, false);
        ViewGroup contentFrame = (ViewGroup) view.findViewById(R.id.content_frame);

        mScannerView = new ZBarScannerView(getActivity());
        mScannerView.setFormats(Arrays.asList(BarcodeFormat.EAN13,BarcodeFormat.UPCA));
        mScannerView.setAutoFocus(true);
        contentFrame.addView(mScannerView);
        initControls(view);
        return view;
    }

    private void initControls(View view) {
        scanText = (TextView) view.findViewById(R.id.scan_text);
        continueButton = (Button) view.findViewById(R.id.continueButton);
        scanButton = (Button) view.findViewById(R.id.scanButton);

        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (barcodeScanned) {
                    barcodeScanned = false;
                    barcode = "";
                    mScannerView.resumeCameraPreview(ScannerFragment.this);
                    scanText.setText(R.string.title_scanning);
                }
            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!barcodeScanned){
                    Toast.makeText(getContext(),R.string.msg_scan_a_product,Toast.LENGTH_LONG).show();
                }else {
                    coordinator.findNearbyCommerces(barcode);
                }
                //coordinator.findNearbyCommerces(barcode);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        //Toast.makeText(this, "Contents = " + rawResult.getContents() +
          //      ", Format = " + rawResult.getBarcodeFormat().getName(), Toast.LENGTH_SHORT).show();
        barcodeScanned = true;
        barcode = rawResult.getContents();
        scanText.setText(getString(R.string.title_barcode) + barcode);

    }



}