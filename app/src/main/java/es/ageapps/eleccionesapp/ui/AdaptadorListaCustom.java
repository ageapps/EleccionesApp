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

public class AdaptadorListaCustom extends ArrayAdapter<String> {
    private final Context context;
    private final String[] nombres;
    private final int[] subnombres;
    private final int[] fotos;
    private boolean isProgramas;
    private String[] comunidades;
    private int[] provincias = {R.drawable.ic_and, R.drawable.ic_ara, R.drawable.ic_ast, R.drawable.ic_bal, R.drawable.ic_can,
            R.drawable.ic_cant, R.drawable.ic_cartm, R.drawable.ic_castl, R.drawable.ic_cat, R.drawable.ic_ceut, R.drawable.ic_ext,
            R.drawable.ic_galicia, R.drawable.ic_mad, R.drawable.ic_mel, R.drawable.ic_mur, R.drawable.ic_nav, R.drawable.ic_rioj,
            R.drawable.ic_vas, R.drawable.ic_val};

    public AdaptadorListaCustom(Context context, String[] nombres, int[] subnombres, int[] fotos, boolean isProgramas) {
        super(context, R.layout.item_lista_custom, nombres);
        this.nombres = nombres;
        this.subnombres = subnombres;
        this.context = context;
        this.fotos = fotos;
        this.isProgramas = isProgramas;
        comunidades = context.getResources().getStringArray(R.array.comunidades);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String text_subnombre;
        int src_foto;
        if (isProgramas) {
            text_subnombre = "Programa Electoral 2015";
            src_foto = fotos[position];
        } else {
            text_subnombre = comunidades[subnombres[position]];
            src_foto = provincias[subnombres[position]-1];
        }
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_lista_custom, parent, false);
        TextView subnombre = (TextView) rowView.findViewById(R.id.subnombre_item);
        TextView nombre = (TextView) rowView.findViewById(R.id.nombre_item);
        ImageView foto = (ImageView) rowView.findViewById(R.id.foto_item);
        nombre.setText(nombres[position]);
        subnombre.setText(text_subnombre);
        foto.setImageResource(src_foto);
        return rowView;
    }
}
