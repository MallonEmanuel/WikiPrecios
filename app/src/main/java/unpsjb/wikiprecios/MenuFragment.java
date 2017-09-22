package unpsjb.wikiprecios;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by emanuel on 20/09/17.
 */
public class MenuFragment extends Fragment implements HttpResponseHandler {

    private Button btnUser;
    private Button btnCamera;
    private Button btnSpecialProduct;
    private Button btnFavourite;
    private Button btnLogout;
    private Button btnExit;
    private OnClickBtnListener coordinator;

    private Context context;
    private SessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu_view, container, false);
        context = view.getContext();

        btnUser = (Button) view.findViewById(R.id.btn_user_menu);
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

        return view;
    }

    public void setCoordinator(OnClickBtnListener coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public void onSuccess(Object data) {

    }
}
