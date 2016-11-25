package com.emilykag.weatherapp.fragments;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.emilykag.weatheapp.R;
import com.emilykag.weatherapp.adapters.ViewPagerAdapter;
import com.emilykag.weatherapp.activities.SettingsActivity;
import com.emilykag.weatherapp.utils.WeatherImageTool;

public class ForecastFragment extends Fragment {

    private LinearLayout layoutFragmentForecast;

    public ForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragments(new MainForecastFragment(), getString(R.string.main_forecast));
        pagerAdapter.addFragments(new WeeklyForecastFragment(), getString(R.string.weakly_forecast));

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        layoutFragmentForecast = (LinearLayout) view.findViewById(R.id.layoutFragmentForecast);
        ImageButton imageButtonMenu = (ImageButton) view.findViewById(R.id.imageButtonMenu);
        imageButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });

        return view;
    }

    private void showMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);

        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_settings) {
                    startActivity(new Intent(getContext(), SettingsActivity.class));
                }
                return true;
            }
        });
    }

    public void setBackgroundImage(String code) {
        new WeatherImageTool(getContext(), layoutFragmentForecast).setWeatherImage(code, "back");
    }
}
