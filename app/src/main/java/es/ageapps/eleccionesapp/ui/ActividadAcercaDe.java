package es.ageapps.eleccionesapp.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import es.ageapps.eleccionesapp.R;

public class ActividadAcercaDe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_acerca_de);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView email = (TextView) findViewById(R.id.id_correo);
        email.setTextColor(Color.parseColor("#2A2B39"));
        TextView web = (TextView) findViewById(R.id.id_web);
        web.setTextColor(Color.parseColor("#2A2B39"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
