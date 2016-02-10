package es.ageapps.eleccionesapp.ui;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import es.ageapps.eleccionesapp.R;
import es.ageapps.eleccionesapp.modelo.Partido;

/**
 * Fragmento que representa el contenido de cada pestaña dentro de la sección "Candidato"
 */
public class FragmentoCandidatos extends Fragment {

    private RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    private AdaptadorCandidatos adaptador;
    private View view;
    private CoordinatorLayout coordinatorLayout;
    private Snackbar snackbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        reciclador.setLayoutManager(layoutManager);

        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id
                .main_content);
        adaptador = new AdaptadorCandidatos();
        reciclador.setAdapter(adaptador);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primaryDarkColor)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                snackbar =
                        Snackbar.make(coordinatorLayout, "Más Candidatos", Snackbar.LENGTH_LONG)
                                .setAction("Aquí    ", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        snackbar.setDuration(Snackbar.LENGTH_SHORT);
                                        String url = "http://www.eitb.eus/es/elecciones/elecciones-generales/candidatos-television-radio-internet/";
                                        Intent i = new Intent(view.getContext(), ActividadWeb.class);
                                        i.putExtra(ActividadWeb.TITULO_WEB, "Información otros Candidatos");
                                        i.putExtra(ActividadWeb.URL_WEB, url);
                                        startActivity(i);
                                    }
                                });
                snackbar.show();
            }
        });
        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onDestroyView() {
        this.getFragmentManager().beginTransaction().addToBackStack("candidato");
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
