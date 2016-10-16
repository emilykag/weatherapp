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
        switch (code) {
            case "0":
            case "2":
                setImageResource("tornado");
                break;
            case "1":
            case "3":
            case "4":
            case "37":
            case "38":
            case "39":
            case "45":
            case "47":
                setImageResource("thunderstorms");
                break;
            case "5":
            case "6":
            case "7":
            case "18":
                setImageResource("rainandsnowmixed");
                break;
            case "8":
            case "9":
            case "10":
            case "17":
            case "35":
                setImageResource("rain");
                break;
            case "11":
            case "12":
            case "40":
                setImageResource("shower");
                break;
            case "13":
            case "14":
                setImageResource("flurries");
                break;
            case "15":
            case "16":
            case "41":
            case "42":
            case "43":
            case "46":
                setImageResource("snow");
                break;
            case "19":
            case "20":
            case "21":
            case "22":
                setImageResource("fog");
                break;
            case "23":
            case "24":
                setImageResource("windy");
                break;
            case "25":
                setImageResource("cold");
                break;
            case "26":
            case "44":
                setImageResource("mostlycloudy");
                break;
            case "27":
            case "29":
                setImageResource("mostlyclear");
                break;
            case "28":
            case "30":
                setImageResource("partlysunny");
                break;
            case "31":
            case "33":
                setImageResource("clear");
                break;
            case "32":
            case "34":
                setImageResource("sunny");
                break;
            case "36":
                setImageResource("hot");
                break;
        }
    }

    private void setImageResource(String imgName) {
        int bgID = getResources().getIdentifier("drawable/bg_" + imgName, null, getContext().getPackageName());
        layoutFragmentForecast.setBackgroundResource(bgID);
    }
}
