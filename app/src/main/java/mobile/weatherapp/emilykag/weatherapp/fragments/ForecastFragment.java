package mobile.weatherapp.emilykag.weatherapp.fragments;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import mobile.weatherapp.emilykag.weatherapp.R;
import mobile.weatherapp.emilykag.weatherapp.adapters.ViewPagerAdapter;

public class ForecastFragment extends Fragment {

    private LinearLayout layoutFragmentForecast;
    private static ForecastFragment forecastFragment;

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
        forecastFragment = this;

        return view;
    }

    public static ForecastFragment getInstance() {
        return forecastFragment;
    }

    public void setWeatherImage(String code) {
        if (code.equals("0") || code.equals("2")) {
            setImageResource("tornado");
        } else if (code.equals("1") || code.equals("3") || code.equals("4") || code.equals("37") || code.equals("38")
                || code.equals("39") || code.equals("45") || code.equals("47")) {
            setImageResource("thunderstorms");
        } else if (code.equals("5") || code.equals("6") || code.equals("7") || code.equals("18")) {
            setImageResource("rainandsnowmixed");
        } else if (code.equals("8") || code.equals("9") || code.equals("10") || code.equals("17") || code.equals("35")) {
            setImageResource("rain");
        } else if (code.equals("11") || code.equals("12") || code.equals("40")) {
            setImageResource("shower");
        } else if (code.equals("13") || code.equals("14")) {
            setImageResource("flurries");
        } else if (code.equals("15") || code.equals("16") || code.equals("41") || code.equals("42") || code.equals("43")
                || code.equals("46")) {
            setImageResource("snow");
        } else if (code.equals("19") || code.equals("20") || code.equals("21") || code.equals("22")) {
            setImageResource("fog");
        } else if (code.equals("23") || code.equals("24")) {
            setImageResource("windy");
        } else if (code.equals("25")) {
            setImageResource("cold");
        } else if (code.equals("26") || code.equals("44")) {
            setImageResource("mostlycloudy");
        } else if (code.equals("27") || code.equals("29")) {
            setImageResource("mostlyclear");
        } else if (code.equals("28") || code.equals("30")) {
            setImageResource("partlysunny");
        } else if (code.equals("31") || code.equals("33")) {
            setImageResource("clear");
        } else if (code.equals("32") || code.equals("34")) {
            setImageResource("sunny");
        } else if (code.equals("36")) {
            setImageResource("hot");
        }
    }

    private void setImageResource(String imgName) {
        int bgID = getResources().getIdentifier("drawable/bg_" + imgName, null, getContext().getPackageName());
        Drawable weatherBackground = getResources().getDrawable(bgID);
        layoutFragmentForecast.setBackground(weatherBackground);
    }
}
