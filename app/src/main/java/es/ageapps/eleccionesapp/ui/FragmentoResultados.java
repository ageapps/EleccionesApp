package es.ageapps.eleccionesapp.ui;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
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
 * Created by adricacho on 01/12/15.
 */
public class FragmentoResultados extends Fragment {
    private BarChart barChart;
    private View view;
    private TextView percent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragmento_resultados, container, false);
        barChart = (BarChart) view.findViewById(R.id.chart2);
        percent = (TextView) view.findViewById(R.id.percent_escrut);
        setUpData();
        setHasOptionsMenu(true);

        return view;
    }

    private void setUpBarChart(float[] yData) {

        // apply styling
        barChart.setDescription("");
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setSpaceTop(20f);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);


        ArrayList<String> xVals = new ArrayList<String>();
        String[] partidos = Partido.PP.getPartidos();
        for (int i = 0; i < partidos.length; i++) {
            xVals.add(partidos[i]);
        }

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < xVals.size(); i++) {
            entries.add(new BarEntry(yData[i], i));
        }

        BarDataSet d = new BarDataSet(entries, "Partidos");
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
        d.setHighLightAlpha(255);

        BarData cd = new BarData(xVals, d);
        barChart.setData(cd);
        barChart.invalidate();
        barChart.animateY(1400);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menu_resultados, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.resultados_actualizar:
                if (Utils.hayConexion(view.getContext())) {
                    setUpData();
                } else {
                    Toast.makeText(view.getContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpData() {


        if (!Utils.hayConexion(view.getContext())) {
            Toast.makeText(view.getContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
            return;
        }

        ParseQuery.getQuery("resultados").orderByAscending("porcentaje").setLimit(7).findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    Toast.makeText(view.getContext(), "Error de Red", Toast.LENGTH_SHORT).show();
                    return;
                }
                new DataGetter().execute(list);
            }
        });


    }

    private class DataGetter extends AsyncTask<List<ParseObject>, Integer, float[]> {

        protected ProgressDialog barra;
        protected int porcentage;

        protected void onPreExecute() {
            barra = ProgressDialog.show(view.getContext(), "Cargando", "Se esta cargando el contenido", true);
        }

        @Override
        protected float[] doInBackground(List<ParseObject>... params) {


            float[] yDataBar = {0, 0, 0, 0, 0, 0, 0};

            porcentage = (int) params[0].get(0).getNumber("porcentage");

            for (int i = 0; i < params[0].size(); i++) {
                publishProgress(i);
                switch (params[0].get(i).getString("partido")) {
                    case "PP": {
                        yDataBar[0] = (int) params[0].get(i).get("votos");
                        break;
                    }
                    case "PSOE": {
                        yDataBar[1] = (int) params[0].get(i).get("votos");
                        break;
                    }
                    case "Ciudadanos": {
                        yDataBar[2] = (int) params[0].get(i).get("votos");
                        break;
                    }
                    case "Podemos": {
                        yDataBar[3] = (int) params[0].get(i).get("votos");
                        break;
                    }
                    case "UPyD": {
                        yDataBar[4] = (int) params[0].get(i).get("votos");
                        break;
                    }
                    case "IU": {
                        yDataBar[5] = (int) params[0].get(i).get("votos");
                        break;
                    }
                    case "Otro": {
                        yDataBar[6] = (int) params[0].get(i).get("votos");
                        break;
                    }
                }
            }
            return yDataBar;
        }


        protected void onProgressUpdate(Integer... value) {
            super.onProgressUpdate(value);
            barra.setProgress(value[0]);
        }

        protected void onPostExecute(float[] data) {
            String text;
            if (porcentage == 0) {
                text = "Todavía no hay Resultados";
            } else {
                text = Integer.toString(porcentage) + " % Escrutado";
            }
            percent.setText(text);
            setUpBarChart(data);
            if (barra.isShowing()) {
                barra.dismiss();
            }

        }
    }


}

