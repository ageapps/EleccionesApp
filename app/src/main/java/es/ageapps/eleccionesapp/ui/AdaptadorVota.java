package es.ageapps.eleccionesapp.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import es.ageapps.eleccionesapp.R;
import es.ageapps.eleccionesapp.modelo.ItemVota;
import es.ageapps.eleccionesapp.modelo.Utils;

/**
 * Created by adricacho on 22/11/15.
 */
public class AdaptadorVota extends RecyclerView.Adapter<AdaptadorVota.ViewHolder> {

    private ItemVota[] items = ItemVota.values();


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public ImageView imagen;
        public final View mView;



        public ViewHolder(View v) {
            super(v);
            mView = v;
            nombre = (TextView) v.findViewById(R.id.nombre_vota);
            imagen = (ImageView) v.findViewById(R.id.miniatura_vota);

        }

    }


    @Override
    public int getItemCount() {
        return items.length;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_vota, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final ItemVota item = items[i];
        final int n = i;

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                    context.startActivity(item.getUrlIntent());
            }
        });
        Glide.with(viewHolder.mView.getContext())
                .load(item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());

    }



}