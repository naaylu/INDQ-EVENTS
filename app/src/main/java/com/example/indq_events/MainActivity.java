package com.example.indq_events;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    String _id, _token,_firstName, _lastName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AppBar
        android.support.v7.widget.Toolbar toolbar =findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //Data of login
        Bundle extras = getIntent().getExtras();
        if (extras  != null) {
            this._id = extras.getString("Id");
            this._token = extras.getString("Token");
            this._firstName = extras.getString("FirstName");
            this._lastName = extras.getString("LastName");
        }

        //Name
        TextView allName = findViewById(R.id.toolbar_subtitle);
        allName.setText(_firstName+ " "+_lastName);
    }
    //Menu actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
            case R.id.logout:
                Intent in = new Intent(this, LoginActivity.class);
                startActivity(in);
                return true;
            case R.id.add:
                Intent inten = new Intent(this, LoginActivity.class);
                startActivity(inten);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
}
