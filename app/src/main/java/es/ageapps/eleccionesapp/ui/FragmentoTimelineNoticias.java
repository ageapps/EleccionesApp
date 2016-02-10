package es.ageapps.eleccionesapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adindk.euafni261961.AdConfig;
import com.adindk.euafni261961.AdView;
import com.adindk.euafni261961.Main;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TwitterListTimeline;

import es.ageapps.eleccionesapp.R;
import es.ageapps.eleccionesapp.modelo.Utils;
import io.fabric.sdk.android.Fabric;

/**
 * Created by adricacho on 08/11/15.
 */
public class FragmentoTimelineNoticias extends ListFragment implements com.adindk.euafni261961.AdListener {

    private static final String TWITTER_KEY = "D6OkgIsEqMuQVnIbxVswBkgRR";
    private static final String TWITTER_SECRET = "tllO3be0w3fXQpsJoKAQAmh5sNKgKB5ddwD1GnHgQxGRCznvWP";
    public static final String IS_ELECCIONES = "isEleciones";
    private TweetTimelineListAdapter adapter;
    private String TAG = " NOTICIAS";
    private View view;

    private boolean touched;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.fragmento_noticias, container, false);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(view.getContext(), new Twitter(authConfig));


        touched = true;


        AdConfig.setAppId(294275);  //setting appid.
        AdConfig.setApiKey("1449110462261961948"); //setting apikey
        AdView adView=(AdView) view.findViewById(R.id.myAdView_noticias);

        adView.setBannerType(com.adindk.euafni261961.AdView.BANNER_TYPE_IN_APP_AD);
        adView.setBannerAnimation(com.adindk.euafni261961.AdView.ANIMATION_TYPE_FADE);
        adView.showMRinInApp(false);
        adView.setNewAdListener(this);
        adView.loadAd();



        if (Utils.hayConexion(view.getContext())) {
            boolean is_eleccciones = getArguments().getBoolean(IS_ELECCIONES);

            if (is_eleccciones) {
                final TwitterListTimeline timeline = new TwitterListTimeline.Builder()
                        .slugWithOwnerScreenName("CandidatosElecciones2015", "elecciones_gene")
                        .build();

                adapter = new TweetTimelineListAdapter.Builder(view.getContext())
                        .setTimeline(timeline)
                        .build();
                setListAdapter(adapter);
            } else {

                final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                        .query("#20D")
                        .build();
                adapter = new TweetTimelineListAdapter.Builder(view.getContext())
                        .setTimeline(searchTimeline)
                        .build();
                setListAdapter(adapter);
            }
        } else {
            TextView noticias = (TextView) view.findViewById(R.id.titulo_noticias);
            TextView conexion = (TextView) view.findViewById(R.id.titulo_conexion);
            noticias.setText("No hay noticias");
            conexion.setText("Comprueba tu conexión a internet");

        }
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menu_noticias, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.noticias_actualizar:
                if (Utils.hayConexion(view.getContext())) {
                    if (!touched) {
                        break;
                    }
                    touched = (int) Math.abs(Math.random()*2) != 1;
                    getListView().setSelection(0);
                    final Tweet t = adapter.getItem(0);
                    Toast.makeText(view.getContext(), "Actualizando Mensajes", Toast.LENGTH_SHORT).show();
                    adapter.refresh(new Callback<TimelineResult<Tweet>>() {
                        @Override
                        public void success(Result<TimelineResult<Tweet>> result) {
                            Log.i(TAG, "refrescando");
                            if (result.data.items.get(0).equals(t)){
                                Toast.makeText(view.getContext(), "No hay mensajes nuevos", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void failure(TwitterException exception) {
                            Toast.makeText(view.getContext(), "Error de Red", Toast.LENGTH_SHORT).show();
                            // Toast or some other action
                        }
                    });
                } else {
                    Toast.makeText(view.getContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        this.getFragmentManager().beginTransaction().addToBackStack("timeline");
        super.onDestroyView();
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
