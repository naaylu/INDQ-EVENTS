package com.example.indq_events;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventPlaceActivity extends AppCompatActivity implements OnMapReadyCallback {
    String title = null;
    String description = null;
    String date = null;
    String  _firstName,_lastName,_image,_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_place);

        //Data of event
        Bundle extras = getIntent().getExtras();
        if (extras  != null) {
            _firstName= extras.getString("FirstName");
            _lastName=extras.getString("LastName");
            _token = extras.getString("Token");
            this.title= extras.getString("Title");
            this.description = extras.getString("Description");
            _image = extras.getString("Image");
            this.date = extras.getString("Date");
        }

        //actionBar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Paso 4.Lugar");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#10618E")));
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:
                Intent i = new Intent(this, EventFinalActivity.class);
                i.putExtra("FirstName",_firstName);
                i.putExtra("LastName",_lastName);
                i.putExtra("Token",_token);
                i.putExtra("Title",this.title);
                i.putExtra("Description",this.description);
                i.putExtra("Image",_image);
                i.putExtra("Date", date);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflaters = getMenuInflater();
        inflaters.inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
