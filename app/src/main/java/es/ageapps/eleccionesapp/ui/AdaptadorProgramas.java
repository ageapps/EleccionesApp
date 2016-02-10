package es.ageapps.eleccionesapp.ui;

/**
 * Created by adricacho on 08/11/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import es.ageapps.eleccionesapp.R;

public class AdaptadorProgramas extends ArrayAdapter<String> {
    private final Context context;
    private final String[] nombres;
    private final int[] fotos;

    public AdaptadorProgramas(Context context, String[] nombres, int[] fotos) {
        super(context, R.layout.item_lista_programa, nombres);
        this.nombres = nombres;
        this.context = context;
        this.fotos = fotos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_lista_programa, parent, false);
        TextView programa = (TextView) rowView.findViewById(R.id.programa_electoral);
        TextView nombre = (TextView) rowView.findViewById(R.id.nombre_lista_programa);
        ImageView foto = (ImageView) rowView.findViewById(R.id.foto_programa);
        nombre.setText(nombres[position]);
        foto.setImageResource(fotos[position]);
        return rowView;
    }
}
