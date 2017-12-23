package unpsjb.wikiprecios.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import unpsjb.wikiprecios.R;

/**
 * Created by emanuel on 20/09/17.
 * Este fragmento presenta un menu simple al usuario
 */
public class MenuFragment extends MyFragment {

    private Button btnTest;
    private Button btnCamera;
    private Button btnSpecialProduct;
    private Button btnFavourite;
    private Button btnLogout;
    private Button btnExit;
    private Coordinator coordinator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu_view, container, false);
        coordinator = (Coordinator) getActivity();

        btnTest = (Button) view.findViewById(R.id.btn_test);
        btnCamera = (Button) view.findViewById(R.id.btn_camera_menu);
        btnSpecialProduct = (Button) view.findViewById(R.id.btn_special_product_menu);
        btnFavourite = (Button) view.findViewById(R.id.btn_favourite_menu);
        btnLogout = (Button) view.findViewById(R.id.btn_logout_menu);
        btnExit = (Button) view.findViewById(R.id.btn_exit_menu);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coordinator.closeView();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coordinator.logout();
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coordinator.viewBarcodeScanner();
            }
        });

        btnSpecialProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coordinator.findCategories();
            }
        });

        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coordinator.findCommerces();
            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
coordinator.viewMap();
            }
        });
        return view;
    }

}
