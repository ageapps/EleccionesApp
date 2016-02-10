package es.ageapps.eleccionesapp.modelo;

import es.ageapps.eleccionesapp.R;

/**
 * Created by adricacho on 04/11/15.
 */

public enum Programa {
    justicia, cultura, economia, empleo, politica, educacion, sanidad, investigacion, espana;

    public String getNombre() {
        switch (this) {
            case justicia:
                return "Justicia";
            case cultura:
                return "Cultura";
            case economia:
                return "Economía";
            case empleo:
                return "Empleo";
            case politica:
                return "Politíca \nSocial";
            case sanidad:
                return "Sanidad";
            case investigacion:
                return "Investigación";
            case espana:
                return "España";
            case educacion:
                return "Educación";
        }
        return this.toString();
    }

    public int getIdDrawable1() {

        int[] FOTOS_PROGRAMAS1 = {
                R.drawable.justicia1, R.drawable.cultura1, R.drawable.economia1,
                R.drawable.empleo1, R.drawable.sociedad1, R.drawable.educacion1, R.drawable.sanidad1,
                R.drawable.investigacion1, R.drawable.espana1
        };
        switch (this) {
            case justicia:
                return FOTOS_PROGRAMAS1[0];
            case cultura:
                return FOTOS_PROGRAMAS1[1];
            case economia:
                return FOTOS_PROGRAMAS1[2];
            case empleo:
                return FOTOS_PROGRAMAS1[3];
            case politica:
                return FOTOS_PROGRAMAS1[4];
            case educacion:
                return FOTOS_PROGRAMAS1[5];
            case sanidad:
                return FOTOS_PROGRAMAS1[6];
            case investigacion:
                return FOTOS_PROGRAMAS1[7];
            case espana:
                return FOTOS_PROGRAMAS1[8];

        }
        return FOTOS_PROGRAMAS1[0];
    }

    public int getIdDrawable2() {

        int[] FOTOS_PROGRAMAS2 = {
                R.drawable.justicia2, R.drawable.cultura2, R.drawable.economia2,
                R.drawable.empleo2, R.drawable.sociedad2,R.drawable.educacion2, R.drawable.sanidad2,
                R.drawable.investigacion2, R.drawable.espana2
        };
        switch (this) {
            case justicia:
                return FOTOS_PROGRAMAS2[0];
            case cultura:
                return FOTOS_PROGRAMAS2[1];
            case economia:
                return FOTOS_PROGRAMAS2[2];
            case empleo:
                return FOTOS_PROGRAMAS2[3];
            case politica:
                return FOTOS_PROGRAMAS2[4];
            case educacion:
                return FOTOS_PROGRAMAS2[5];
            case sanidad:
                return FOTOS_PROGRAMAS2[6];
            case investigacion:
                return FOTOS_PROGRAMAS2[7];
            case espana:
                return FOTOS_PROGRAMAS2[8];

        }
        return FOTOS_PROGRAMAS2[0];
    }
}
