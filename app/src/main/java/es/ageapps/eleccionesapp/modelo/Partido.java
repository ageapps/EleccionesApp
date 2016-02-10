package es.ageapps.eleccionesapp.modelo;

import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import es.ageapps.eleccionesapp.R;

/**
 * Created by adricacho on 04/11/15.
 */
public enum Partido {
    PP, PSOE, Cs, PODEMOS, UPyD, IU;

    public String getNombre() {
        switch (this) {
            case PP:
                return "\n\nPartido \nPopular";
            case PSOE:
                return "Partido \nSocialista \nObrero\nEspañol";
            case Cs:
                return "Ciudadanos";
            case PODEMOS:
                return "Podemos";
            case UPyD:
                return "\nUPyD";
            case IU:
                return "Izquierda \nUnida";
        }
        return "";
    }
    public int[] getValoresTest(){
        switch (this) {
            case PP:
                int[] values1 = {1,1,3,1,3,3,3,1,3,1,3,1,3,3,3,3,1,2,1,1,3,1,3,1,3};
                return values1;
            case PSOE:
                int[] values2 = {1,3,1,3,1,3,1,3,1,1,3,3,2,3,1,1,3,1,3,3,1,3,1,2,1};
                return values2;
            case Cs:
                int[] values3 = {1,1,2,1,1,1,3,3,1,1,3,3,3,1,3,1,1,1,1,3,3,2,2,1,1,};
                return values3;
            case PODEMOS:
                int[] values4 = {1,1,1,3,1,1,1,3,1,3,1,1,2,1,1,1,3,1,3,3,3,3,1,2,1};
                return values4;
            case UPyD:
                int[] values5 = {1,1,1,3,1,1,1,3,1,3,2,1,3,1,1,1,2,1,3,3,3,3,3,1,1};
                return values5;
            case IU:
                int[] values6 = {3,1,1,3,1,1,1,3,1,3,1,1,1,1,1,1,3,1,3,3,1,3,1,3,1};
                return values6;
        }
        return null;
    }

    private List<Programa> addProgramas() {
        List<Programa> lista = new ArrayList<Programa>();
        Programa[] programas = Programa.values();
        lista.add(programas[0]);
        lista.add(programas[1]);
        lista.add(programas[2]);
        lista.add(programas[3]);
        lista.add(programas[4]);
        lista.add(programas[5]);
        lista.add(programas[6]);
        lista.add(programas[7]);
        lista.add(programas[8]);
        return lista;
    }

    public List<Programa> getProgramas() {
        return addProgramas();
    }


    public int[] getLogos() {
        int[] logos = {
                R.drawable.logo_pp, R.drawable.logo_psoe, R.drawable.logo_c,
                R.drawable.logo_podemos, R.drawable.logo_upyd, R.drawable.logo_iu
        };
        return logos;
    }
    public int[] getLogosGrandes() {
        int[] fotos_candidatos = {
                R.drawable.logo_pp_grande, R.drawable.logo_ps_grande, R.drawable.logo_cs_grande,
                R.drawable.logo_podemos_grande, R.drawable.logo_up_grande, R.drawable.logo_iu_grande
        };
        return fotos_candidatos;
    }

    public Intent getUrlIntent() {

        String url = "";
        switch (this) {
            case PP:
                url = "http://www.pp.es/temas";
                break;
            case PSOE:
                url = "http://www2.psoe.es/propuestas/";
                break;
            case Cs:
                url = "https://www.ciudadanos-cs.org/nuestras-ideas";
                break;
            case PODEMOS:
                url = "http://podemos.info/propuestas/";
                break;
            case UPyD:
                url = "http://www.upyd.es/Que-defendemos";
                break;
            case IU:
                url = "http://www.izquierda-unida.es/areas";
                break;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        return i.setData(Uri.parse(url));
    }

    public String[] getPartidos() {
        String[] xData = {"PP", "PSOE", "Ciudadanos", "Podemos", "UPyD", "IU", "Otros"};
        return xData;
    }

}
