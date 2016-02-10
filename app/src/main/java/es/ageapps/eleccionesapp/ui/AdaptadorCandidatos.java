package es.ageapps.eleccionesapp.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import es.ageapps.eleccionesapp.modelo.Utils;

/**
 * Adaptador para comidas usadas en la sección "Candidato"
 */
public class AdaptadorCandidatos
        extends RecyclerView.Adapter<AdaptadorCandidatos.ViewHolder> {


    private final Candidato[] candidatos = Candidato.values();

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
        return candidatos.length;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_candidatos, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Candidato item = candidatos[i];
        final int n = i;

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                boolean hayConexion = Utils.tengoConexion(context,"Necesita conexión para acceder a los Candidatos");

                if (hayConexion) {
                    Intent intent = new Intent(context, ActividadDetalle.class);
                    intent.putExtra(ActividadPrincipal.IS_CANDIDATO, true);
                    intent.putExtra(ActividadDetalle.EXTRA_NAME, n);
                    context.startActivity(intent);
                }
            }
        });
        Glide.with(viewHolder.mView.getContext())
                .load(item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());
        viewHolder.partido.setText(item.getPartido());

    }


}