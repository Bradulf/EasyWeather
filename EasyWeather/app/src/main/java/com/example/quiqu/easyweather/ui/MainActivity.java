/** Favorite Bookmarks test application Version 1.0*/


package com.example.quiqu.easyweather.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiqu.easyweather.R;
import com.example.quiqu.easyweather.weather.Current;
import com.example.quiqu.easyweather.weather.Day;
import com.example.quiqu.easyweather.weather.Forecast;
import com.example.quiqu.easyweather.weather.Hour;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

/**
 * Following the recipee for making network connections on to the API to be used in here.
 *
 * */

    private static final String TAG =  MainActivity.class.getSimpleName();
    public static final String HOURLY_FORECAST = "heeeeey macarena";
    public static final String DAILY_FORECAST = "DAILY_FORECAST";

    //-----------------------------------API
    private final String apiKey = "da8b6634e285f32e106072bd3ff02df8";
    private double lat = 27.530567;
    private double longi = -99.480324;
    private final String forecastUrl = "https://api.darksky.net/forecast/" + apiKey + "/" + lat + "," + longi;

    // // TODO: 11/14/2016  POJO
    private Forecast forecast;

    // basic database requirements
    private static final String SELECT_SQL = "SELECT * FROM users";

    private SQLiteDatabase db;

    private Cursor c;



   // TextView locationwhatever;
    /**Butterknife injections*/ // pass all of these as regylar find by id...butterknife returning null pointer
    @BindView(R.id.locationLabel)TextView mLocationLabel;
    @BindView(R.id.timeLabel) TextView mTimeLabel;
    @BindView(R.id.temperatureLabel) TextView mTemperatureLabel;
    @BindView(R.id.humidityValue) TextView mHumidityValue;
    @BindView(R.id.precipValue) TextView mPrecipValue;
    @BindView(R.id.summaryLabel) TextView mSummaryLabel;
    @BindView(R.id.iconImageView) ImageView mIconImageView;
    @BindView(R.id.refreshImageView)ImageView mRefreshImageView;
    @BindView(R.id.progressBar2)ProgressBar mProgressBar;
    //TextView mTemperatureLabel;

    // user information to be displayed on the screen
    @BindView(R.id.userInfo) TextView mUserInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        openDatabase();

        mProgressBar.setVisibility(View.INVISIBLE);




        // mTemperatureLabel = (TextView) findViewById(R.id.temperatureLabel);//
        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast();
            }
        });

        /**The initial client connection goes in here **/
        getForecast();


        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        showRecords();

    } // end of onCreate()

    private void getForecast() {

        toggleRefresh();

        if (isNetworkAvailable()) {
            OkHttpClient client  = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if(response.isSuccessful()) {
                            forecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateUi();
                                }
                            });

                        }else{
                            alertUserAboutError();
                        }

                    }catch(IOException e){
                        Log.e(TAG, "Exception caught: " + e);
                    }
                    catch(JSONException je) {

                    }
                }
            });

        } else {
            Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
        }
    }
    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        }
        else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }
    /**
     *
     * The following method takes care of making an update to the view content based on the information being passed
     * after the current weather has been set, this needs to run on a runnable ui thread since it separates from the other
     * network components.
     * Notice that each one of these view widgets had already been described above.
     *
     *
     * -- notice that the change is currently being done because we are GETTING the information from forecast while we CREATE them from the parseForecastMethod
     * */
    public void updateUi() {
        mLocationLabel.setText(forecast.getCurrent().getmCity());
        mTemperatureLabel.setText(forecast.getCurrent().getTemperature()+ "");
        mTimeLabel.setText("At " + forecast.getCurrent().getFormattedTime() + " it will be");
        mHumidityValue.setText(forecast.getCurrent().getHumidity() + "");
        mPrecipValue.setText(forecast.getCurrent().getPrecipChance() + "%");
        mSummaryLabel.setText(forecast.getCurrent().getSummary());

        Drawable drawable = ContextCompat.getDrawable(this, forecast.getCurrent().getIconId());// this is how you get a drawable icon
        mIconImageView.setImageDrawable(drawable);
    }


    /**
     *
     * The recipe for populating this(which will be basically the Create object from my other project) is simple
     * we are basically parsing POPULATING each one of the classes from inside the main class(Forecast) by using methods inside the controller that
     * take care of populating all the information.
     *
     * THE ONLY ARRAYS ARE BEING USED FROM INSIDE THE FORECAST.JAVA file
     *
     * And since the  Forecast class EXPECTS arrays, arrays need to be returned from the methods in here, hence why Day[] is the way it is as well as Hour[]
     *
     * :*
     *
     * */
    private Forecast parseForecastDetails(String jsonData)throws JSONException{
        Forecast forecast = new Forecast();
        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setHourlyforecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));


        return forecast;
    }

    private Day[] getDailyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData); // from the request we get the big body of json
        String timezone = forecast.getString("timezone");
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];

        for(int i  = 0; i < data.length();i++) {
            JSONObject jsonDay = data.getJSONObject(i);
            Day day = new Day();
            day.setSummary(jsonDay.getString("summary"));
            day.setIcon(jsonDay.getString("icon"));
            day.setTemperatureMax(jsonDay.getDouble("temperatureMax"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimezone(timezone);

            days[i] = day;

        }
        return days;
    }

    // use this as the main example regarding getitng the information from nested
    // objects inside of json arrays
    private Hour[] getHourlyForecast(String jsonData)throws JSONException {
        JSONObject forecast = new JSONObject(jsonData); // from the request we get the big body of json
        String timezone = forecast.getString("timezone"); // we pass the timezone from the whole thing by accessing its string key
        JSONObject hourly = forecast.getJSONObject("hourly"); //we access the hourly object by getting its name
        JSONArray data = hourly.getJSONArray("data"); // we access its data key by gettig the array value element that it points to.

        // Now, since we are working with the hour model, we create a new hour array equal to the total length of the items inside
        // of data, this will be done across all information being passed regardless of it being Arraylist or whatnot
        Hour[] hours = new Hour[data.length()];
        //now we loop accross everything inside and we get the information that we need
        for (int i  = 0; i < data.length();i++) {
            JSONObject jsonHour = data.getJSONObject(i);
            Hour hour = new Hour();
            hour.setSummary(jsonHour.getString("summary"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimezone(timezone);

            // now that everything has been set we can add to the first element of the hours array
            hours[i] = hour;
        }
    return hours;
    }


    /**
     *
     * Step 1 in passing information(methods wise)
     * The following method takes care of getting the information from JSON objects and parsing it to the appropiate constructs
     * from here on it returns a current instance ALREADY populated to be sent to the user interface for updates
     *
     * --This stayed the same btw
     **/

    private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "From JSON: " + timezone);

        JSONObject currently = forecast.getJSONObject("currently");

        Current current = new Current();
        current.setmCity("Laredo");
        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setIcon(currently.getString("icon"));
        current.setPrecipChance(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setTimeZone(timezone);

        Log.d(TAG, current.getFormattedTime());

        return current;
    }


    /**This method checks to see if the connection has been made to the actual system.*/
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected())
            isAvailable = true;
        return isAvailable;
    }

    /**Method to alert the user about any errors*/
    public void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }


    //todo here is where all the other onclick listeners will go into
    //this spell is quite cool, basically what happens is that we are injecting this as a dependency manager for the
    //onClick listener that will be attached to the 7 day button, so if you were wondering how to start activities and additional information
    // regarding the custom views required....this is the case for it. It will be simpler to add

    //// TODO: 11/15/2016 FIRST
    // First we send the intent with the key value pair to the activity, this is a parcelable class
    // it was created as one so we need to receive it inside the correct parcelable variables inside the activity
    @OnClick(R.id.dailyButton)
    public void startDailyActivity(View view) {
        Intent intent = new Intent(this, DailyForecastActivity.class);
        intent.putExtra(DAILY_FORECAST,forecast.getDailyForecast());
        startActivity(intent);
    }


    @OnClick(R.id.hourlyButton)
    public void startHourlyActivity(View view) {
        Intent intent = new Intent(this, HourlyForecastActivity.class);
        intent.putExtra(HOURLY_FORECAST, forecast.getHourlyforecast());
        Log.d("Is there anything?",intent.toString());

        startActivity(intent);
    }


    // database information
    protected void openDatabase() {
        db = openOrCreateDatabase("UserDb", Context.MODE_PRIVATE, null);

    }

    // and to show the information inside of the database
    protected void showRecords() {
        String userUserName = c.getString(1);
        String city = c.getString(2);

        String allTogether = String.format("Welcome %s from %s", userUserName, city);
        mUserInfo.setText(allTogether);
    }
}
