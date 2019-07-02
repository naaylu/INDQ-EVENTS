package com.example.indq_events;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class EventTitleActivity extends AppCompatActivity {
    EditText title, description;
    String _title, _description;
    String  _firstName,_lastName,_token;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_titlte);

        //actionBar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Paso 1.Introduccion");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#10618E")));

        //Data login
        Bundle extras = getIntent().getExtras();
        if (extras  != null) {
            _firstName= extras.getString("FirstName");
            _lastName=extras.getString("LastName");
            _token=extras.getString("Token");
        }
        //Get values of EditText
        title = findViewById(R.id.input_title);
        description = findViewById(R.id.input_description);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:
                next();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflaters = getMenuInflater();
        inflaters.inflate(R.menu.actions, menu);
        return true;
    }
    public boolean validate(){
        boolean valid = true;
        if (_title.isEmpty()){
            title.setError("El titulo esta vacio");
            return false;
        }else {
            title.setError(null);
        }
        if (_description.isEmpty()){
            description.setError("El titulo esta vacio");
            return false;
        }else {
            description.setError(null);
        }
        return valid;
    }
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Next failed", Toast.LENGTH_LONG).show();

    }
    public void next(){
        _title= title.getText().toString();
        _description = description.getText().toString();
        if (!validate()) {
            onSignupFailed();
            return;
        }
        else {
            Intent i = new Intent(this, EventImageActivity.class);
            i.putExtra("FirstName",_firstName);
            i.putExtra("LastName",_lastName);
            i.putExtra("Token",_token);
            i.putExtra("Title",_title);
            i.putExtra("Description",_description);
            startActivity(i);
        }
    }
}
