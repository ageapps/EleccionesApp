package es.ageapps.eleccionesapp.ui;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adindk.euafni261961.AdConfig;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import es.ageapps.eleccionesapp.R;
import es.ageapps.eleccionesapp.modelo.Partido;

/**
 * Fragmento que representa el contenido de cada pestaña dentro de la sección "Programa"
 */
public class FragmentoIdeas extends Fragment implements com.adindk.euafni261961.AdListener {

    private static final String INDICE_PARTIDO
            = "es.ageapps.beleccionesapp.FragmentoIdeas.extra.INDICE_PARTIDO";
    public static final String QUE_PROGRAMA
            = "es.ageapps.beleccionesapp.FragmentoIdeas.extra.QUE_PROGRAMA";
    private TextView cabecera;

    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private es.ageapps.eleccionesapp.ui.AdaptadorIdeas adaptador;
    private CoordinatorLayout coordinatorLayout;
    private Snackbar snackbar;
    private View view;


    public static FragmentoIdeas nuevaInstancia(int indiceSeccion, boolean indiceprograma) {
        es.ageapps.eleccionesapp.ui.FragmentoIdeas fragment = new FragmentoIdeas();
        Bundle args = new Bundle();
        args.putInt(INDICE_PARTIDO, indiceSeccion);
        args.putBoolean(QUE_PROGRAMA, indiceprograma);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmento_grupo_ideas, container, false);

        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id
                .main_content);
        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);
        cabecera = (TextView) view.findViewById(R.id.id_cabecera);


        AdConfig.setAppId(294275);  //setting appid.
        AdConfig.setApiKey("1449110462261961948"); //setting apikey
        com.adindk.euafni261961.AdView adView=(com.adindk.euafni261961.AdView) view.findViewById(R.id.myAdView);

        adView.setBannerType(com.adindk.euafni261961.AdView.BANNER_TYPE_IN_APP_AD);
        adView.setBannerAnimation(com.adindk.euafni261961.AdView.ANIMATION_TYPE_FADE);
        adView.showMRinInApp(false);
        adView.setNewAdListener(this);
        adView.loadAd();

        final int indiceSeccion = getArguments().getInt(INDICE_PARTIDO);
        boolean queprograma = getArguments().getBoolean(QUE_PROGRAMA);
        switch (indiceSeccion) {
            case 0:
                cabecera.setText("Partido Popular");
                cabecera.setBackgroundColor(getResources().getColor(R.color.color_pp));
                break;
            case 1:
                cabecera.setText("Partido Socialista");
                cabecera.setBackgroundColor(getResources().getColor(R.color.color_psoe));
                break;
            case 2:
                cabecera.setText("Ciudadanos");
                cabecera.setBackgroundColor(getResources().getColor(R.color.color_c));
                break;
            case 3:
                cabecera.setText("Podemos");
                cabecera.setBackgroundColor(getResources().getColor(R.color.color_podemos));
                break;
            case 4:
                cabecera.setText("Unión Progreso y Democracia");
                cabecera.setBackgroundColor(getResources().getColor(R.color.color_up));
                break;
            case 5:
                cabecera.setText("Izquierda Unida");
                cabecera.setBackgroundColor(getResources().getColor(R.color.color_iu));
                break;

        }

        adaptador = new AdaptadorIdeas(indiceSeccion, queprograma);
        reciclador.setAdapter(adaptador);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_ideas);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primaryDarkColor)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                snackbar =
                        Snackbar.make(coordinatorLayout, "Más Programas", Snackbar.LENGTH_LONG)
                                .setAction("Aquí    ", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        snackbar.setDuration(Snackbar.LENGTH_SHORT);
                                        startActivity(Partido.values()[indiceSeccion].getUrlIntent());
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
        this.getFragmentManager().beginTransaction().addToBackStack("programa");
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

        return super.

                onOptionsItemSelected(item);
    }


    @Override
    public void onAdCached(AdConfig.AdType adType) {

    }

    @Override
    public void onIntegrationError(String s) {

    }

    @Override
    public void onAdError(String s) {

    }

    @Override
    public void noAdListener() {

    }

    @Override
    public void onAdShowing() {

    }

    @Override
    public void onAdClosed() {

    }

    @Override
    public void onAdLoadingListener() {

    }

    @Override
    public void onAdLoadedListener() {

    }

    @Override
    public void onCloseListener() {

    }

    @Override
    public void onAdExpandedListner() {

    }

    @Override
    public void onAdClickedListener() {

    }
}
