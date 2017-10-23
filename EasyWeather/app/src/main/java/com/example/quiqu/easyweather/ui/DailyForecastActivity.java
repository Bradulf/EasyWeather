package com.example.quiqu.easyweather.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.quiqu.easyweather.R;
import com.example.quiqu.easyweather.adapters.DayAdapter;
import com.example.quiqu.easyweather.weather.Day;

import java.util.Arrays;

public class DailyForecastActivity extends ListActivity {

    // we set the class which will display the objects, this is a requirement A WEBO
    private Day[] mdays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        // we get the intent
        Intent intent = getIntent();

        // since we are sending a PARCELABLE array of items we need to set the intent here accordingly from the key value pair
        Parcelable[] parcelable = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        // we copy the parcelable info into our instance class
        mdays = Arrays.copyOf(parcelable, parcelable.length, Day[].class);


        // now we send to the  adapter

        // we create the adaptor instance and we pass it the array that we created
        DayAdapter adapter = new DayAdapter(this, mdays);
        // skadoosh
        setListAdapter(adapter);
    }
}
