package es.ageapps.eleccionesapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import es.ageapps.eleccionesapp.R;

import com.adindk.euafni261961.AdConfig;
import com.adindk.euafni261961.AdListener;
import com.adindk.euafni261961.Main;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import es.ageapps.eleccionesapp.modelo.Utils;
import io.fabric.sdk.android.Fabric;

public class ActividadPrincipal extends AppCompatActivity implements AdListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "RRRpRCOXJXO35xUs4UEDIwFKl";
    private static final String TWITTER_SECRET = "U4GzJyY7Cpzlj52IhEX2VLdIB0RKPLu1PwTBsm2wAbk1BagkCn";
    public static final String INDICE_PAGER
            = "es.ageapps.beleccionesapp.FragmentoIdeas.extra.INDICE_PAGER";
    public static final String IS_CANDIDATO
            = "es.ageapps.beleccionesapp.FragmentoIdeas.extra.IS_CANDIDATO";
    public static final String IS_ANTISONDEO
            = "es.ageapps.beleccionesapp.FragmentoIdeas.extra.IS_ANTISONDEO";
    static String[] comunidades;
    private DrawerLayout drawerLayout;
    private static int current;
    private NavigationView navigationView;
    private Main main; //Declare here

    private int[] ids = {R.id.item_inicio, R.id.item_noticias, R.id.item_partidos, R.id.item_sondeos, R.id.item_resultados
            , R.id.item_informacion, R.id.item_participa, R.id.item_compartir, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.actividad_principal);
        agregarToolbar();


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);


        comunidades = getResources().getStringArray(R.array.comunidades);

        if (navigationView != null) {
            prepararDrawer(navigationView);
            current = 7; // para que no coincida con ninguno
            // Seleccionar item por defecto
            if (savedInstanceState != null) {
                seleccionarItem(navigationView.getMenu().getItem(savedInstanceState.getInt("current")));
            } else {
                seleccionarItem(navigationView.getMenu().getItem(0));
            }
        }
        AdConfig.setAppId(294275);  //setting appid.
        AdConfig.setApiKey("1449110462261961948"); //setting apikey
        AdConfig.setAdListener(this);
        AdConfig.setCachingEnabled(true);
        AdConfig.setPlacementId(0);
        //initialize Airpush
        main = new Main(this); //Here this is reference of current Activity.

        //for calling banner 360
        main.start360BannerAd(this);  //pass the current Activity's reference.


    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    private void seleccionarItem(MenuItem itemDrawer) {

        boolean cambio = true;

        if (itemDrawer.getItemId() == ids[current]) {
            return;
        }
       // itemDrawer.setChecked(true);
        Fragment fragmentoGenerico = null;
        Bundle args = new Bundle();
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (itemDrawer.getItemId()) {
            case R.id.item_inicio:
                fragmentoGenerico = new es.ageapps.eleccionesapp.ui.FragmentoInicio();
                current = 0;
                break;
            case R.id.item_noticias:
                current = 1;
                fragmentoGenerico = new FragmentoPager();
                args.putInt(INDICE_PAGER, 1);
                fragmentoGenerico.setArguments(args);
                break;
            case R.id.item_partidos:
                current = 2;
                fragmentoGenerico = new FragmentoPartidos();
                break;
            case R.id.item_sondeos:
                boolean hayConexion4 = Utils.tengoConexion(this, "Necesita conexión para ver los Sondeos");
                if (hayConexion4) {
                    fragmentoGenerico = new FragmentoPager();
                    args.putInt(INDICE_PAGER, 4);
                    fragmentoGenerico.setArguments(args);
                    current = 3;
                    break;
                }
                itemDrawer.setChecked(false);
                cambio = false;
                Log.i("PRiNCIPAL", "cambio1 "+cambio);
                break;

            case R.id.item_resultados:
                boolean hayConexion6 = Utils.tengoConexion(this, "Necesita conexión para ver los Resultados");
                if (hayConexion6) {
                    fragmentoGenerico = new es.ageapps.eleccionesapp.ui.FragmentoResultados();
                    current = 4;
                    break;
                }
                itemDrawer.setChecked(false);
                cambio = false;
                break;
            case R.id.item_informacion:
                current = 5;
                fragmentoGenerico = new FragmentoInfo();
                args.putBoolean(FragmentoInfo.IS_INFO, true);
                fragmentoGenerico.setArguments(args);
                break;
            case R.id.item_participa:
                    current = 6;
                fragmentoGenerico = new FragmentoParticipa();
                break;
            case R.id.item_compartir:
                current = 7;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Mira la nueva aplicación de las Elecciones 2015.\n" +
                        "Aqui la puedes encontrar: " + "https://play.google.com/store/apps/details?id=es.ageapps.eleccionesapp");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                break;
        }
        // Setear título actual
        if (current < 7 & cambio) {
            Log.i("PRINCIPAL", "cambio3 "+cambio);
            setTitle(itemDrawer.getTitle());
        }
        if (fragmentoGenerico != null) {
            FragmentTransaction f = fragmentManager.beginTransaction();
            f.setCustomAnimations(android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            f.replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }
        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);


    }


    @Override
    public void onBackPressed() {
        if (current == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Cerrando Applicación")
                    .setMessage("Seguro que quieres cerrar Elecciones Generales 2015?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            moveTaskToBack(false);
                            System.runFinalizersOnExit(true);
                            System.exit(0);
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            seleccionarItem(navigationView.getMenu().getItem(0));
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.acerca:
                startActivity(new Intent(this, ActividadAcercaDe.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public static String[] getComunidades() {
        return comunidades;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public static void setCurrent(int c) {
        current = c;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("current", current);
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
