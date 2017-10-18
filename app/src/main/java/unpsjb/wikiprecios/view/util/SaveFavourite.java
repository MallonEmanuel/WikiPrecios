package unpsjb.wikiprecios.view.util;

import android.content.Context;

import unpsjb.wikiprecios.view.Coordinator;

/**
 * Created by emanuel on 17/10/17.
 */
public class SaveFavourite extends Question implements DialogListener  {

    public SaveFavourite(Coordinator coordinator, Context context) {
        super(coordinator, context);
    }

    @Override
    public void posiviteResult() {
        coordinator.saveFavourites();
    }

    @Override
    public void negativeResult() {

    }
}
