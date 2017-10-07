package unpsjb.wikiprecios.controller;

import android.content.Context;

import unpsjb.wikiprecios.view.Coordinator;

/**
 * Created by emanuel on 05/10/17.
 */
public class NearbyCommerceFinder extends CommerceFinder {

    public NearbyCommerceFinder(Coordinator coordinator, Context context) {
        super(coordinator, context);
    }

    @Override
    public void onSuccess(Object commerces) {
        coordinator.viewNearbyCommerces(commerces);
    }
}
