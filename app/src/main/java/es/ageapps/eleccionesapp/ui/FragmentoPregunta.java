package es.ageapps.eleccionesapp.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adindk.euafni261961.AdConfig;
import com.adindk.euafni261961.AdListener;
import com.adindk.euafni261961.AdView;

import es.ageapps.eleccionesapp.R;

/**
 * Created by adricacho on 15/12/15.
 */
public class FragmentoPregunta extends Fragment implements AdListener {

    public static final String INDICE_PREGUNTA
            = "INDICE_PREGUNTA";

    private View view;
    private int indice;

    private TextView pregunta;
    private String[] preguntas = {"España debería ser una República en vez de una Monarquía",

            "España debe estar en la Unión Europea",

            "El Estado Español debe de ser Laico",

            "El Estado debería aumentar la financiación en las escuelas públicas, y reducir la de las escuelas concertadas",

            "Los inmigrantes ilegales no deberían tener los mismos derechos que los españoles en sanidad, educación y/o ayudas sociales",

            "La eutanasia debe ser legalizada",

           "El sistema electoral debería ser reformado para que no beneficie demasiado a los grandes partidos",

            "El gobierno debería proveer una renta mínima a todas las personas en necesidad, con una cuantía que se aproxime al salario mínimo interprofesional",

            "La cadena perpetua debería contemplarse para algunos delitos muy graves",

            "Debería redistribuirse la riqueza de los más ricos a los más pobres",

            "No se debería subir el salario mínimo, porque perjudicaría el mercado laboral",

            "Las decisiones políticas más importantes deberían ser consultadas a la ciudadanía mediante un referéndum",

            "Las personas del mismo sexo deben tener el derecho de contraer matrimonio",

            "Las corridas de toros deberían prohibirse en todo el territorio español",

            "Los políticos imputados por corrupción política deben dimitir y no poder presentarse a las elecciones por ley",

            "Si un ciudadano no consigue pagar su hipoteca, la entrega de su vivienda debería anular su deuda con el banco",

            "Las personas que más tienen deberían pagar más para asegurar una mayor redistribución de la riqueza en el país",

            "la sanidad pública debería ser gestionada por empresas privadas para asegurar unos servicios de mayor calidad y más eficientes",

            "Todas las cuentas de los partidos políticos deberían estar online a disposición de la ciudadanía (tanto del partido como de los cargos públicos)",

            "Es necesario aumentar la edad de jubilación hasta los 67 años para garantizar la sostenibilidad del sistema público de pensiones ",

            "Es importante limitar el derecho a manifestación para no alterar el orden público",

            "La legislación sobre el aborto debería ser más restrictiva",

            "Para luchar contra el desempleo, las empresas deben tener más facilidades para contratar y despedir a los trabajadores",

            "El medio ambiente debe protegerse aunque ello sea perjudicial para el crecimiento económico",

            "La prostitución debería ser legalizada y regulada",

            "Legalizar la Marihuana, tanto para el consumo particular como para el uso terapeútico"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragmento_pregunta, container, false);
        pregunta = (TextView) view.findViewById(R.id.preg);
        if (getArguments() == null) {
            indice = 0;
        } else {
            indice = getArguments().getInt(INDICE_PREGUNTA);
        }
        if (indice >= preguntas.length) {
            return view;
        }
        Log.i("TEST"," "+ indice);
        pregunta.setText(preguntas[indice]);
        return view;
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
