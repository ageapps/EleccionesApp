package es.ageapps.eleccionesapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.adindk.euafni261961.AdConfig;
import com.adindk.euafni261961.AdListener;
import com.adindk.euafni261961.AdView;

import es.ageapps.eleccionesapp.R;
import es.ageapps.eleccionesapp.modelo.Utils;

/**
 * Created by adricacho on 15/12/15.
 */
public class FragmentoParticipa extends Fragment implements AdListener {
    private String TAG = "PARTIDOS";
    private View view;
    private CardView sondeo;
    private CardView antisondeo;
    private CardView test;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragmento_participa, container, false);

        sondeo = (CardView) view.findViewById(R.id.boton_sondeo);
        antisondeo = (CardView) view.findViewById(R.id.boton_antisondeo);
        test = (CardView) view.findViewById(R.id.boton_test);


        AdConfig.setAppId(294275);  //setting appid.
        AdConfig.setApiKey("1449110462261961948"); //setting apikey
        AdView adView=(AdView) view.findViewById(R.id.myAdView);

        adView.setBannerType(AdView.BANNER_TYPE_IN_APP_AD);
        adView.setBannerAnimation(AdView.ANIMATION_TYPE_FADE);
        adView.showMRinInApp(false);
        adView.setNewAdListener(this);
        adView.loadAd();

        sondeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hayConexion2 = Utils.tengoConexion(view.getContext(), "Necesita conexión para poder realizar el Sondeo");
                if (hayConexion2) {
                    Intent i = new Intent(view.getContext(), ActividadSondeo.class);
                    i.putExtra(ActividadPrincipal.IS_ANTISONDEO, false);
                    view.getContext().startActivity(i);
                }
            }
        });
        antisondeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hayConexion2 = Utils.tengoConexion(view.getContext(), "Necesita conexión para poder realizar el AntiSondeo");
                if (hayConexion2) {
                    Intent i = new Intent(view.getContext(), ActividadSondeo.class);
                    i.putExtra(ActividadPrincipal.IS_ANTISONDEO, true);
                    view.getContext().startActivity(i);
                }
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), ActividadTest.class);
                i.putExtra(FragmentoPregunta.INDICE_PREGUNTA, 0);
                view.getContext().startActivity(i);
            }
        });


        setHasOptionsMenu(true);
        return view;
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
