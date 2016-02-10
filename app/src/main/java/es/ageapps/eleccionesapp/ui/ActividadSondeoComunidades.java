package es.ageapps.eleccionesapp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.ageapps.eleccionesapp.R;
import es.ageapps.eleccionesapp.modelo.Utils;

public class ActividadSondeoComunidades extends AppCompatActivity {

    protected ProgressDialog barra;

    private ArrayList<BarData> listaViews;
    private ListView listView;
    private boolean girado;
    private Matriz matrix;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_sondeo_comunidades);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_comunidades);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        listView = (ListView) findViewById(R.id.listView1);

        listaViews = new ArrayList<BarData>();
        setUpData();
    }

    private class ChartDataAdapter extends ArrayAdapter<BarData> {


        public ChartDataAdapter(Context context, List<BarData> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            BarData data = getItem(position);

            ViewHolder holder = null;

            if (convertView == null) {

                holder = new ViewHolder();

                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item_barchart, null);
                holder.chart = (BarChart) convertView.findViewById(R.id.chart);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // apply styling
            data.setValueTextColor(Color.BLACK);
            holder.chart.setDescription("");
            holder.chart.setDrawGridBackground(false);

            XAxis xAxis = holder.chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);

            YAxis leftAxis = holder.chart.getAxisLeft();
            leftAxis.setLabelCount(5, false);
            leftAxis.setSpaceTop(15f);

            YAxis rightAxis = holder.chart.getAxisRight();
            rightAxis.setLabelCount(5, false);
            rightAxis.setSpaceTop(15f);

            // set data
            holder.chart.setData(data);

            // do not forget to refresh the chart
//            holder.chart.invalidate();
            holder.chart.animateY(700, Easing.EasingOption.EaseInCubic);

            return convertView;
        }

        private class ViewHolder {

            BarChart chart;
        }
    }


    private BarData generateData(float[] yData, String comunidad) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < yData.length; i++)
            entries.add(new BarEntry(yData[i], i));


        BarDataSet d = new BarDataSet(entries, "Sondeo de " + comunidad);
        d.setBarSpacePercent(20f);
        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(getResources().getColor(R.color.color_pp));
        colors.add(getResources().getColor(R.color.color_psoe));
        colors.add(getResources().getColor(R.color.color_c));
        colors.add(getResources().getColor(R.color.color_podemos));
        colors.add(getResources().getColor(R.color.color_up));
        colors.add(getResources().getColor(R.color.color_iu));
        colors.add(Color.GRAY);

        d.setColors(colors);
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<BarDataSet> sets = new ArrayList<BarDataSet>();
        sets.add(d);

        BarData cd = new BarData(getPartidos(), sets);
        return cd;
    }

    private ArrayList<String> getPartidos() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("PP");
        m.add("PSOE");
        m.add("Cs");
        m.add("Pod");
        m.add("UPyD");
        m.add("IU");
        m.add("Otros");

        return m;
    }

    private void setUpData() {

        barra = ProgressDialog.show(ActividadSondeoComunidades.this, "Cargando",
                "Se esta cargando el contenido", true);
        ParseQuery.getQuery("votos_usuarios").setLimit(145).findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    Toast.makeText(ActividadSondeoComunidades.this, "Error de Red", Toast.LENGTH_SHORT).show();
                    return;
                }
                new DataGetter().execute(list);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private class DataGetter extends AsyncTask<List<ParseObject>, Integer, float[][]> {

        float[][] matriz = new float[19][7];

        private void resetData(float[][] list) {
            for (int j = 0; j < list.length; j++) {
                for (int i = 0; i < list[0].length; i++) {
                    list[j][i] = 0;
                }
            }
        }


        protected void onPreExecute() {
            resetData(matriz);
        }


        @Override
        protected float[][] doInBackground(List<ParseObject>... params) {

            List<ParseObject> yData_and = new ArrayList<ParseObject>();
            List<ParseObject> yData_ara = new ArrayList<ParseObject>();
            List<ParseObject> yData_ast = new ArrayList<ParseObject>();
            List<ParseObject> yData_bal = new ArrayList<ParseObject>();
            List<ParseObject> yData_cana = new ArrayList<ParseObject>();
            List<ParseObject> yData_cant = new ArrayList<ParseObject>();
            List<ParseObject> yData_cast_m = new ArrayList<ParseObject>();
            List<ParseObject> yData_cast_l = new ArrayList<ParseObject>();
            List<ParseObject> yData_cat = new ArrayList<ParseObject>();
            List<ParseObject> yData_ceut = new ArrayList<ParseObject>();
            List<ParseObject> yData_ext = new ArrayList<ParseObject>();
            List<ParseObject> yData_gal = new ArrayList<ParseObject>();
            List<ParseObject> yData_rioj = new ArrayList<ParseObject>();
            List<ParseObject> yData_mad = new ArrayList<ParseObject>();
            List<ParseObject> yData_mel = new ArrayList<ParseObject>();
            List<ParseObject> yData_mur = new ArrayList<ParseObject>();
            List<ParseObject> yData_nav = new ArrayList<ParseObject>();
            List<ParseObject> yData_pvas = new ArrayList<ParseObject>();
            List<ParseObject> yData_val = new ArrayList<ParseObject>();

            Log.i("SONDEOUSUARIO", "" + (params[0].size()));


            for (int i = 0; i < params[0].size(); i++) {
                switch ((String) params[0].get(i).get("comunidad")) {
                    case "Andalucía": {
                        yData_and.add(params[0].get(i));
                        break;
                    }
                    case "Aragón": {
                        yData_ara.add(params[0].get(i));
                        break;
                    }
                    case "Asturias": {
                        yData_ast.add(params[0].get(i));
                        break;

                    }
                    case "Baleares": {
                        yData_bal.add(params[0].get(i));
                        break;

                    }
                    case "Canarias": {
                        yData_cana.add(params[0].get(i));
                        break;

                    }
                    case "Cantabria": {
                        yData_cant.add(params[0].get(i));
                        break;

                    }
                    case "Castilla la Mancha": {
                        yData_cast_m.add(params[0].get(i));
                        break;

                    }
                    case "Castilla y León": {
                        yData_cast_l.add(params[0].get(i));
                        break;

                    }
                    case "Cataluña": {
                        yData_cat.add(params[0].get(i));
                        break;

                    }
                    case "Ceuta": {
                        yData_ceut.add(params[0].get(i));
                        break;

                    }
                    case "Extremadura": {

                        yData_ext.add(params[0].get(i));
                        break;
                    }
                    case "Galicia": {

                        yData_gal.add(params[0].get(i));
                        break;
                    }
                    case "Madrid": {

                        yData_mad.add(params[0].get(i));
                        break;
                    }
                    case "Melilla": {

                        yData_mel.add(params[0].get(i));
                        break;
                    }
                    case "Murcia": {

                        yData_mur.add(params[0].get(i));
                        break;
                    }
                    case "Navarra": {

                        yData_nav.add(params[0].get(i));
                        break;
                    }
                    case "La Rioja": {

                        yData_rioj.add(params[0].get(i));
                        break;
                    }
                    case "País Vasco": {

                        yData_pvas.add(params[0].get(i));
                        break;
                    }
                    case "Valencia": {

                        yData_val.add(params[0].get(i));
                        break;
                    }
                    default:
                        break;


                }

            }
            List<List<ParseObject>> comunidades = new ArrayList<List<ParseObject>>();
            comunidades.add(yData_and);
            comunidades.add((yData_ara));
            comunidades.add((yData_ast));
            comunidades.add((yData_bal));
            comunidades.add((yData_cana));
            comunidades.add((yData_cant));
            comunidades.add((yData_cast_m));
            comunidades.add((yData_cast_l));
            comunidades.add((yData_cat));
            comunidades.add((yData_ceut));
            comunidades.add((yData_ext));
            comunidades.add((yData_gal));
            comunidades.add((yData_mad));
            comunidades.add((yData_mel));
            comunidades.add((yData_mur));
            comunidades.add((yData_nav));
            comunidades.add((yData_rioj));
            comunidades.add((yData_pvas));
            comunidades.add((yData_val));


            for (int i = 0; i < comunidades.size(); i++) {
                List<ParseObject> comunidad = comunidades.get(i);


                for (int j = 0; j < comunidad.size(); j++) {
                    switch ((String) comunidad.get(j).get("partido")) {
                        case "PP": {
                            matriz[i][0] = (int) comunidad.get(j).getNumber("num");
                            break;
                        }
                        case "PSOE": {
                            matriz[i][1] = (int) comunidad.get(j).getNumber("num");
                            break;
                        }
                        case "Ciudadanos": {
                            matriz[i][2] = (int) comunidad.get(j).getNumber("num");
                            break;
                        }
                        case "Podemos": {
                            matriz[i][3] = (int) comunidad.get(j).getNumber("num");
                            break;
                        }
                        case "UPyD": {
                            matriz[i][4] = (int) comunidad.get(j).getNumber("num");
                            break;
                        }
                        case "IU": {
                            matriz[i][5] = (int) comunidad.get(j).getNumber("num");
                            break;
                        }
                        case "Otro": {
                            matriz[i][6] = (int) comunidad.get(j).getNumber("num");
                            break;
                        }
                    }
                }
            }
            return matriz;
        }


        protected void onPostExecute(float[][] data) {
            ArrayList<BarData> list = new ArrayList<BarData>();
            matrix = new Matriz(data);
            // 20 items
            for (int i = 0; i < data.length; i++) {
                list.add(generateData(getPorcentages(data[i]), ActividadPrincipal.getComunidades()[i + 1]));// i + 1 porque el primer elemento del array no es comunidad
            }
            ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
            listView.setAdapter(cda);
            if (barra.isShowing()) {
                barra.dismiss();
            }
        }
    }

    public float[] getPorcentages(float[] lista) {

        float cuenta = 0;

        for (int j = 0; j < lista.length; j++) {
            cuenta = lista[j] + cuenta;
        }
        for (int i = 0; i < lista.length; i++) {
            lista[i] = 100 * lista[i] / cuenta;
        }
        return lista;

    }


    public class Matriz implements Serializable {

        float[][] matriz;

        public Matriz(float[][] matriz) {
            this.matriz = matriz;
        }

        private float[][] getMatriz() {
            return matriz;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_noticias, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.noticias_actualizar: {
                if (!Utils.hayConexion(ActividadSondeoComunidades.this)) {
                    Toast.makeText(ActividadSondeoComunidades.this, "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                    break;
                }
                setUpData();
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
