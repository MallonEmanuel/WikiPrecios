package unpsjb.wikiprecios.http;

import android.content.Context;

import unpsjb.wikiprecios.view.Coordinator;

/**
 * Created by emanuel on 05/10/17.
 */
public class NearbyCommerceHttpClient extends CommerceHttpClient {

    public NearbyCommerceHttpClient(Coordinator coordinator, Context context) {
        super(coordinator, context);
    }

    @Override
    public void onSuccess(Object commerces) {
        coordinator.hideDialog();
        coordinator.viewNearbyCommerces(commerces);
    }
}
