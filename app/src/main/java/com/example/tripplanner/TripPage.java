package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class TripPage extends AppCompatActivity {

    private static final String TAG = "demo";
    Trips trip = new Trips();
    TextView title, creator;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page);

        final Bundle tripData = getIntent().getExtras().getBundle("tripData");
        trip = (Trips) tripData.getSerializable("trip");

        title = findViewById(R.id.textViewTitleTrip);
        creator = findViewById(R.id.textViewCreatorName);
        iv = findViewById(R.id.imageViewCoverPic);

        title.setText(trip.title);
        creator.setText(trip.name);
        Picasso.get().load(trip.cover_image).into(iv);

    }
}
