package com.example.quiqu.easyweather.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quiqu.easyweather.R;
import com.example.quiqu.easyweather.weather.Hour;

/**
 * Created by quiqu on 11/15/2016.
 */
// first create this
public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {


    // object to be passed around
    private Hour[] mHours;

    public HourAdapter(Hour[] hours) {
        mHours = hours;

    }

    // here is where the views get created into the list as they are needed.
    // it works in pretty much the same way as the listview
    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_list_item, parent, false);

        HourViewHolder viewHolder = new HourViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
        holder.bindHour(mHours[position]);

    }

    @Override
    public int getItemCount() {
        return mHours.length;
    }



    // then create this and after to get the error get the entireoverrides
    // TODO: 11/15/2016  this is similar to the createView or the getView() for  listviews
    //1 nested class for the viewholder
    public class HourViewHolder extends RecyclerView.ViewHolder {
        // after that we can now create the labels from the actual view
        public TextView mTimeLabel;
        public TextView mSummaryLabel;
        public TextView mTemperatureLabel;
        public ImageView mIconImageView;

        // 2 constructor
        public HourViewHolder(View itemView) {
            super(itemView);

            mTimeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
            mSummaryLabel = (TextView) itemView.findViewById(R.id.summaryLabel);
            mTemperatureLabel = (TextView) itemView.findViewById(R.id.temperatureLabel);
            mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
        }

        // 3 bind it
        public void bindHour(Hour hour) {
            mTimeLabel.setText(hour.getHour());
            mSummaryLabel.setText(hour.getSummary());
            mTemperatureLabel.setText(hour.getTemperature() + "");
            mIconImageView.setImageResource(hour.getIconId());
        }
    }

}
