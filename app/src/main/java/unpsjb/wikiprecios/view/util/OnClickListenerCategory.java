package unpsjb.wikiprecios.view.util;

import android.view.View;
import android.widget.AdapterView;

import unpsjb.wikiprecios.model.Category;
import unpsjb.wikiprecios.view.Coordinator;

/**
 * Created by emanuel on 05/10/17.
 */
public class OnClickListenerCategory implements AdapterView.OnItemClickListener  {

    private Coordinator coordinator;

    public OnClickListenerCategory(Coordinator coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Category category = (Category) adapterView.getAdapter().getItem(i);//obtiene el elemento selccionado y se lo asigna a categoria
        coordinator.findSpecialProducts(category);
    }

}
