package es.ageapps.eleccionesapp.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import es.ageapps.eleccionesapp.R;
import es.ageapps.eleccionesapp.modelo.Candidato;
import es.ageapps.eleccionesapp.modelo.Partido;
import es.ageapps.eleccionesapp.modelo.Utils;

/**
 * Created by adricacho on 03/12/15.
 */
public class AdaptadorPartido extends RecyclerView.Adapter<AdaptadorPartido.ViewHolder> {


    private final Partido[] partidos = Partido.values();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView partido;
        public ImageView imagen;
        public final View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            nombre = (TextView) v.findViewById(R.id.nombre_candidato);
            partido = (TextView) v.findViewById(R.id.nompre_partido);
            imagen = (ImageView) v.findViewById(R.id.miniatura_foto);

        }
    }


    @Override
    public int getItemCount() {
        return partidos.length;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_candidatos, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Partido item = partidos[i];
        final int n = i;

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                boolean hayConexion = Utils.tengoConexion(context, "Necesita conexión para acceder a los Partidos");

                if (hayConexion) {
                    Intent intent = new Intent(context, ActividadContainer.class);
                    intent.putExtra(FragmentoPartidoDetalle.INDICE_PARTIDO, n);
                    intent.putExtra(ActividadContainer.INDICE_PAGER, 6);
                    context.startActivity(intent);
                }
            }
        });
        Glide.with(viewHolder.mView.getContext())
                .load(item.getLogosGrandes()[i])
                .into(viewHolder.imagen);
        viewHolder.partido.setText(item.getNombre());
        viewHolder.nombre.setText("");

    }


}
