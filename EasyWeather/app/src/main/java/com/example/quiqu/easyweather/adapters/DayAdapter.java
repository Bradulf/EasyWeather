/**
 *   Day adapter
 *
 * */

package com.example.quiqu.easyweather.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quiqu.easyweather.R;
import com.example.quiqu.easyweather.weather.Day;

/**
 * Created by quiqu on 11/15/2016.
 */

// the methods get overwritten as soon as we set it to extend the baseadapter
public class DayAdapter extends BaseAdapter {
    private Context mContext;
    private Day[] mdays;

    // constructor REQUIRED
    public DayAdapter(Context context, Day[] days){
        this.mContext = context;
        this.mdays = days;
    }

    @Override
    public int getCount() {
        return mdays.length;
    }

    @Override
    public Object getItem(int position) {
        return mdays[position];
    }

    @Override
    public long getItemId(int position) {
        return 0; // we are not going to use this
    }
    // method that gets called for each view in the screen
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        /**
         *   SORT of like the onCreate, this here takes care of grabing the convertView which is set to null initially
         *   as it was created and adding the required INFLATER for it to be popiulated later on when creating the file
         *   for this we can call our basic holder class which holds the id of the information that was given from
         *   the bottom (the class basically holds all the information of the screen if you had noticed)
         *   and then starts setting the view, being that we have a convertView already that is what we use for the code to
         *   work with the findViewById method
         *
         *   ELSE
         *   if the information has already been set we just call getTag();
         * */
        if (convertView == null) {
            // brand new
            convertView = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item, null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            holder.temperatureLabel = (TextView) convertView.findViewById(R.id.temperatureLabel);
            holder.dayLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Day day = mdays[position];

        holder.iconImageView.setImageResource(day.getIconId());
        holder.temperatureLabel.setText(day.getTemperatureMax() + "");

        // add the day accordingly, tis is how you would change the data s it is being displayed from inside the adapter
        if(position  == 0) {
            holder.dayLabel.setText("Today");
        } else {
            holder.dayLabel.setText(day.getDayOfTheWeek());
        }


        return convertView;
    }

    private static class ViewHolder {
        ImageView iconImageView; // public by default
        TextView temperatureLabel;
        TextView dayLabel;
    }
}



































