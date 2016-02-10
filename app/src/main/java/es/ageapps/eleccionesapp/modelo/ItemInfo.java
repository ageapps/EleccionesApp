package es.ageapps.eleccionesapp.modelo;

import android.content.Intent;
import android.net.Uri;

import es.ageapps.eleccionesapp.R;

/**
 * Created by adricacho on 22/11/15.
 */
public enum ItemInfo {
     CONGRESO, SENADO,COMO, LOCAL,CIFRAS,DUDAS,PRESIDENCIA;

    public String getNombre() {
        switch (this) {
            case COMO:
                return "Cómo Votar";
            case CONGRESO:
                return "Candidatos al Congreso";
            case SENADO:
                return "Candidatos al Senado";
            case LOCAL:
                return "Encuentra tu Local y Mesa Electoral";
            case CIFRAS:
                return "Las Elecciones en Cifras";
            case DUDAS:
                return "Resuelva sus Dudas";
            case PRESIDENCIA:
                return "Candidatos a la Presidencia";
        }
        return "";
    }

    public String getUrl() {
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
            case CIFRAS:
                url = "http://generales2015.interior.es/es/las-elecciones-en-cifras/";
                break;
            case DUDAS:
                url = "http://generales2015.interior.es/es/resuelva-sus-dudas/las-cortes-generales/";
                break;
        }


        return url;
    }
    public int getIdDrawable() {
        int[] fotos_info = {
               R.drawable.congreso, R.drawable.senado,  R.drawable.como,
                R.drawable.local,R.drawable.numeros,R.drawable.dudas,R.drawable.presidencia
        };
        switch (this) {
            case COMO:
                return fotos_info[2];
            case CONGRESO:
                return fotos_info[0];
            case SENADO:
                return fotos_info[1];
            case LOCAL:
                return fotos_info[3];
            case CIFRAS:
                return fotos_info[4];
            case DUDAS:
                return fotos_info[5];
            case PRESIDENCIA:
                return fotos_info[6];
        }
        return fotos_info[0];
    }
}
