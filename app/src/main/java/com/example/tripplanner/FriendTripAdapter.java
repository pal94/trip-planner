package com.example.tripplanner;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class FriendTripAdapter extends ArrayAdapter<Trip> {


    public FriendTripAdapter(@NonNull Context context, int resource, @NonNull List<Trip> objects) {
        super(context, resource, objects);
    }


}
