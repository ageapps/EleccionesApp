package es.ageapps.eleccionesapp.ui;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import es.ageapps.eleccionesapp.R;
import es.ageapps.eleccionesapp.modelo.Partido;
import es.ageapps.eleccionesapp.modelo.Utils;

/**
 * Created by adricacho on 06/12/15.
 */
public class ActividadVotosUsuarios extends AppCompatActivity {

    protected ProgressDialog barra;
    protected HorizontalBarChart mChart;
    private float[] yDataNoConex = {0, 0, 0, 0, 0, 0, 0};
    private String[] xData = Partido.PP.getPartidos();
    private TextView text_cuenta;
    public static final String RESULTADOS_TEST
            = "RESULTADOS_TEST";
    private Boolean test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getIntArrayExtra(RESULTADOS_TEST) != null) {
            setTitle("Resultados del Test");
            int[] list = getIntent().getIntArrayExtra(RESULTADOS_TEST);
            String[] xData = {"PP", "PSOE", "Ciudadanos", "Podemos", "UPyD", "IU"};
            this.xData = xData;
            setContentView(R.layout.actividad_resultado_test);
            new DataGetterTest().execute(list);
            test = true;
           /* String s = "{";
            for (int i = 0; i< list.length;i++){
                s = s + list[i] + ",";
            }
            Log.i("TESTVOTOS", s+"}" );*/
        } else {
            test = false;
            setContentView(R.layout.actividad_votos_usuarios);

        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_votos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mChart = (HorizontalBarChart) findViewById(R.id.chart);
        mChart.setDrawBarShadow(false);

        mChart.setDrawValueAboveBar(true);
        text_cuenta = (TextView) findViewById(R.id.cuenta);

        mChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        XAxis xl = mChart.getXAxis();
        xl.setDrawLabels(true);

        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);

        YAxis yl = mChart.getAxisLeft();
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setDrawLabels(false);

        YAxis yr = mChart.getAxisRight();
        yr.setDrawAxisLine(false);
        yr.setDrawGridLines(false);
        yr.setDrawLabels(false);
//        yr.setInverted(true);

        mChart.animateY(2500);
        mChart.getLegend().setEnabled(false);
        if (!test) {
            setUpDataVotos();
        }
    }


    private void addData(float[] yData) {

        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();

        for (int i = 0; i < yData.length; i++)
            yVals.add(new BarEntry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < yData.length; i++)
            xVals.add(xData[i]);

        BarDataSet dataSet = new BarDataSet(yVals, "");
        dataSet.setValueTextSize(10f);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(getResources().getColor(R.color.color_pp));
        colors.add(getResources().getColor(R.color.color_psoe));
        colors.add(getResources().getColor(R.color.color_c));
        colors.add(getResources().getColor(R.color.color_podemos));
        colors.add(getResources().getColor(R.color.color_up));
        colors.add(getResources().getColor(R.color.color_iu));
        colors.add(Color.GRAY);
        dataSet.setColors(colors);

        BarData data = new BarData(xVals, dataSet);
        if (test) {
            data.setValueTextSize(11f);
            data.setValueFormatter(new PercentFormatter());
        }

        mChart.setData(data);
        mChart.invalidate();

    }

    private void setUpDataVotos() {
        if (!Utils.hayConexion(ActividadVotosUsuarios.this)) {
            addData(yDataNoConex);
            Toast.makeText(ActividadVotosUsuarios.this, "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
            return;
        }
        ParseQuery.getQuery("votos_usuarios").setLimit(145).findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    Toast.makeText(ActividadVotosUsuarios.this, "Error de Red", Toast.LENGTH_SHORT).show();
                    return;
                }
                new DataGetterVotos().execute(list);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private class DataGetterVotos extends AsyncTask<List<ParseObject>, Integer, float[]> {

        protected ProgressDialog barra;

        protected void onPreExecute() {
            barra = ProgressDialog.show(ActividadVotosUsuarios.this, "Cargando",
                    "Se esta cargando el contenido", true);
        }

        @Override
        protected float[] doInBackground(List<ParseObject>... params) {

            float[] yData = {0, 0, 0, 0, 0, 0, 0};

            for (int i = 0; i < params[0].size(); i++) {
                publishProgress(i);
                switch (params[0].get(i).getString("partido")) {
                    case "PP": {
                        yData[0] = yData[0] + (int) params[0].get(i).get("num");
                        break;
                    }
                    case "PSOE": {
                        yData[1] = yData[1] + (int) params[0].get(i).getNumber("num");
                        break;
                    }
                    case "Ciudadanos": {
                        yData[2] = yData[2] + (int) params[0].get(i).getNumber("num");
                        break;
                    }
                    case "Podemos": {
                        yData[3] = yData[3] + (int) params[0].get(i).getNumber("num");
                        break;
                    }
                    case "UPyD": {
                        yData[4] = yData[4] + (int) params[0].get(i).getNumber("num");
                        break;
                    }
                    case "IU": {
                        yData[5] = yData[5] + (int) params[0].get(i).getNumber("num");
                        break;
                    }
                    case "Otro": {
                        yData[6] = yData[6] + (int) params[0].get(i).getNumber("num");
                        break;
                    }
                }

            }
            return yData;
        }


        protected void onProgressUpdate(Integer... value) {
            super.onProgressUpdate(value);
            barra.setProgress(value[0]);
        }

        protected void onPostExecute(float[] data) {
            mChart.setDescription("Un Total de " + getCuenta(data) + " votos");
            addData(data);
            if (barra.isShowing()) {
                barra.dismiss();
            }
            mChart.animateY(1400);
        }

        public int getCuenta(float[] lista) {

            int cuenta = 0;

            for (int j = 0; j < lista.length; j++) {
                cuenta = (int) lista[j] + cuenta;
            }
            return cuenta;

        }


    }

    private class DataGetterTest extends AsyncTask<int[], Integer, float[]> {

        protected ProgressDialog barra;

        protected void onPreExecute() {
            barra = ProgressDialog.show(ActividadVotosUsuarios.this, "Cargando",
                    "Se estan procesando los resultados", true);
        }

        @Override
        protected float[] doInBackground(int[]... params) {

            float[] yData = {0, 0, 0, 0, 0, 0};

            for (int i = 0; i < params[0].length; i++) {
                publishProgress(i);
                if (params[0][i] == Partido.PP.getValoresTest()[i]) {
                    yData[0]++;
                }
                if (params[0][i] == Partido.PSOE.getValoresTest()[i]) {
                    yData[1]++;
                }
                if (params[0][i] == Partido.PODEMOS.getValoresTest()[i]) {
                    yData[2]++;
                }
                if (params[0][i] == Partido.Cs.getValoresTest()[i]) {
                    yData[3]++;
                }

                if (params[0][i] == Partido.UPyD.getValoresTest()[i]) {
                    yData[4]++;
                }
                if (params[0][i] == Partido.IU.getValoresTest()[i]) {
                    yData[5]++;
                }
            }
            return yData;
        }


        protected void onProgressUpdate(Integer... value) {
            super.onProgressUpdate(value);
            barra.setProgress(value[0]);
        }

        protected void onPostExecute(float[] data) {
            mChart.setDescription("");
            ((TextView) findViewById(R.id.partido_test)).setText("Eres más afín al partido : " + xData[getMasAfin(data)]);
            ((ImageView) findViewById(R.id.foto_test)).setImageDrawable(getResources().getDrawable(Partido.PODEMOS.getLogosGrandes()[getMasAfin(data)]));
            addData(getPorcentages(data));
            if (barra.isShowing()) {
                barra.dismiss();
            }
            mChart.animateY(1400);
        }

        public int getMasAfin(float[] data) {
            int indice = 0;
            float mayor = 0;
            for (int j = 0; j < data.length; j++) {
                if (mayor < data[j]) {
                    mayor = data[j];
                    indice = j;
                }
            }
            return indice;
        }
        public float[] getPorcentages(float[] lista) {

            int cuenta = 0;

            for (int j = 0; j < lista.length; j++) {
                cuenta = (int)lista[j] + cuenta;
            }
            for (int i = 0; i < lista.length; i++) {
                lista[i] = 100 * lista[i] / cuenta;
            }
            return lista;

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getIntent().getIntArrayExtra(RESULTADOS_TEST) != null) {
            getMenuInflater().inflate(R.menu.menu_vacio, menu);
            return true;
        }
        getMenuInflater().inflate(R.menu.menu_noticias, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.noticias_actualizar: {
                if (!Utils.hayConexion(ActividadVotosUsuarios.this)) {
                    Toast.makeText(ActividadVotosUsuarios.this, "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                    break;
                }
                setUpDataVotos();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }


}
