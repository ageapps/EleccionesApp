package es.ageapps.eleccionesapp.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import es.ageapps.eleccionesapp.R;


/**
 * Created by adricacho on 15/12/15.
 */
public class ActividadTest extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private Button but_1;
    private Button but_2;
    private Button but_3;
    private int current;
    private int[] resultados = new int[25];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (savedInstanceState != null) {
            current = savedInstanceState.getInt("current");
        } else {
            current = getIntent().getIntExtra(FragmentoPregunta.INDICE_PREGUNTA, 0);
            setTitle("Test de afinidad - " + (current + 1));
        }
        progressBar = (ProgressBar) findViewById(R.id.progressBarPregunta);
        progressBar.setProgress(0);
        but_1 = (Button) findViewById(R.id.resp1);
        but_2 = (Button) findViewById(R.id.resp2);
        but_3 = (Button) findViewById(R.id.resp3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            but_1.setBackground(getResources().getDrawable(R.drawable.custom_button));
            but_2.setBackground(getResources().getDrawable(R.drawable.custom_button));
            but_3.setBackground(getResources().getDrawable(R.drawable.custom_button));
        }
        but_1.setOnClickListener(this);
        but_2.setOnClickListener(this);
        but_3.setOnClickListener(this);
        cambiaPregunta(current);
        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);

    }

    private void cambiaPregunta(int indice) {
        progressBar.setProgress((int) ((double) indice / 25 * 100));
        setTitle("Test de afinidad - " + (current + 1));
        Bundle args = new Bundle();
        args.putInt(FragmentoPregunta.INDICE_PREGUNTA, (indice + 1));
        Fragment fragment = new FragmentoPregunta();
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction f = fragmentManager.beginTransaction();
        f.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        f.replace(R.id.fragmento_pregunta, fragment)
                .commit();
    }

    @Override
    public void onClick(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            but_1.setBackground(getResources().getDrawable(R.drawable.custom_button));
            but_2.setBackground(getResources().getDrawable(R.drawable.custom_button));
            but_3.setBackground(getResources().getDrawable(R.drawable.custom_button));
        }
        int resp = 0;
        switch (v.getId()) {
            case R.id.resp1:
                resp = 1;
                break;
            case R.id.resp2:
                resp = 2;
                break;
            case R.id.resp3:
                resp = 3;
                break;
        }
        resultados[current] = resp;
        Log.i("TEST", "Pregunta " + current + " = " + resp);
        if (current == 24) {
            Intent i = new Intent(v.getContext(), ActividadVotosUsuarios.class);
            i.putExtra(ActividadVotosUsuarios.RESULTADOS_TEST, resultados);
            v.getContext().startActivity(i);
            finish();
            return;
        }
        //  v.setBackgroundColor(getResources().getColor(R.color.colorCandidatos));
        current = current + 1;
        cambiaPregunta(current);
    }

    @Override
    public void onBackPressed() {
        if (current == 0) {
            finish();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction f = fragmentManager.beginTransaction();
            f.setCustomAnimations(android.R.anim.slide_out_right,
                    android.R.anim.slide_out_right);
            return;
        }
        current--;
        cambiaPregunta(current);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            but_1.setBackground(getResources().getDrawable(R.drawable.custom_button));
            but_2.setBackground(getResources().getDrawable(R.drawable.custom_button));
            but_3.setBackground(getResources().getDrawable(R.drawable.custom_button));
            switch (resultados[current]) {
                case 1:
                    but_1.setBackground(getResources().getDrawable(R.drawable.custom_button_selected));
                    break;
                case 2:
                    but_2.setBackground(getResources().getDrawable(R.drawable.custom_button_selected));
                    break;
                case 3:
                    but_3.setBackground(getResources().getDrawable(R.drawable.custom_button_selected));
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.test_close: {
                finish();
                overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("current", current);
    }


}
