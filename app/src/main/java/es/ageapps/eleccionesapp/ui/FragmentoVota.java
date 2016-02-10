package es.ageapps.eleccionesapp.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import es.ageapps.eleccionesapp.R;

/**
 * Created by adricacho on 22/11/15.
 */
public class FragmentoVota extends Fragment {

    private RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    private AdaptadorVota adaptador;
    private View view;
    private CoordinatorLayout coordinatorLayout;
    public  boolean touched;
    private InterstitialAd mInterstitialAd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        reciclador.setLayoutManager(layoutManager);
        mInterstitialAd = new InterstitialAd(view.getContext());
        // Defined in res/values/strings.xml
        mInterstitialAd.setAdUnitId("ca-app-pub-5047980690000653/9133133124");

        touched = false;
        mInterstitialAd.setAdListener (new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();


        reciclador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!touched) {
                    touched = true;
                    showInterstitial();
                }
                else {
                    touched = false;
                }

            }
        });

        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id
                .main_content);
        adaptador = new AdaptadorVota();
        reciclador.setAdapter(adaptador);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onDestroyView() {
        this.getFragmentManager().beginTransaction().addToBackStack("candidato");
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
        return super.onOptionsItemSelected(item);
    }
    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        else {
            Log.i("Vota", "intersicial no cargado");
        }
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();

        mInterstitialAd.loadAd(adRequest);
    }


}
