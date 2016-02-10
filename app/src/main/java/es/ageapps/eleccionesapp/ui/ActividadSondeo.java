package es.ageapps.eleccionesapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adindk.euafni261961.AdConfig;
import com.adindk.euafni261961.AdListener;
import com.adindk.euafni261961.Main;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.ageapps.eleccionesapp.EleccionesApp;
import es.ageapps.eleccionesapp.R;
import es.ageapps.eleccionesapp.modelo.Partido;
import es.ageapps.eleccionesapp.modelo.Utils;

public class ActividadSondeo extends AppCompatActivity implements AdListener {

    private String TAG = "ActividadSondeo";
    private CoordinatorLayout coordinatorLayout;
    private Snackbar snackbar;
    private RadioGroup grupoPartido;
    private Spinner comunidades;
    private String indice_comunidad;
    private RadioButton partido;
    private boolean is_antisondeo;
    private String name;
    private boolean dialogo;
    private boolean touched;
    private Main main; //Declare here


    public static String[] nombres_comunidades = {"ninguna", "andalucia", "aragon", "asturias", "Baleares", "canarias", "cantabria",
            "castillal", "castillam", "cataluña", "ceuta", "extremadura", "galicia", "larioja", "madrid", "melilla", "murcia", "navarra", "paisvasco",
            "valencia"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.actividad_sondeo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sondeo);
        setSupportActionBar(toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_sondeo);
        grupoPartido = (RadioGroup) findViewById(R.id.radioPartido);

        comunidades = (Spinner) findViewById(R.id.spinner);

        loadSpinnerComunidades();

        AdConfig.setAppId(29011);
        AdConfig.setApiKey("1345103177648703821");
        AdConfig.setAdListener(this);
        AdConfig.setCachingEnabled(true);
        AdConfig.setPlacementId(0);
        main=new Main(this);
        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);

        dialogo = false;
        if (savedInstanceState != null) {
            dialogo = savedInstanceState.getBoolean("DIALOGO");
        }

        is_antisondeo = getIntent().getBooleanExtra(ActividadPrincipal.IS_ANTISONDEO, false);

        if (is_antisondeo) {
            name = "anti_votos_usuarios";
            getSupportActionBar().setTitle("AntiSondeo");
            TextView elige = (TextView) findViewById(R.id.titulo_elige_partido);
            elige.setText("Elige el partido que NO te gustaría que ganara ");
        } else {
            name = "votos_usuarios";
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_sondeo);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primaryDarkColor)));
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(final View view) {
                                       snackbar = Snackbar.make(coordinatorLayout, "Enviar Sondeo ?", Snackbar.LENGTH_LONG).setAction("Si    ", new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       snackbar.setDuration(Snackbar.LENGTH_SHORT);
                                                       int selectedId = grupoPartido.getCheckedRadioButtonId();


                                                       if (comunidades.getSelectedItem() == null) {
                                                           Toast.makeText(ActividadSondeo.this, "Seleccione su Comunidad", Toast.LENGTH_LONG).show();
                                                           return;
                                                       }
                                                       if (selectedId == -1) {
                                                           Toast.makeText(ActividadSondeo.this, "Seleccione Partido", Toast.LENGTH_LONG).show();
                                                           return;
                                                       }

                                                       indice_comunidad = comunidades.getSelectedItem().toString();
                                                       partido = (RadioButton) findViewById(selectedId);
                                                       Log.d(TAG, "Partido: " + partido.getText().toString() +
                                                               "\nComunidad: " + indice_comunidad);
                                                       boolean tengoConexion = Utils.tengoConexion(view.getContext(),
                                                               "Necesita conexión para poder enviar el sondeo");
                                                       if (tengoConexion) {

                                                           String toast = "Mañana podrás votar de nuevo";

                                                           Date currentDate = new Date(System.currentTimeMillis());
                                                           ParseQuery<ParseObject> query3 = ParseQuery.getQuery("IDS");
                                                           boolean votado = true;
                                                           try {
                                                               ParseObject objeto_voto = query3.whereEqualTo("idVoto", EleccionesApp.myId()).getFirst();
                                                               Log.i(TAG, objeto_voto.getUpdatedAt().getDay() + " -> " + currentDate.getDay());
                                                               if (objeto_voto.getUpdatedAt().getDay() != currentDate.getDay()) {
                                                                   votado = false;
                                                                   query3.getInBackground(objeto_voto.getObjectId(), new GetCallback<ParseObject>() {
                                                                       @Override
                                                                       public void done(ParseObject object, ParseException e) {
                                                                           int votos = (int) object.getNumber("num_votos");
                                                                           object.put("num_votos", votos + 1);
                                                                           object.saveInBackground();
                                                                       }
                                                                   });


                                                               }


                                                           } catch (ParseException e) {
                                                               Log.i(TAG, "primer voto");
                                                               votado = false;
                                                               ParseObject object = new ParseObject("IDS");
                                                               object.put("idVoto", EleccionesApp.myId());
                                                               object.put("num_votos", 1);
                                                               object.saveInBackground();

                                                           }

                                                           if (votado) {
                                                               Toast.makeText(view.getContext(), "Ya has votado hoy", Toast.LENGTH_SHORT).show();
                                                               return;
                                                           }


                                                           String part = partido.getText().toString();

                                                           String id = getId(indice_comunidad, part);

                                                           Log.i("IDS", "ID " + id);
                                                           ParseQuery<ParseObject> query = ParseQuery.getQuery(name);
                                                           query.getInBackground(id, new GetCallback<ParseObject>() {
                                                               @Override
                                                               public void done(ParseObject object, ParseException e) {
                                                                   int cuenta = (int) object.getNumber("num");
                                                                   object.put("num", cuenta + 1);
                                                                   object.saveInBackground();
                                                               }
                                                           });


                                                           Toast.makeText(view.getContext(), "Sondeo Enviado", Toast.LENGTH_SHORT).show();


                                                           Toast.makeText(view.getContext(), toast, Toast.LENGTH_SHORT).show();
                                                           main.startInterstitialAd(AdConfig.AdType.interstitial);
                                                           onBackPressed();
                                                       } else {
                                                           Toast.makeText(view.getContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                                                       }

                                                   }
                                               }

                                       );
                                       snackbar.show();
                                   }
                               }

        );


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (!dialogo)

        {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("Información del Sondeo");
            b.setMessage("Los Datos proporcionados en este sondeo son completamente anónimos.\n " +
                    "El propósito del sondeo es meramente informativo, los datos no serán utilizados con cualquier otro fín.");
            b.setPositiveButton("OK", new DialogInterface.OnClickListener()

                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }

            );
            dialogo = true;
            b.show();
        }

    }

    private void loadSpinnerComunidades() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.comunidades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        this.comunidades.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        outState.putBoolean("DIALOGO", dialogo);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private String getId(String comunidad, String partido) {
        String[] ids = {"1QLWFvpFmj", "sEB2R2Zp3P", "kTNcshhK0Z", "YQ3LazQINM", "2c0iGPCK0f", "Rgd414r1wY", "vkP7khiJMW",
                "jQ9R378GuE", "93GtPTnwPN", "r8MdSjpEhi", "RwKuewDg24", "it6zNySP0S", "ESBcyfF9VQ", "9aXxPxdsvG", "R3wXfKAPCs",
                "GpQzYdRFY0", "4Jp3PKKhLL", "9eg7aZRYmM", "7pZmPZk78A", "atwW2Bq5Ju", "VfhGu8qBzJ", "mZfy3e7rgl", "wbqmSvSNZP",
                "HE3cmteXHa", "VFrdrbw3Rh", "v5dtLzjuFR", "X47WirceXE", "kp7S4qKByE", "w2GIc7tyL0", "Fzg5Oasuzo", "Fhh8IpTr4x",
                "67rEnHvcSr", "b0X3JBh9O2", "ze9FaixXi4", "JaH7nxZ1ma", "BaYzvy27VK", "nhvx0fGZgZ", "gQJEHleHee", "82kKKfJ4dK",
                "NTVoaKGqQG", "Meru2aq6vX", "B3PcOlLIDq", "ODZLvEPkP6", "j3BfnPQPcF", "OSn0RFi10J", "oBAD2MKq04", "hheRx2pTPZ",
                "ZsQmny3QQr", "bgNbhrKGhW", "SyaSM1Nzt0", "5VP0N6ntGB", "AQ7ELrzS7c", "ajVpZYP83n", "LXEPvQ3tmM", "KWb0mrBNNV",
                "4VWr5Ez1tM", "OKJgWa9anx", "3HwQMsIq72", "wsYFOlngrL", "D85wCxDf7c", "A6cbJc9ZdT", "T10l86vSay", "AqcgGsz2oV",
                "3Fb9LJgUnR", "sGhED7srOg", "K25UwWsF8a", "vP15rJkqna", "aDMsirxQGx", "wJ7MGsHkk2", "pGJLIYPOQC", "2lQJIjPwhK",
                "eB4KtQv2ps", "rarXcX6jb2", "NQRMKemDjt", "hvqSpDt3F5", "jRyc7Lt3YD", "cMHuXH8xhz", "r7Q7HMT33T", "U2pQCI39q0",
                "rLTshSgCQQ", "WodROycvOO", "TOWa9Y2DI9", "ljDTAODGDg", "MglhbwYrrF", "eXyXOU72pz", "uZ6rAGCMZW", "Pov8q57CqF",
                "kMzy08yzqC", "wHHGlc40wP", "Ns4aPyN6KM", "oQrahXRpJT", "yYZhlfwm9P", "gW0knFqPrO", "oBqTPDZ3cg", "Yn6L2Jf5Tt",
                "JJ2J92kIQ6", "TBYk2WKKBl", "qI7JHDTYcB", "SFw1AmJgf3", "k2MBkGGSwW", "1a25DzRpVg", "lrNLVtpJ4g", "aOxrDVIz9x",
                "7ZOxIPiFAm", "op8vkrGI89", "zQnrcIGKl6", "ah5gRkMsol", "N23eslBRP9", "iqudqKsX3n", "ekoMNwoDna", "Jjvq2Fjmha",
                "g2UwHyJF5I", "cGM36wbUM5", "lgRqLNqCgi", "FXfSBl1Bba", "SWa7ZHsx6K", "aVIPVc05oA", "5xwDruMOe3", "kkcPvv6BWV",
                "v8Fjk0hVmq", "Luzh3gvlVN", "934QecUVUy", "Vih4w9VAUN", "f9zQugTenr", "Oj06tRz7LS", "6Nsa81SdGb", "bIFSaBMDaK",
                "G7AHWqGKY0", "fTxrGfgKAN", "Q58hxoDFGA", "JN83SgaSEU", "YJhRA0MKXp", "YWhJsS41zs", "x1mCyKsarV", "eY4lWSHCzj",
                "YYtNkDw1KM", "IqwJAqYrCZ", "TA1kaneXuh", "fS7rM5L5Q2", "cVJQjK2Sfb"

        };

        int indice = 0;


        switch (partido) {
            case "Ciudadanos": {
                indice = 0;
                break;
            }
            case "IU": {
                indice = 20;
                break;
            }
            case "Otro": {
                indice = 40;
                break;
            }
            case "PP": {
                indice = 60;
                break;
            }
            case "PSOE": {
                indice = 80;
                break;
            }
            case "Podemos": {
                indice = 100;
                break;
            }
            case "UPyD": {
                indice = 120;
                break;
            }
        }


        switch (comunidad) {
            case "--Ninguna--": {
                indice = indice + 0;
                break;
            }
            case "Andalucía": {
                indice = indice + 1;
                break;
            }
            case "Aragón": {
                indice = indice + 2;

                break;
            }
            case "Asturias": {
                indice = indice + 3;

                break;

            }
            case "Baleares": {
                indice = indice + 4;

                break;

            }
            case "Canarias": {
                indice = indice + 5;

                break;

            }
            case "Cantabria": {
                indice = indice + 6;

                break;

            }
            case "Castilla la Mancha": {
                indice = indice + 7;

                break;

            }
            case "Castilla y León": {
                indice = indice + 8;

                break;

            }
            case "Cataluña": {
                indice = indice + 9;

                break;

            }
            case "Ceuta": {
                indice = indice + 10;

                break;

            }
            case "Extremadura": {

                indice = indice + 11;

                break;
            }
            case "Galicia": {

                indice = indice + 12;

                break;
            }
            case "La Rioja": {

                indice = indice + 13;

                break;
            }
            case "Madrid": {

                indice = indice + 14;

                break;
            }
            case "Melilla": {

                indice = indice + 15;

                break;
            }
            case "Murcia": {

                indice = indice + 16;

                break;
            }
            case "Navarra": {

                indice = indice + 17;

                break;
            }
            case "País Vasco": {

                indice = indice + 18;

                break;
            }
            case "Valencia": {

                indice = indice + 19;

                break;
            }
        }


        return ids[indice];


    }
    @Override
    public void onBackPressed() {
        try {
            //Displaying Cached SmartWall Ad
            main.showCachedAd(AdConfig.AdType.smartwall);
        } catch (Exception e)
        {
            super.onBackPressed();
            finish();
            overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
        }
        ActividadPrincipal.setCurrent(8);
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
