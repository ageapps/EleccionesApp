package es.ageapps.eleccionesapp.ui;

/**
 * Created by adricacho on 11/11/15.
 */


import android.graphics.Color;
import android.os.Bundle;
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

import es.ageapps.eleccionesapp.R;

import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;


/**
 * Fragmento que representa el contenido de cada pestaña dentro de la sección "Sondeos"
 */
public class FragmentoSondeoOficial extends Fragment {
    private String TAG = " SONDEOS";
    private View view;
    private PieChart pie;
    private boolean isOficial;

    private float[] yData = {27.4f, 20.6f, 21.3f, 14.3f, 0.2f, 4.3f, 11.9f};
    private String[] xData = {"PP", "PSOE", "C's", "Podemos", "UPyD", "IU", "Otros"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragmento_sondeo_oficial, container, false);
        setHasOptionsMenu(true);
        pie = (PieChart) view.findViewById(R.id.chart);
        // Create the InterstitialAd and set the adUnitId.

        pie.setDescription("Fuente : http://www.tnsglobal.es - Noviembre");
        pie.setUsePercentValues(true);
        pie.setExtraOffsets(5, 10, 5, 5);
        pie.setDragDecelerationFrictionCoef(0.95f);

        pie.setCenterText(generateCenterText());

        pie.setDrawHoleEnabled(true);
        pie.setHoleColorTransparent(true);

        pie.setTransparentCircleColor(Color.WHITE);
        pie.setTransparentCircleAlpha(110);

        pie.setDrawSliceText(false);
        pie.setHoleRadius(58f);
        pie.setTransparentCircleRadius(61f);
        pie.setHighlightPerTapEnabled(true);


        pie.setRotationAngle(0);
        // enable rotation of the chart by touch
        pie.setRotationEnabled(true);


        pie.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null)
                    return;
                Toast.makeText(view.getContext(), xData[e.getXIndex()], Toast.LENGTH_SHORT).show();
                Log.i("Chart", "touched" + xData[e.getXIndex()]);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        addData();

        pie.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = pie.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(4);
        l.setYEntrySpace(3);
        return view;
    }


    private void addData() {

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; i++)
            yVals.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        Log.i("Chart", "adding Data");
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menu_vacio, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        Log.i(TAG, "menu pulsado");
        return super.onOptionsItemSelected(item);
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Sondeos\nOficiales 2015");
        s.setSpan(new RelativeSizeSpan(2f), 0, 8, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length(), 0);
        s.setSpan(new RelativeSizeSpan(1f), 8, s.length(), 0);
        return s;
    }
}

