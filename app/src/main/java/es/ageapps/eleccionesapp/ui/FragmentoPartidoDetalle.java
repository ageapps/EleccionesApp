package es.ageapps.eleccionesapp.ui;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.adindk.euafni261961.AdConfig;
import com.adindk.euafni261961.AdListener;
import com.adindk.euafni261961.AdView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import es.ageapps.eleccionesapp.R;
import es.ageapps.eleccionesapp.modelo.Partido;

/**
 * Created by adricacho on 03/12/15.
 */
public class FragmentoPartidoDetalle extends Fragment implements AdListener {
    private String TAG = "PARTIDOS";
    private View view;
    private int indice;
    private TextView fundacion;
    private TextView origen;
    private TextView secretario;
    private TextView escanos;
    private ProgressDialog progress;
    private ImageView imagen;

    public static final String INDICE_PARTIDO
            = "INDICE_PARTIDO";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragmento_partido_detalle, container, false);

        AdConfig.setAppId(294275);  //setting appid.
        AdConfig.setApiKey("1449110462261961948"); //setting apikey
        AdView adView = (AdView) view.findViewById(R.id.myAdView);

        adView.setBannerType(AdView.BANNER_TYPE_IN_APP_AD);
        adView.setBannerAnimation(AdView.ANIMATION_TYPE_FADE);
        adView.showMRinInApp(false);
        adView.setNewAdListener(this);
        adView.loadAd();

        fundacion = (TextView) view.findViewById(R.id.fundacion);
        origen = (TextView) view.findViewById(R.id.origen);
        secretario = (TextView) view.findViewById(R.id.secretario);
        escanos = (TextView) view.findViewById(R.id.escanos);
        imagen = (ImageView) view.findViewById(R.id.imagen);

        indice = getArguments().getInt(INDICE_PARTIDO);

        imagen.setImageDrawable(getResources().getDrawable(Partido.Cs.getLogosGrandes()[indice]));

        setUp();
        setHasOptionsMenu(true);
        return view;
    }

    private void setUp() {
        progress = ProgressDialog.show(view.getContext(), "Cargando",
                "Se esta cargando el contenido del Partido", true);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("partido");
        query.whereEqualTo("partido", Partido.values()[indice].toString());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                ParseObject objeto = objects.get(0);

                fundacion.setText(objeto.getString("fundacion"));
                origen.setText(objeto.getString("origen"));
                secretario.setText(objeto.getString("secretario"));
                escanos.setText(objeto.getString("escanos"));
                progress.dismiss();
            }
        });
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
