package com.example.indq_events;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventDateActivity extends AppCompatActivity {
    String title = null;
    String description = null;
    String  _firstName,_lastName,_image,_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_date);

        //Data of event
        Bundle extras = getIntent().getExtras();
        if (extras  != null) {
            _firstName= extras.getString("FirstName");
            _lastName=extras.getString("LastName");
            _token = extras.getString("Token");
            this.title= extras.getString("Title");
            this.description = extras.getString("Description");
            _image = extras.getString("Image");
        }

        //actionBar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Paso 3.Fecha");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#10618E")));
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:
                Intent i = new Intent(this, EventPlaceActivity.class);
                i.putExtra("FirstName",_firstName);
                i.putExtra("LastName",_lastName);
                i.putExtra("Title",this.title);
                i.putExtra("Token",_token);
                i.putExtra("Description",this.description);
                i.putExtra("Image",this._image);
                Date date = getDateFromDatePicker((DatePicker)findViewById(R.id.dpDate));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                i.putExtra("Date", sdf.format(date));
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflaters = getMenuInflater();
        inflaters.inflate(R.menu.actions, menu);
        return true;
    }
}
