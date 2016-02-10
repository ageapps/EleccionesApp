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

/**
 * Created by adricacho on 03/12/15.
 */
public class FragmentoPartidos extends Fragment implements AdListener {
    private String TAG = "PARTIDOS";
    private View view;
    private CardView ideas;
    private CardView candidaturas;
    private CardView programas;
    private CardView info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragmento_partidos, container, false);

        ideas = (CardView) view.findViewById(R.id.boton_ideas);
        candidaturas = (CardView) view.findViewById(R.id.boton_candidaturas);
        programas = (CardView) view.findViewById(R.id.boton_programas);
        info = (CardView) view.findViewById(R.id.boton_info);


        AdConfig.setAppId(294275);  //setting appid.
        AdConfig.setApiKey("1449110462261961948"); //setting apikey
        AdView adView=(AdView) view.findViewById(R.id.myAdView);

        adView.setBannerType(AdView.BANNER_TYPE_IN_APP_AD);
        adView.setBannerAnimation(AdView.ANIMATION_TYPE_FADE);
        adView.showMRinInApp(false);
        adView.setNewAdListener(this);
        adView.loadAd();

        ideas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), ActividadContainer.class);
                i.putExtra(ActividadContainer.INDICE_PAGER, 2);
                view.getContext().startActivity(i);
            }
        });
        candidaturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), ActividadContainer.class);
                i.putExtra(ActividadContainer.INDICE_PAGER, 1);
                view.getContext().startActivity(i);
            }
        });
        programas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), ActividadContainer.class);
                i.putExtra(ActividadContainer.INDICE_PAGER, 3);
                view.getContext().startActivity(i);
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), ActividadContainer.class);
                i.putExtra(ActividadContainer.INDICE_PAGER, 5);
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
