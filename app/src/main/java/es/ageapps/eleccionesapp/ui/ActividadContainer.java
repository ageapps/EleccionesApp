package es.ageapps.eleccionesapp.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import es.ageapps.eleccionesapp.R;
import es.ageapps.eleccionesapp.modelo.Partido;

public class ActividadContainer extends AppCompatActivity {

    public static final String INDICE_PAGER
            = "INDICE_PAGER";
    private int current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contenido_principal);
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
            current = getIntent().getIntExtra(INDICE_PAGER, 5);
        }
        setTitle("Ideas");
        Fragment fragment = null;
        Bundle args = new Bundle();

        switch (current) {

            case 1:
                setTitle("Candidaturas");
                fragment = new FragmentoInfo();
                args.putBoolean(FragmentoInfo.IS_INFO, false);
                fragment.setArguments(args);
                break;
            case 2:
                setTitle("Ideas");
                fragment = new FragmentoPager();
                args.putInt(ActividadPrincipal.INDICE_PAGER, current);
                fragment.setArguments(args);
                break;
            case 3:
                setTitle("Programas");
                fragment = new FragmentoProgramas();
                break;
            case 4:
                setTitle("Candidatos");
                fragment = new FragmentoCandidatos();
                break;
            case 5:
                setTitle("Información");
                fragment = new FragmentoPartido();
                break;
            case 6:
               int indice = getIntent().getIntExtra(FragmentoPartidoDetalle.INDICE_PARTIDO, 0);
                setTitle(Partido.IU.getPartidos()[indice]);
                fragment = new FragmentoPartidoDetalle();
                args.putInt(FragmentoPartidoDetalle.INDICE_PARTIDO,indice );
                fragment.setArguments(args);
                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction f = fragmentManager.beginTransaction();
        f.replace(R.id.contenedor_principal, fragment)
                .commit();
        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("current", current);
    }


}
