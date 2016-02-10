package es.ageapps.eleccionesapp.modelo;

import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import es.ageapps.eleccionesapp.R;

/**
 * Created by adricacho on 04/11/15.
 */
public enum Candidato {
    RAJOY, SANCHEZ, RIVERA, IGLESIAS, HERZOG, GARZON;

    public String getNombre() {
        switch (this) {
            case RAJOY:
                return "Mariano Rajoy";
            case SANCHEZ:
                return "Pedro Sánchez";
            case RIVERA:
                return "Albert Rivera";
            case IGLESIAS:
                return "Pablo Iglesias";
            case HERZOG:
                return "Andrés G. Herzog";
            case GARZON:
                return "Alberto Garzón";
        }
        return "";
    }

    public String getPartido() {
        Partido[] partido = Partido.values();

        switch (this) {
            case RAJOY:
                return partido[0].getNombre();
            case SANCHEZ:
                return partido[1].getNombre();
            case RIVERA:
                return partido[2].getNombre();
            case IGLESIAS:
                return partido[3].getNombre();
            case HERZOG:
                return partido[4].getNombre();
            case GARZON:
                return partido[5].getNombre();
        }
        return partido[0].getNombre();
    }

    public int getIdDrawable() {
        int[] fotos_candidatos = {
                R.drawable.mariano_rajoy1, R.drawable.pedro_sanchez1, R.drawable.albert_rivera1,
                R.drawable.pablo_iglesias1, R.drawable.herzog1, R.drawable.alberto_garzon1
        };
        switch (this) {
            case RAJOY:
                return fotos_candidatos[0];
            case SANCHEZ:
                return fotos_candidatos[1];
            case RIVERA:
                return fotos_candidatos[2];
            case IGLESIAS:
                return fotos_candidatos[3];
            case HERZOG:
                return fotos_candidatos[4];
            case GARZON:
                return fotos_candidatos[5];
        }
        return fotos_candidatos[0];
    }

    public Intent getUrlIntent() {

        String url = "";
        switch (this) {
            case RAJOY:
                url = "http://www.pp.es/mariano-rajoy";
                break;
            case SANCHEZ:
                url = "http://www2.psoe.es/conocenos/secretaria-general/";
                break;
            case RIVERA:
                url = "https://www.ciudadanos-cs.org/equipo";
                break;
            case IGLESIAS:
                url = "https://transparencia.podemos.info/perfil/estatal/pablo-iglesias-turrion";
                break;
            case HERZOG:
                url = "http://www.upyd.es/Consejo-de-Direccion";
                break;
            case GARZON:
                url = "https://es.wikipedia.org/wiki/Alberto_Garz%C3%B3n";
                break;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        return i.setData(Uri.parse(url));
    }


}
