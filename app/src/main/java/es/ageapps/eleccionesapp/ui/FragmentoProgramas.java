package es.ageapps.eleccionesapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import com.adindk.euafni261961.AdView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.adindk.euafni261961.AdConfig;
import com.adindk.euafni261961.AdConfig.AdType;
import com.adindk.euafni261961.AdListener;
import com.adindk.euafni261961.Main;
import java.util.List;

import es.ageapps.eleccionesapp.R;
import es.ageapps.eleccionesapp.modelo.Partido;
import es.ageapps.eleccionesapp.modelo.Utils;

/**
 * Created by adricacho on 08/11/15.
 */
public class FragmentoProgramas extends ListFragment implements AdListener {
    private View view;
    private static final String INDICE_PARTIDO
            = "es.ageapps.beleccionesapp.FragmentoIdeas.extra.INDICE_PARTIDO";
    private AdaptadorListaCustom adapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragmento_programas, container, false);


        String[] values = {"Partido Popular", "Partido Socialista Obrero Español", "Ciudadanos", "Podemos",
                "Unión, Progreso y Democracia", "Izquierda Unida"

        };

        AdConfig.setAppId(294275);  //setting appid.
        AdConfig.setApiKey("1449110462261961948"); //setting apikey
        AdView adView=(AdView) view.findViewById(R.id.myAdView);

        adView.setBannerType(AdView.BANNER_TYPE_IN_APP_AD);
        adView.setBannerAnimation(AdView.ANIMATION_TYPE_FADE);
        adView.showMRinInApp(false);
        adView.setNewAdListener(this);
        adView.loadAd();

        // use custom layout
        adapter = new AdaptadorListaCustom(view.getContext(), values, null, Partido.PSOE.getLogos(),true);
        setListAdapter(adapter);
        setHasOptionsMenu(true);
        return view;

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        boolean hayConexion = Utils.tengoConexion(view.getContext(), "Necesita conexión para acceder a los Programas");

        if (hayConexion) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("lista");
            query.whereEqualTo("partido", Partido.values()[position].toString());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    String url = (String) objects.get(0).get("enlace");
                    Log.i("LISTA", "/" + url + "/");
                    if (url.contains("nada")) {
                        Toast.makeText(view.getContext(), "El programa todavía no esta disponible", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });

        }

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
    public void onAdCached(AdType adType) {

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
