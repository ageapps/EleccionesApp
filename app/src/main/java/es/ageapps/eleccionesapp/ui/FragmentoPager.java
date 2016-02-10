package es.ageapps.eleccionesapp.ui;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.ageapps.eleccionesapp.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Fragmento de la sección "Programas"
 */
public class FragmentoPager extends Fragment {

    private AppBarLayout appBar;
    private TabLayout pestanas;
    private ViewPager viewPager;
    int indice_pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_paginado, container, false);

        indice_pager = getArguments().getInt(ActividadPrincipal.INDICE_PAGER);

        int[] tabIcons = {
                R.drawable.logo_pp, R.drawable.logo_psoe, R.drawable.logo_c,
                R.drawable.logo_podemos, R.drawable.logo_upyd, R.drawable.logo_iu
        };
        if (savedInstanceState == null) {
            insertarTabs(container);

            // Setear adaptador al viewpager.
            viewPager = (ViewPager) view.findViewById(R.id.pager);
            poblarViewPager(viewPager);
            pestanas.setupWithViewPager(viewPager);

            if (indice_pager == 2 || indice_pager == 3) {
                pestanas.getTabAt(0).setIcon(tabIcons[0]);
                pestanas.getTabAt(1).setIcon(tabIcons[1]);
                pestanas.getTabAt(2).setIcon(tabIcons[2]);
                pestanas.getTabAt(3).setIcon(tabIcons[3]);
                pestanas.getTabAt(4).setIcon(tabIcons[4]);
                pestanas.getTabAt(5).setIcon(tabIcons[5]);
            }

        }
        return view;
    }

    private void insertarTabs(ViewGroup container) {


        View padre = (View) container.getParent();
        appBar = (AppBarLayout) padre.findViewById(R.id.appbar);
        pestanas = new TabLayout(getActivity());
        pestanas.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));

        if (indice_pager == 1 || indice_pager == 4) {
            pestanas.setTabMode(TabLayout.MODE_FIXED);
            pestanas.setTabGravity(TabLayout.GRAVITY_CENTER);
        } else {
            pestanas.setTabMode(TabLayout.MODE_SCROLLABLE);
            pestanas.setTabGravity(TabLayout.GRAVITY_FILL);
        }
        appBar.addView(pestanas);
    }

    private void poblarViewPager(ViewPager viewPager) {

        AdaptadorSecciones adapter = new AdaptadorSecciones(getFragmentManager());

        switch (indice_pager) {
            case 1:
                Fragment fragmento1 = new FragmentoTimelineNoticias();
                Bundle args1 = new Bundle();
                args1.putBoolean(FragmentoTimelineNoticias.IS_ELECCIONES, true);
                fragmento1.setArguments(args1);
                adapter.addFragment(fragmento1, "Políticos");


                Fragment fragmento2 = new FragmentoTimelineNoticias();
                Bundle args2 = new Bundle();
                args2.putBoolean(FragmentoTimelineNoticias.IS_ELECCIONES, false);
                fragmento2.setArguments(args2);
                adapter.addFragment(fragmento2, "Elecciones  #20D");
                break;

            case 2:
                adapter.addFragment(FragmentoIdeas.nuevaInstancia(0, true), "PP");
                adapter.addFragment(FragmentoIdeas.nuevaInstancia(1, false), "PSOE");
                adapter.addFragment(FragmentoIdeas.nuevaInstancia(2, true), "C's");
                adapter.addFragment(FragmentoIdeas.nuevaInstancia(3, false), "Podemos");
                adapter.addFragment(FragmentoIdeas.nuevaInstancia(4, true), "UPyD");
                adapter.addFragment(FragmentoIdeas.nuevaInstancia(5, false), "IU");

                break;

            case 4:

                Fragment fragmento3 = new es.ageapps.eleccionesapp.ui.FragmentoSondeoOficial();
                adapter.addFragment(fragmento3, "Oficiales");

                Fragment fragmento4 = new FragmentoSodeoUsuario();
                adapter.addFragment(fragmento4, "Usuarios");

                break;

        }

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBar.removeView(pestanas);

    }


    /**
     * Un {@link FragmentStatePagerAdapter} que gestiona las secciones, fragmentos y
     * títulos de las pestañas
     */
    public class AdaptadorSecciones extends FragmentStatePagerAdapter {
        private final List<Fragment> fragmentos = new ArrayList<>();
        private final List<String> titulosFragmentos = new ArrayList<>();

        public AdaptadorSecciones(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentos.get(position);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentos.add(fragment);
            titulosFragmentos.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (indice_pager == 1 || indice_pager == 4) {
                return titulosFragmentos.get(position);
            }
            return null;
        }
    }
}
