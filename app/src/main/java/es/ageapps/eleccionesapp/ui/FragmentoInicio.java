package es.ageapps.eleccionesapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ScrollView;
import android.widget.TextView;


import com.adindk.euafni261961.AdConfig;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import es.ageapps.eleccionesapp.R;

/**
 * Created by adricacho on 06/11/15.
 */
public class FragmentoInicio extends Fragment implements OnDateSelectedListener {

    private MaterialCalendarView widget;
    private TextView textView;
    private TextView descripcion;

    private TextView dias_tex;
    private TextView horas_tex;
    private TextView minutos_tex;
    private TextView segundos_tex;
    private TextView titulo;
    private ScrollView scrollView;
    private View view;
    private List<Date> dias = new ArrayList<Date>();
    private List<String> descripciones = new ArrayList<String>();
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();


    public FragmentoInicio() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmento_inicio, container, false);

        dias_tex = (TextView) view.findViewById(R.id.text_dias);
        horas_tex = (TextView) view.findViewById(R.id.text_horas);
        minutos_tex = (TextView) view.findViewById(R.id.text_minutos);
        segundos_tex = (TextView) view.findViewById(R.id.text_segundos);
        titulo = (TextView) view.findViewById(R.id.titulo_contador);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);

        widget = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        textView = (TextView) view.findViewById(R.id.textView_calendar);
        descripcion = (TextView) view.findViewById(R.id.textView_calendar_description);


        widget.setOnDateChangedListener(this);

        //Setup initial text
        textView.setText(getSelectedDatesString());


        Date currentDate = new Date(System.currentTimeMillis());
        GregorianCalendar fecha = new GregorianCalendar(2015, 11, 20);
        long time = fecha.getTime().getTime() - currentDate.getTime();

        new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                dias_tex.setText(Long.toString(TimeUnit.MILLISECONDS.toDays(millisUntilFinished)));
                horas_tex.setText(Long.toString(TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished))));
                minutos_tex.setText(Long.toString(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))));
                segundos_tex.setText(Long.toString(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                titulo.setText("Hoy son las Elecciones Generales 2015!");
                dias_tex.setText("Hoy hay que Votar!");
                dias_tex.setTextSize(40);
                TextView tex_dias = (TextView) view.findViewById(R.id.titulo_dias);
                tex_dias.setText("");

            }
        }.start();

        setUpFechas();


        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());

        setHasOptionsMenu(true);
        return view;
    }

    public void setUpFechas() {
        GregorianCalendar fecha1 = new GregorianCalendar(2015, 10, 6);
        dias.add(fecha1.getTime());
        descripciones.add("Fecha tope para que los partidos que quieren presentarse en coalición con otras formaciones lo comuniquen a las Juntas electorales correspondientes.");
        GregorianCalendar fecha2 = new GregorianCalendar(2015, 10, 11);
        dias.add(fecha2.getTime());
        descripciones.add("Comienza el plazo para presentar las candidaturas ante la Junta electora");
        GregorianCalendar fecha3 = new GregorianCalendar(2015, 10, 16);
        dias.add(fecha3.getTime());
        descripciones.add("Acaba el plazo para presentar las candidaturas ante la Junta electora");
        GregorianCalendar fecha4 = new GregorianCalendar(2015, 10, 18);
        dias.add(fecha4.getTime());
        descripciones.add("Publicación en el BOE de las candidaturas presentadas.");
        GregorianCalendar fecha5 = new GregorianCalendar(2015, 10, 21);
        dias.add(fecha5.getTime());
        descripciones.add("Fin del plazo para que los españoles residentes en el extranjero soliciten el voto.\nComienzan los sorteos para designar a los miembros de las mesas electorales.");
        GregorianCalendar fecha6 = new GregorianCalendar(2015, 10, 24);
        dias.add(fecha6.getTime());
        descripciones.add("Publicación en el BOE de las candidaturas proclamadas una vez subsanadas las posibles irregularidades detectadas.");
        GregorianCalendar fecha7 = new GregorianCalendar(2015, 10, 25);
        dias.add(fecha7.getTime());
        descripciones.add("Acaban los sorteos para designar a los miembros de las mesas electorales.");
        GregorianCalendar fecha8 = new GregorianCalendar(2015, 11, 4);
        dias.add(fecha8.getTime());
        descripciones.add("Comienza la campaña electoral.");
        GregorianCalendar fecha9 = new GregorianCalendar(2015, 11, 10);
        dias.add(fecha9.getTime());
        descripciones.add("Fin del plazo para solicitar el voto por correo para los electores residentes en España.");
        GregorianCalendar fecha10 = new GregorianCalendar(2015, 11, 15);
        dias.add(fecha10.getTime());
        descripciones.add("Fecha a partir de la cual está prohibida la publicación de sondeos y encuestas electorales.");
        GregorianCalendar fecha11 = new GregorianCalendar(2015, 11, 18);
        dias.add(fecha11.getTime());
        descripciones.add("Fin de la campaña electoral ");
        GregorianCalendar fecha12 = new GregorianCalendar(2015, 11, 19);
        dias.add(fecha12.getTime());
        descripciones.add("Jornada de reflexión.");
        GregorianCalendar fecha13 = new GregorianCalendar(2015, 11, 20);
        dias.add(fecha13.getTime());
        descripciones.add("Jornada de votación.");
        GregorianCalendar fecha14 = new GregorianCalendar(2015, 11, 23);
        dias.add(fecha14.getTime());
        descripciones.add("Escrutinio general, contando también con los votos procedentes del extranjero.");
        GregorianCalendar fecha15 = new GregorianCalendar(2015, 12, 13);
        dias.add(fecha15.getTime());
        descripciones.add("Fecha tope para la constitución del Congreso y el Senado resultantes de las elecciones.");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menu_actividad_principal, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.acerca:
                startActivity(new Intent(view.getContext(), ActividadAcercaDe.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {
        scrollView.scrollBy(0,1000);
        textView.setText(getSelectedDatesString());
        descripcion.setText(getSelectedDatesDescription());
    }


    private String getSelectedDatesString() {
        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return "Seleccione un día";
        }
        return FORMATTER.format(date.getDate());
    }
    private String getSelectedDatesDescription() {
        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return "No hay eventos este día";
        }

        for (int i = 0; i < dias.size(); i++) {
            CalendarDay day = CalendarDay.from(dias.get(i));
            if (day.equals(date)) {
                return descripciones.get(i);
            }
        }
        return "No hay eventos este día";
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -2);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < dias.size(); i++) {
                CalendarDay day = CalendarDay.from(dias.get(i));
                // CalendarDay day = CalendarDay.from(calendar);
                dates.add(day);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);
            if (isRemoving()) {
                return;
            }
            widget.addDecorator(new EventDecorator(Color.parseColor("#2A2B39"), calendarDays));
        }
    }

    public class EventDecorator implements DayViewDecorator {

        private int color;
        private HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(5, color));
        }
    }
}

