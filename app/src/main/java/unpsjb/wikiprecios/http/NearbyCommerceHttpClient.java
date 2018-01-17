package unpsjb.wikiprecios.http;

import android.content.Context;

import unpsjb.wikiprecios.view.Coordinator;

/**
 * Esta clase se encarga de buscar los comercios cercanos a un usuario
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
