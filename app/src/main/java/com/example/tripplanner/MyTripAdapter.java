package com.example.tripplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyTripAdapter extends ArrayAdapter<Trip> {
    public MyTripAdapter(@NonNull Context context, int resource, @NonNull List<Trip> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Trip trip = getItem(position);
        ViewHolder viewHolder;

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.activity_trip_details,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.textViewTitle=convertView.findViewById(R.id.tvTitleDetail);
            viewHolder.ivCoverpic=convertView.findViewById(R.id.tvImageDetail);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.textViewTitle.setText(trip.title);
        Picasso.get().load(trip.url).into(viewHolder.ivCoverpic);


        return convertView;


    }

    public static class ViewHolder{
        TextView textViewTitle;
        ImageView ivCoverpic;
    }
}

