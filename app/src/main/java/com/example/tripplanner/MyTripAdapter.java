package com.example.tripplanner;

import android.content.Context;
import android.util.Log;
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

import static com.example.tripplanner.R.drawable.forest;

public class MyTripAdapter extends ArrayAdapter<Trips> {

    public MyTripAdapter(@NonNull Context context, int resource, @NonNull List<Trips> objects) {
        super(context,resource,objects);
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Trips trips = getItem(position);
        ViewHolder viewHolder;

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.trip_list_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.textViewTitle=convertView.findViewById(R.id.listTitle);
            viewHolder.textViewCreator=convertView.findViewById(R.id.textViewCreator);
            viewHolder.iv=convertView.findViewById(R.id.listCoverImage);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.textViewTitle.setText(trips.title);
        viewHolder.textViewCreator.setText(trips.name);
//        Picasso.get().load(trips.cover_image).into(viewHolder.iv);
        switch(trips.cover_image){
            case "forest":
                viewHolder.iv.setImageResource(forest);
                break;
            case "city":
                viewHolder.iv.setImageResource(R.drawable.city);
                break;
            case "camping":
                viewHolder.iv.setImageResource(R.drawable.camping);
                break;
            case "desert":
                viewHolder.iv.setImageResource(R.drawable.desert);
                break;
            case "mountains":
                viewHolder.iv.setImageResource(R.drawable.mountains);
                break;
            case "beach":
                viewHolder.iv.setImageResource(R.drawable.beach);
                break;
        }


        return convertView;

    }

    public static class ViewHolder{
        TextView textViewTitle,textViewCreator;
        ImageView iv;
    }
}

