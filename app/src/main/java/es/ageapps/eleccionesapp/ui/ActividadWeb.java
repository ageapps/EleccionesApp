package es.ageapps.eleccionesapp.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.adindk.euafni261961.AdConfig;
import com.adindk.euafni261961.Main;

import es.ageapps.eleccionesapp.R;

public class ActividadWeb extends AppCompatActivity implements com.adindk.euafni261961.AdListener {

    public static final String URL_WEB
            = "es.ageapps.beleccionesapp.FragmentoInfo.extra.URL_WEB";
    public static final String TITULO_WEB
            = "es.ageapps.beleccionesapp.FragmentoInfo.extra.TITULO_WEB";

    private WebView mWeb;
    private String url;
    private String titulo;
    private Main main; //Declare here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_web);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        url = getIntent().getStringExtra(URL_WEB);
        titulo = getIntent().getStringExtra(TITULO_WEB);
        mWeb = (WebView) findViewById(R.id.id_web);

        AdConfig.setAppId(294275);  //setting appid.
        AdConfig.setApiKey("1449110462261961948"); //setting apikey
        AdConfig.setAdListener(this);
        AdConfig.setCachingEnabled(true);
        AdConfig.setPlacementId(2);
        //initialize Airpush
        main=new Main(this); //Here this is reference of current Activity.

        //for calling banner 360
        main.start360BannerAd(this);  //pass the current Activity's reference.

        setTitle(titulo);
        toolbar.setTitleTextAppearance(this,R.style.letraDetalle);
        WebSettings web_sett = mWeb.getSettings();
        web_sett.setJavaScriptEnabled(true);
        mWeb.loadUrl(url);
        mWeb.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
                findViewById(R.id.progress1).setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                findViewById(R.id.progress1).setVisibility(View.GONE);
            }
        });



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
