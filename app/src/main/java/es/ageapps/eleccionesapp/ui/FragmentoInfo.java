package es.ageapps.eleccionesapp.ui;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import es.ageapps.eleccionesapp.R;

/**
 * Created by adricacho on 22/11/15.
 */
public class FragmentoInfo extends Fragment {

    private RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    private es.ageapps.eleccionesapp.ui.AdaptadorInfo adaptador;
    private View view;
    private CoordinatorLayout coordinatorLayout;
    public static final String IS_INFO
            = "es.ageapps.beleccionesapp.FragmentoIdeas.extra.IS_INFO";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);
        layoutManager = new GridLayoutManager(getActivity(), 1);
        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        reciclador.setLayoutManager(layoutManager);

        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id
                .main_content);
        adaptador = new AdaptadorInfo(getArguments().getBoolean(IS_INFO));
        reciclador.setAdapter(adaptador);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menu_vacio, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
    }


}
