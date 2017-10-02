package unpsjb.wikiprecios.view;

/**
 * Created by Ema on 03/10/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import unpsjb.wikiprecios.model.Listable;
import unpsjb.wikiprecios.R;


/**
 * Esta clase se ocupa de adaptar una lista de Listable, para brindar una vista amigable para el usuario.
 *
 */
public class ItemArrayAdapter extends ArrayAdapter<Listable> {
    private List items;

    public ItemArrayAdapter(Context context, List<Listable> objects) {
        super(context, 0, objects);
        items = new ArrayList<Listable>(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Obteniendo una instancia del inflater
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Salvando la referencia del View de la fila
        View listItemView = convertView;

        //Comprobando si el View no existe
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            listItemView = inflater.inflate(
                    R.layout.list_item, parent, false);
        }

        //Obteniendo instancias de los elementos
        TextView title = (TextView) listItemView.findViewById(R.id.text1);
        TextView subtitle = (TextView) listItemView.findViewById(R.id.text2);
        ImageView category = (ImageView) listItemView.findViewById(R.id.category);

        ImageView img = (ImageView) listItemView.findViewById(R.id.favourite);

        //Obteniendo instancia de la Commerce en la posición actual
        Listable item = getItem(position);

        title.setText(item.getTitle());
        subtitle.setText(item.getSubtitle());
        category.setImageResource(item.getImg());

        if (item.isFavourite()) {
            img.setImageResource(R.drawable.ic_star);
        } else {
            img.setImageResource(R.drawable.ic_space);
        }

        //Devolver al ListView la fila creada
        return listItemView;
    }
}