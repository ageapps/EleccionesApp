package es.ageapps.eleccionesapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
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
 * Created by adricacho on 16/11/15.
 */
public class FragmentoSodeoUsuario extends Fragment {
    private String TAG = " SONDEOS";
    private View view;
    private PieChart pie;

    private float[] yDataSondeo = {0, 0, 0, 0, 0, 0, 0};
    private float[] yDataAntiSondeo = {0, 20, 10, 10, 10, 20, 20};
    private float[] yDataNoConex = {0, 0, 0, 0, 0, 0, 0};
    private Handler mUiHandler = new Handler();
    private FloatingActionButton fab_sondeo;
    private FloatingActionButton fab_antisondeo;
    private FloatingActionButton fab_comunidades;
    private FloatingActionButton fab_votos;
    private boolean isSondeo;
    private String[] xData = Partido.PP.getPartidos();
    private int current;
    private TextView text_cuenta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragmento_sondeo_usuarios, container, false);
        setHasOptionsMenu(true);
        pie = (PieChart) view.findViewById(R.id.chart);

        final FloatingActionMenu menu2 = (FloatingActionMenu) view.findViewById(R.id.menu2);

        menu2.hideMenuButton(false);

        int delay = 400;
        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                menu2.showMenuButton(true);
            }
        }, delay);

        text_cuenta = (TextView) view.findViewById(R.id.cuenta);


        fab_sondeo = (FloatingActionButton) view.findViewById(R.id.fab_sondeo);
        fab_antisondeo = (FloatingActionButton) view.findViewById(R.id.fab_antisondeo);
        fab_comunidades = (FloatingActionButton) view.findViewById(R.id.fab_comunidades);
        fab_votos = (FloatingActionButton) view.findViewById(R.id.fab_votos);


        fab_sondeo.setOnClickListener(clickListener);
        fab_antisondeo.setOnClickListener(clickListener);
        fab_comunidades.setOnClickListener(clickListener);
        fab_votos.setOnClickListener(clickListener);


        pie.setDescription("");
        pie.setUsePercentValues(true);
        pie.setExtraOffsets(5, 10, 5, 5);
        pie.setDragDecelerationFrictionCoef(0.95f);

        // Parse.initialize(view.getContext(), "a62OWoPm0nWef8SoAXHGQpRQb0xmteRmjzIRrxqL", "PkeFiNp6wgI2XG0imwEApwHcBsRd67QNWya5hEeO");


        pie.setDrawHoleEnabled(true);
        pie.setHoleColorTransparent(true);

        pie.setTransparentCircleColor(Color.WHITE);
        pie.setTransparentCircleAlpha(110);

        pie.setDrawSliceText(false);
        pie.setHoleRadius(58f);
        pie.setTransparentCircleRadius(61f);
        pie.setHighlightPerTapEnabled(true);
        pie.setCenterText(generateCenterText("Sondeo\n 2015", 6));
        current = R.id.fab_sondeo;

        pie.setRotationAngle(0);
        // enable rotation of the chart by touch
        pie.setRotationEnabled(true);


        pie.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null)
                    return;
                Toast.makeText(view.getContext(), xData[e.getXIndex()], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        setUpDataSondeo(true, true);//cuento como refresco para que acceda a los datos
        Legend l = pie.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(4);
        l.setYEntrySpace(3);
        return view;
    }


    private void addData(float[] yData) {

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; i++)
            yVals.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(getResources().getColor(R.color.color_pp));
        colors.add(getResources().getColor(R.color.color_psoe));
        colors.add(getResources().getColor(R.color.color_c));
        colors.add(getResources().getColor(R.color.color_podemos));
        colors.add(getResources().getColor(R.color.color_up));
        colors.add(getResources().getColor(R.color.color_iu));
        colors.add(Color.GRAY);
        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueTextColor(Color.WHITE);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);

        pie.setData(data);
        pie.highlightValues(null);
        pie.invalidate();

    }


    private void setUpDataSondeo(boolean isSondeo, boolean isRefresco) {

        this.isSondeo = isSondeo;

        if (!Utils.hayConexion(view.getContext())) {
            addData(yDataNoConex);
            pie.setCenterText(generateCenterText("No hay datos\n Compruebe su conexión a Internet", 13));
            Toast.makeText(view.getContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isRefresco & isSondeo) {
            addData(yDataSondeo);
            pie.animateY(1400);
            return;
        }
        if (!isRefresco & !isSondeo & yDataAntiSondeo[0] != 0) {
            addData(yDataAntiSondeo);
            pie.animateY(1400);
            return;
        }

        String text;
        final String query_name;
        if (isSondeo) {
            text = "votos_usuarios";
        } else {
            text = "anti_votos_usuarios";
        }

        query_name = text;

        ParseQuery.getQuery(query_name).setLimit(141).findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    Toast.makeText(view.getContext(), "Error de Red", Toast.LENGTH_SHORT).show();
                    addData(yDataNoConex);
                    return;
                }
                Log.i(TAG, "" + list.size());
                Log.i(TAG, "" + (int) list.get(1).get("num"));
                new DataGetter().execute(list);
            }
        });


    }

    private class DataGetter extends AsyncTask<List<ParseObject>, Integer, float[]> {

        protected ProgressDialog barra;

        protected void onPreExecute() {
            barra = ProgressDialog.show(view.getContext(), "Cargando",
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
            if (isSondeo) {
                yDataSondeo = data;
            } else {
                yDataAntiSondeo = data;
            }

            addData(getPorcentages(data));
            if (barra.isShowing()) {
                barra.dismiss();
            }
            pie.animateY(1400);
        }

        public float[] getPorcentages(float[] lista) {

            int cuenta = 0;

            for (int j = 0; j < lista.length; j++) {
                cuenta = (int)lista[j] + cuenta;
            }
            for (int i = 0; i < lista.length; i++) {
                lista[i] = 100 * lista[i] / cuenta;
            }
            text_cuenta.setText("Encuesta basada en " + cuenta + " votos");
            return lista;

        }

    }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            current = v.getId();
            Log.i(TAG,"" + current);
            refreshData(false, false, false);
        }


    };


    private void refreshData(boolean noComunidades, boolean isRefresh,boolean noVotos) {
        switch (current) {
            case R.id.fab_sondeo:
                pie.setCenterText(generateCenterText("Sondeo\n 2015", 6));
                setUpDataSondeo(true, isRefresh);
                break;

            case R.id.fab_antisondeo:
                pie.setCenterText(generateCenterText("AntiSondeo\n 2015", 10));
                setUpDataSondeo(false, isRefresh);
                break;

            case R.id.fab_comunidades:
                if (Utils.hayConexion(view.getContext()) && !noComunidades) {
                    if (isSondeo) {
                        current = R.id.fab_sondeo;
                    } else {
                        current = R.id.fab_antisondeo;
                    }
                    view.getContext().startActivity(new Intent(view.getContext(), ActividadSondeoComunidades.class));
                    return;
                }
                Toast.makeText(view.getContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab_votos:
                Log.i(TAG,"" + "votos");
                if (Utils.hayConexion(view.getContext()) && !noVotos) {
                    if (isSondeo) {
                        current = R.id.fab_sondeo;
                    } else {
                        current = R.id.fab_antisondeo;
                    }
                    view.getContext().startActivity(new Intent(view.getContext(), ActividadVotosUsuarios.class));
                    return;
                }
                Toast.makeText(view.getContext(), "No hay conexión a Internet", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private SpannableString generateCenterText(String text, int n) {
        SpannableString s = new SpannableString(text);
        s.setSpan(new RelativeSizeSpan(1.5f), 0, n, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), n, s.length(), 0);
        s.setSpan(new RelativeSizeSpan(1f), n, s.length(), 0);
        return s;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menu_sondeos, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sondeos_actualizar: {
                refreshData(true, true,true);
                break;
            }
            case R.id.sondeo: {
                boolean hayConexion = Utils.tengoConexion(view.getContext(), "Necesita conexión para acceder poder realizar el sondeo");
                if (hayConexion) {
                    startActivity(new Intent(view.getContext(), ActividadSondeo.class));
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}


