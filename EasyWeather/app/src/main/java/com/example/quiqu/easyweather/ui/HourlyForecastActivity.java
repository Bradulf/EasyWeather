package com.example.quiqu.easyweather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.quiqu.easyweather.R;
import com.example.quiqu.easyweather.adapters.HourAdapter;
import com.example.quiqu.easyweather.weather.Hour;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HourlyForecastActivity extends AppCompatActivity {

    // -- Set that gold juju
    private Hour[] mHours;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);
        ButterKnife.bind(this);

        // get that intent parcelable juju mojo baby
        Intent intent = getIntent();
        Parcelable[] parcelable = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        mHours = Arrays.copyOf(parcelable, parcelable.length, Hour[].class);

        // set the adapter and pass it the information
        HourAdapter adapter = new HourAdapter(mHours);
        mRecyclerView.setAdapter(adapter);
        // holy fuck.......an extra step not only do we have to pass the adapter, but we need to pass a layout manager at
        // the same time, this is cool cuz it can be configured later on though
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);


    }
}
