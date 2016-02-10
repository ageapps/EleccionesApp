package es.ageapps.eleccionesapp.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import es.ageapps.eleccionesapp.R;
import es.ageapps.eleccionesapp.modelo.Partido;
import es.ageapps.eleccionesapp.modelo.Programa;
import es.ageapps.eleccionesapp.modelo.Utils;

/**
 * Adaptador para Programas"
 */
public class AdaptadorIdeas
        extends RecyclerView.Adapter<AdaptadorIdeas.ViewHolder> {


    private int indice_partido;
    private List<Programa> programas;
    private boolean queprograma;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public ImageView imagen;
        public final View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            nombre = (TextView) v.findViewById(R.id.nombre_programa);
            imagen = (ImageView) v.findViewById(R.id.miniatura_programa);
        }
    }


    public AdaptadorIdeas(int indice_partido, boolean queprograma) {
        this.indice_partido = indice_partido;
        this.queprograma = queprograma;
        programas = Partido.values()[indice_partido].getProgramas();
    }

    @Override
    public int getItemCount() {
        return programas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_ideas, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final int n = i;
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                boolean hayConexion = Utils.tengoConexion(context, "Necesita conexión para acceder a las Ideas");

                if (hayConexion) {
                    Intent intent = new Intent(context, ActividadDetalle.class);
                    intent.putExtra(ActividadDetalle.EXTRA_PROGRAMA, n);
                    intent.putExtra(ActividadDetalle.EXTRA_PARTIDO, indice_partido);
                    intent.putExtra(FragmentoIdeas.QUE_PROGRAMA, queprograma);
                    intent.putExtra(ActividadPrincipal.IS_CANDIDATO, false);
                    context.startActivity(intent);
                }
            }
        });

        viewHolder.nombre.setText(programas.get(n).getNombre());
        int imagen;
        if (queprograma) {
            imagen = programas.get(n).getIdDrawable1();
        } else {
            imagen = programas.get(n).getIdDrawable2();
            viewHolder.nombre.setTextColor(Color.parseColor("#37384E"));
        }

        Glide.with(viewHolder.mView.getContext()).load(imagen).centerCrop().into(viewHolder.imagen);


    }


}