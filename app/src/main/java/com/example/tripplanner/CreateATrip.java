package com.example.tripplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.tripplanner.R.drawable.forest;
import static com.example.tripplanner.R.drawable.male1;

public class CreateATrip extends AppCompatActivity {
    EditText tvTitle,location;
    EditText tvlatitude;
    EditText tvlongitude;
    TextView textViewDate;
    ImageView iv;
    User loggedUser;
    Gson gson = new Gson();
    String title;
    Double latitude, longitude;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences prefsEditor;
    SharedPreferences.Editor editor;
    ArrayList<String> emails = new ArrayList<>();
    private String API_KEY = "AIzaSyAWhCYD8AI4FZfrdteZVgx7IH720gjx8YI";
    Place selectedLocation;
    String date = "";
    String emailofuser;
    String cover = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_atrip);

        getSupportActionBar().setTitle("Create A Trip");

        prefsEditor = getSharedPreferences("loggedUser", MODE_PRIVATE);
        String json = prefsEditor.getString("userObject", "");
        editor = prefsEditor.edit();

        loggedUser = gson.fromJson(json, User.class);


        tvTitle = findViewById(R.id.tv_title);
        location = findViewById(R.id.editTextLocation);

//        tvlatitude = findViewById(R.id.tvlatitude);
//        tvlongitude = findViewById(R.id.tvlongitude);
        iv = findViewById(R.id.coverpic);

        textViewDate = findViewById(R.id.textViewDate);
        findViewById(R.id.buttonDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateATrip.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date = month + "/" + dayOfMonth + "/" + year;
                        Log.d("demo", date);
                        textViewDate.setText(date);
                        textViewDate.setVisibility(View.VISIBLE);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateATrip.this, SelectCover.class);
                startActivityForResult(intent, 727);
            }
        });

        findViewById(R.id.buttonLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location.getText().toString().isEmpty()) {
                    location.setError("Enter some location");

                } else {
                    new GetAsyncData().execute("https://maps.googleapis.com/maps/api/place/autocomplete/json?key=" + API_KEY + "&types=(cities)&input=" + location.getText().toString());
                }
            }
        });

        findViewById(R.id.btnCreateTrip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    title = tvTitle.getText().toString();
                    emailofuser = loggedUser.email;

                    List<String> addedUsers = new ArrayList<>();
                    Map<String, Object> tripdetails = new HashMap<>();
                    tripdetails.put("title", title);
                    tripdetails.put("latitude", selectedLocation.latitude);
                    tripdetails.put("longitude", selectedLocation.longitude);
                    tripdetails.put("location", selectedLocation.description);
                    tripdetails.put("url", cover);
                    tripdetails.put("date", date);
                    tripdetails.put("Emailofuser", emailofuser);
                    tripdetails.put("creator", loggedUser.first + " " + loggedUser.last);
                    tripdetails.put("addedUsers", addedUsers);
                    db.collection("trip")
                            .document(title)
                            .set(tripdetails)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()) {
                                        Log.d("demo", "ERROR SAVING TO FIRESTORE");
                                    }
                                    Intent intent = new Intent(CreateATrip.this, Dashboard.class);
                                    startActivity(intent);
                                }
                            });


            }
        });


    }

    private void setCover(String id){
        switch(id){
            case "forest":
                iv.setImageResource(forest);
                break;
            case "city":
                iv.setImageResource(R.drawable.city);
                break;
            case "camping":
                iv.setImageResource(R.drawable.camping);
                break;
            case "desert":
                iv.setImageResource(R.drawable.desert);
                break;
            case "mountains":
                iv.setImageResource(R.drawable.mountains);
                break;
            case "beach":
                iv.setImageResource(R.drawable.beach);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 727 && resultCode == RESULT_OK) {
            cover = data.getStringExtra("cover");
            Log.d("demo", "onActivityResult: "+cover);
            setCover(cover);

        }
    }

    public class GetGeoAsyncData extends AsyncTask<String,Void, Place> {

        protected Place doInBackground(String... params) {
            HttpURLConnection connection = null;
            ArrayList<Place> result = new ArrayList<>();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                    JSONObject root = new JSONObject(json);
                    JSONObject resultObj = root.getJSONObject("result");
                    JSONObject geo = resultObj.getJSONObject("geometry");
                    JSONObject loc = geo.getJSONObject("location");
                    selectedLocation.latitude = loc.getString("lat");
                    selectedLocation.longitude = loc.getString("lng");
                }
            } catch (Exception e) {
                //Handle Exceptions
            } finally {
                //Close the connections
            }
            return selectedLocation;
        }

        @Override
        protected void onPostExecute(final Place result) {
            if (result!=null) {
                Log.d("demo", result.toString());

            } else {
                Log.d("demo", "empty result");
                Toast.makeText(CreateATrip.this, "NO DETAILS FOUND", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public class GetAsyncData extends AsyncTask<String,Void, ArrayList<Place>> {

        protected ArrayList<Place> doInBackground(String... params) {
            HttpURLConnection connection = null;
            ArrayList<Place> result = new ArrayList<>();
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                    JSONObject root = new JSONObject(json);
                    JSONArray predictions = root.getJSONArray("predictions");


                    for (int i=0;i<predictions.length();i++) {
                        JSONObject sourceJson = predictions.getJSONObject(i);
                        String desc = sourceJson.getString("description");
                        String[] name = desc.split(",");

                        Place place = new Place(sourceJson.getString("place_id"),name[0]+", "+name[1]);
                        result.add(place);
                    }
                }
            } catch (Exception e) {
                //Handle Exceptions
            } finally {
                //Close the connections
            }
            return result;
        }

        @Override
        protected void onPostExecute(final ArrayList<Place> result) {
            if (result.size() > 0) {
                Log.d("demo", result.toString());

                ListView listView = findViewById(R.id.listView);
                LocationAdapter adapter = new LocationAdapter(CreateATrip.this, R.layout.location_item,result);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("demo", "Clicked item "+position);
                        selectedLocation = result.get(position);
                        location.setText(""+selectedLocation.description);
                        new GetGeoAsyncData().execute("https://maps.googleapis.com/maps/api/place/details/json?key="+API_KEY+"&placeid="+selectedLocation.place_id);

                    }
                });

            } else {
                Log.d("demo", "empty result");
                Toast.makeText(CreateATrip.this, "NO CITIES FOUND", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
