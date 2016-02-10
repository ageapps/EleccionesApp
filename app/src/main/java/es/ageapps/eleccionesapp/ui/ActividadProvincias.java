package es.ageapps.eleccionesapp.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.adindk.euafni261961.AdConfig;
import com.adindk.euafni261961.AdView;

import es.ageapps.eleccionesapp.R;
import es.ageapps.eleccionesapp.modelo.Partido;

public class ActividadProvincias extends AppCompatActivity implements com.adindk.euafni261961.AdListener {

    private ListView list;
    private Snackbar snackbar;
    private CoordinatorLayout coordinatorLayout;
    private AdaptadorListaCustom adapter;
    private boolean isSenado;
    private String raiz;
    public static final String IS_SENADO
            = "es.ageapps.beleccionesapp.FragmentoIdeas.extra.IS_SENADO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_provincias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        AdConfig.setAppId(294275);  //setting appid.
        AdConfig.setApiKey("1449110462261961948"); //setting apikey
        AdView adView=(AdView) findViewById(R.id.myAdView);

        adView.setBannerType(com.adindk.euafni261961.AdView.BANNER_TYPE_IN_APP_AD);
        adView.setBannerAnimation(com.adindk.euafni261961.AdView.ANIMATION_TYPE_FADE);
        adView.showMRinInApp(false);
        adView.setNewAdListener(this);
        adView.loadAd();

        isSenado = getIntent().getBooleanExtra(IS_SENADO, true);
        if (isSenado) {
            raiz = "Senado";
        } else {
            raiz = "Congreso";
        }

        setTitle(raiz + " - Selecciona una Provincia");
        toolbar.setTitleTextAppearance(this, R.style.letraDetalle);

        String[] values = getResources().getStringArray(R.array.provincias);
        int[] subvalues = getResources().getIntArray(R.array.iconos_provincias);


        list = (ListView) findViewById(R.id.list_provincias);
        adapter = new AdaptadorListaCustom(ActividadProvincias.this, values, subvalues, Partido.PSOE.getLogos(), false);
        list.setAdapter(adapter);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinator_prov);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_provincias);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primaryDarkColor)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                snackbar =
                        Snackbar.make(coordinatorLayout, "Más Candidaturas", Snackbar.LENGTH_LONG)
                                .setAction("Aquí    ", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String url = "http://generales2015.interior.es/es/formaciones-politicas/" + raiz.toLowerCase() + "/proclamadas/";
                                                String titulo = "Candidaturas al " + raiz;
                                                snackbar.setDuration(Snackbar.LENGTH_SHORT);
                                                Intent i = new Intent(ActividadProvincias.this, ActividadWeb.class);
                                                i.putExtra(ActividadWeb.URL_WEB, url);
                                                i.putExtra(ActividadWeb.TITULO_WEB, titulo);
                                                startActivity(i);
                                            }
                                        }

                                );
                snackbar.show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ActividadProvincias.this, ActividadWeb.class);
                i.putExtra(ActividadWeb.URL_WEB, getUrl(position));
                i.putExtra(ActividadWeb.TITULO_WEB, "Candidaturas al "
                        + raiz + " - " + getResources().getStringArray(R.array.provincias)[position]);
                startActivity(i);
                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);

            }
        });
    }

    private String getUrl(int posicion) {
        String apellido = getResources().getStringArray(R.array.provincias_formateadas)[posicion];
        String url = "http://generales2015.interior.es/es/formaciones-politicas/" + raiz.toLowerCase() + "/proclamadas/" + apellido + "/";
        return url;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
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
