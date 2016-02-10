package es.ageapps.eleccionesapp.modelo;

import android.content.Intent;
import android.net.Uri;

import es.ageapps.eleccionesapp.R;

/**
 * Created by adricacho on 22/11/15.
 */
public enum ItemVota {
    COMO, CONGRESO, SENADO, LOCAL;

    public String getNombre() {
        switch (this) {
            case COMO:
                return "\nCómo Votar";
            case CONGRESO:
                return "Candidatos\nal Congreso";
            case SENADO:
                return "\nCandidatos \nal Senado";
            case LOCAL:
                return "Encuentra tu Local y Mesa Electoral";
        }
        return "";
    }

    public Intent getUrlIntent() {
        String url = "";

        switch (this) {
            case COMO:
                url = "http://generales2015.interior.es/es/como-votar/voto-presencial/";
                break;
            case CONGRESO:
                url = "http://generales2015.interior.es/es/formaciones-politicas/congreso/presentadas/";
                break;
            case SENADO:
                url = "http://generales2015.interior.es/es/formaciones-politicas/senado/presentadas/";
                break;
            case LOCAL:
                url = "https://sede.ine.gob.es/ss/Satellite?c=SETramite_C&cid=1254735541845&lang=es_ES&p=1254734719739&pagename=SedeElectronica%2FSELayout";
                break;
        }


        Intent i = new Intent(Intent.ACTION_VIEW);
        return i.setData(Uri.parse(url));
    }
    public int getIdDrawable() {
        int[] fotos_candidatos = {
                R.drawable.como, R.drawable.congreso, R.drawable.senado,
                R.drawable.local
        };
        switch (this) {
            case COMO:
                return fotos_candidatos[0];
            case CONGRESO:
                return fotos_candidatos[1];
            case SENADO:
                return fotos_candidatos[2];
            case LOCAL:
                return fotos_candidatos[3];
        }
        return fotos_candidatos[0];
    }
}
