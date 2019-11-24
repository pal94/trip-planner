package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FindTripPage extends AppCompatActivity {

    private static final String TAG = "demo";
    Trips trip = new Trips();
    TextView title, creator;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_trip_page);

        final Bundle tripData = getIntent().getExtras().getBundle("tripData");
        trip = (Trips) tripData.getSerializable("trip");

        title = findViewById(R.id.textViewTitleFind);
        creator = findViewById(R.id.textViewCreatedByFind);
        iv = findViewById(R.id.imageViewCover);

        title.setText(trip.title);
        creator.setText(trip.name);
        Picasso.get().load(trip.cover_image).into(iv);
    }
}
