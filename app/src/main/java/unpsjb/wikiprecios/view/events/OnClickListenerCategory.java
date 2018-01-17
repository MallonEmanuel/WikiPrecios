package unpsjb.wikiprecios.view.events;

import android.view.View;
import android.widget.AdapterView;

import java.io.Serializable;

import unpsjb.wikiprecios.model.Category;
import unpsjb.wikiprecios.view.Coordinator;

/**
 * Esta clase se encarga de manejar los eventos que surgen en la lista de Categorias
 */
public class OnClickListenerCategory implements AdapterView.OnItemClickListener ,Serializable {

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
