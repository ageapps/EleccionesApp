package es.ageapps.eleccionesapp.modelo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;

import es.ageapps.eleccionesapp.ui.ActividadPrincipal;

/**
 * Created by adricacho on 13/11/15.
 */
public class Utils {

    public static int votos;
    public static boolean signed;
    //                     pp pso cs p up iu  ot
    static float[] yData_and = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_ara = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_ast = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_bal = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_cana = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_cant = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_cast_m = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_cast_l = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_cat = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_ceut = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_ext = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_gal = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_rioj = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_mad = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_mel = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_mur = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_nav = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_pvas = {0, 0, 0, 0, 0, 0, 0};
    static float[] yData_val = {0, 0, 0, 0, 0, 0, 0};

    public static boolean hayConexion2() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1    www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal == 0);
            if (reachable) {
                System.out.println("Internet access");
                return reachable;
            } else {
                System.out.println("No Internet access");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public static boolean hayConexion(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }


    public static boolean tengoConexion(Context context, String texto) {

        if (!hayConexion(context)) {
            AlertDialog.Builder b = new AlertDialog.Builder(context);
            b.setTitle("Error de Red");
            b.setMessage(texto + ". \n" +
                    "Porfavor compruebe su conexión a internet");
            b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            b.show();
            return false;
        }
        return true;
    }



}

