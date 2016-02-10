package es.ageapps.eleccionesapp.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adindk.euafni261961.AdConfig;
import com.adindk.euafni261961.Main;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import es.ageapps.eleccionesapp.R;
import es.ageapps.eleccionesapp.modelo.Candidato;
import es.ageapps.eleccionesapp.modelo.Partido;
import es.ageapps.eleccionesapp.modelo.Programa;

/**
 * Created by adricacho on 04/11/15.
 */
public class ActividadDetalle extends AppCompatActivity implements com.adindk.euafni261961.AdListener {

    public static final String EXTRA_NAME = "nombre_candidato";
    public static final String EXTRA_PROGRAMA = "nombre_programa";
    public static final String EXTRA_PARTIDO = "nombre_partido";
    public static final int REQ_DETALLE = 193;

    private int indice;
    private Candidato candidato;
    private int indice_partido;
    private int indice_programa;
    private List<Programa> programas;
    private String nombre;
    private Uri foto;
    private String key;
    private String value;
    private String query_name;
    private boolean is_candidato;
    private CoordinatorLayout coordinatorLayout;
    private Snackbar snackbar;
    private ProgressDialog progress;
    private String imagen;
    private boolean girado;
    private Main main; //Declare here

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        girado = false;

        if (savedInstanceState != null) {
            girado = true;
            imagen = savedInstanceState.getString("imagen");
            is_candidato = savedInstanceState.getBoolean("isCandidato");
            if (is_candidato) {
                indice = savedInstanceState.getInt("indice");
            } else {
                indice_partido = savedInstanceState.getInt("indice partido");
                indice_programa = savedInstanceState.getInt("indice programa");
            }
        } else {
            Intent intent = getIntent();
            is_candidato = intent.getBooleanExtra(ActividadPrincipal.IS_CANDIDATO, true);
            if (is_candidato) {
                indice = intent.getIntExtra(EXTRA_NAME, 0);
            } else {
                indice_programa = intent.getIntExtra(EXTRA_PROGRAMA, 0);
                indice_partido = intent.getIntExtra(EXTRA_PARTIDO, 0);
            }
        }
        if (is_candidato) {
            setContentView(R.layout.actividad_detalle);
            candidato = Candidato.values()[indice];
            nombre = candidato.getNombre();
            key = "nombre";
            value = nombre;
            query_name = "candidato";
        } else {
            setContentView(R.layout.actividad_detalle_idea);

            programas = Partido.values()[indice_partido].getProgramas();
            nombre = programas.get(indice_programa).getNombre();
            query_name = "programa";
            key = "partido";
            value = Partido.values()[indice_partido].toString();
        }

        if (girado) {
            loadFoto(Uri.parse(imagen));
            if (is_candidato) {
                TextView tex_edad = (TextView) findViewById(R.id.edad);
                TextView tex_nacimiento = (TextView) findViewById(R.id.nacimiento);
                TextView tex_lugar = (TextView) findViewById(R.id.lugar);
                TextView tex_formacion = (TextView) findViewById(R.id.formacion);
                TextView tex_carrera = (TextView) findViewById(R.id.carrera);
                TextView tex_ocup = (TextView) findViewById(R.id.ocupacion);
                tex_edad.setText(savedInstanceState.getString("edad"));
                tex_nacimiento.setText(savedInstanceState.getString("nacimiento"));
                tex_lugar.setText(savedInstanceState.getString("lugar"));
                tex_formacion.setText(savedInstanceState.getString("formacion"));
                tex_carrera.setText(savedInstanceState.getString("carrera"));
                tex_ocup.setText(savedInstanceState.getString("ocupacion"));
            } else {
                TextView tex_descripcion = (TextView) findViewById(R.id.descripcion_programa);
                TextView tex_fuente = (TextView) findViewById(R.id.fuente_idea);
                tex_descripcion.setText(savedInstanceState.getString("descripcion"));
                tex_fuente.setText(savedInstanceState.getString("fuente"));

            }
        }

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle(nombre);
        if (is_candidato) {
            collapsingToolbar.setExpandedTitleTextAppearance(R.style.letraDetalle);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        if (!girado) {
            progress = ProgressDialog.show(this, "Cargando",
                    "Se esta cargando el contenido del " + query_name, true);

            ParseQuery<ParseObject> query = ParseQuery.getQuery(query_name);
            query.whereEqualTo(key, value);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (objects == null) {
                        Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    setUpView(is_candidato, objects.get(0));
                    progress.dismiss();
                }
            });
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.button);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primaryDarkColor)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                snackbar = Snackbar.make(coordinatorLayout, "Más Información", Snackbar.LENGTH_LONG)
                        .setAction("Aquí    ", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                snackbar.setDuration(Snackbar.LENGTH_SHORT);
                                if (is_candidato) {
                                    startActivity(candidato.getUrlIntent());
                                } else {
                                    startActivity(Partido.values()[indice_partido].getUrlIntent());
                                }
                            }
                        });
                snackbar.show();
            }
        });
        AdConfig.setAppId(294275);  //setting appid.
        AdConfig.setApiKey("1449110462261961948"); //setting apikey
        AdConfig.setAdListener(this);
        AdConfig.setCachingEnabled(true);
        AdConfig.setPlacementId(1);
        //initialize Airpush
        main=new Main(this); //Here this is reference of current Activity.

        //for calling banner 360
        main.start360BannerAd(this);  //pass the current Activity's reference.


    }


    private void setUpView(boolean is_candidato, ParseObject parseObject) {
        if (is_candidato) {
            TextView tex_edad = (TextView) findViewById(R.id.edad);
            TextView tex_nacimiento = (TextView) findViewById(R.id.nacimiento);
            TextView tex_lugar = (TextView) findViewById(R.id.lugar);
            TextView tex_formacion = (TextView) findViewById(R.id.formacion);
            TextView tex_carrera = (TextView) findViewById(R.id.carrera);
            TextView tex_ocup = (TextView) findViewById(R.id.ocupacion);

            tex_edad.setText(parseObject.getString("edad"));
            tex_nacimiento.setText(parseObject.getString("fechaNacimiento"));
            tex_lugar.setText(parseObject.getString("lugarNacimiento"));
            tex_formacion.setText(parseObject.getString("formacion"));
            tex_carrera.setText(parseObject.getString("descripcion"));
            tex_ocup.setText(parseObject.getString("profesion"));


            imagen = parseObject.getParseFile("foto").getUrl();
            foto = Uri.parse(imagen);

        } else {
            TextView tex_fuente = (TextView) findViewById(R.id.fuente_idea);
            tex_fuente.setText(parseObject.getString("fuente"));
            TextView tex_descripcion = (TextView) findViewById(R.id.descripcion_programa);
            tex_descripcion.setText(parseObject.getString(programas.get(indice_programa).toString()));
            imagen = parseObject.getParseFile("foto" + programas.get(indice_programa).toString()).getUrl();
            foto = Uri.parse(imagen);

        }
        loadFoto(foto);


    }


    private void loadFoto(Uri foto) {
        ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(foto).centerCrop().into(imageView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isCandidato", is_candidato);
        outState.putString("imagen", imagen);
        if (is_candidato) {
            TextView tex_edad = (TextView) findViewById(R.id.edad);
            TextView tex_nacimiento = (TextView) findViewById(R.id.nacimiento);
            TextView tex_lugar = (TextView) findViewById(R.id.lugar);
            TextView tex_formacion = (TextView) findViewById(R.id.formacion);
            TextView tex_carrera = (TextView) findViewById(R.id.carrera);
            TextView tex_ocup = (TextView) findViewById(R.id.ocupacion);

            outState.putInt("indice", indice);
            outState.putString("edad", tex_edad.getText().toString());
            outState.putString("nacimiento", tex_nacimiento.getText().toString());
            outState.putString("lugar", tex_lugar.getText().toString());
            outState.putString("formacion", tex_formacion.getText().toString());
            outState.putString("carrera", tex_carrera.getText().toString());
            outState.putString("ocupacion", tex_ocup.getText().toString());
        } else {
            TextView tex_fuente = (TextView) findViewById(R.id.fuente_idea);
            TextView tex_descripcion = (TextView) findViewById(R.id.descripcion_programa);
            outState.putString("descripcion", tex_descripcion.getText().toString());
            outState.putString("fuente", tex_fuente.getText().toString());
            outState.putInt("indice programa", indice_programa);
            outState.putInt("indice partido", indice_partido);
        }
    }


    @Override
    public void onBackPressed() {
        finish();
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