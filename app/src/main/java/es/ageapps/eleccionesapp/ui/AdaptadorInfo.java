package es.ageapps.eleccionesapp.ui;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import es.ageapps.eleccionesapp.R;
import es.ageapps.eleccionesapp.modelo.ItemInfo;
import es.ageapps.eleccionesapp.modelo.Utils;

/**
 * Created by adricacho on 22/11/15.
 */
public class AdaptadorInfo extends RecyclerView.Adapter<AdaptadorInfo.ViewHolder> {

    private List<ItemInfo> items = new ArrayList<ItemInfo>();
    private boolean isInfo;
    private static int dimen;
    private String texto;

    public AdaptadorInfo(boolean isInfo) {
        this.isInfo = isInfo;
        if (isInfo) {
            items.add(ItemInfo.COMO);
            items.add(ItemInfo.LOCAL);
            items.add(ItemInfo.CIFRAS);
            items.add(ItemInfo.DUDAS);
            dimen = 200;
            texto = "más Información";

        } else {
            items.add(ItemInfo.PRESIDENCIA);
            items.add(ItemInfo.CONGRESO);
            items.add(ItemInfo.SENADO);
            dimen = 200;
            texto = "las Candidaturas";
        }

    }


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
            imagen.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dimen));
        }

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_vota, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final ItemInfo item = items.get(i);
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                boolean hayConexion2 = Utils.tengoConexion(context, "Necesita conexión para poder acceder a " + texto);
                if (hayConexion2) {
                    Intent i;
                    if (isInfo) {
                        i = new Intent(context, ActividadWeb.class);
                        i.putExtra(ActividadWeb.URL_WEB, item.getUrl());
                        i.putExtra(ActividadWeb.TITULO_WEB, item.getNombre());
                    } else if (item == ItemInfo.PRESIDENCIA) {
                        i = new Intent(context, ActividadContainer.class);
                        i.putExtra(ActividadContainer.INDICE_PAGER, 4);
                    } else {
                        boolean isSenado = item == ItemInfo.SENADO;
                        i = new Intent(context, ActividadProvincias.class);
                        i.putExtra(ActividadProvincias.IS_SENADO, isSenado);
                    }
                    context.startActivity(i);
                }
            }
        });
        Glide.with(viewHolder.mView.getContext()).
                load(item.getIdDrawable()).
                centerCrop().into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());

    }


}