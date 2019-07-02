package com.example.indq_events;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.log4j.chainsaw.Main;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EventFinalActivity extends AppCompatActivity {
    String title = null;
    String description = null;
    String date = null;
    TextView _title,_description,_date;
    Button btnRegistrar, btnCancelar;
    String  _firstName,_lastName,_image,_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_final);

        Bundle extras = getIntent().getExtras();
        if (extras  != null) {
            _firstName= extras.getString("FirstName");
            _lastName=extras.getString("LastName");
            _token = extras.getString("Token");
            this.title= extras.getString("Title");
            this.description = extras.getString("Description");
            _image=extras.getString("Image");
            this.date = extras.getString("Date");
        }
        _title=findViewById(R.id.resultTitle);
        _title.setText(this.title);
        _description=findViewById(R.id.resultDescription);
        _description.setText("Descripcion: " + this.description);
        _date=findViewById(R.id.resultDate);
        this.date= this.date.substring(0,4)+"-"+this.date.substring(4,6)+"-"+this.date.substring(6,8);
        _date.setText("Fecha: "+this.date);
        this.date=this.date+"T06:00:00.000Z";

        btnRegistrar =findViewById(R.id.btnRegisterEvent);
        btnCancelar =findViewById(R.id.btnCancel);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Evento no registrado", Toast.LENGTH_LONG).show();
                Intent i = new Intent(EventFinalActivity.this,MainActivity.class);
                i.putExtra("FirstName",_firstName);
                i.putExtra("LastName",_lastName);
                i.putExtra("Token",_token);
                startActivity(i);
            }
        });
    }
    public void getData(){
        String api = "http://api.events.indqtech.com/events";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conn;

        try {
            url = new URL(api);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization",_token);
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);

            JSONObject obj = new JSONObject();
            obj.put("title",_title.getText().toString());
            obj.put("description",_description.getText().toString());
            obj.put("date",_date.getText().toString());
            obj.put("image",_image);
            JSONArray array=new JSONArray();;
            array.put(-109.01291847427979);
            array.put(25.802937562653185);
            obj.put("location",array);
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(obj.toString());
            os.flush();
            os.close();

            Log.d("result ",Integer.toString(conn.getResponseCode()));
            if (conn.getResponseCode() == 200){
                Toast.makeText(getBaseContext(), "Evento registrado correctamente", Toast.LENGTH_LONG).show();
                Intent i = new Intent(EventFinalActivity.this,MainActivity.class);
                i.putExtra("FirstName",_firstName);
                i.putExtra("LastName",_lastName);
                startActivity(i);
            }else if(conn.getResponseCode() == 400){
                Toast.makeText(getBaseContext(), "Datos invalidos", Toast.LENGTH_LONG).show();
            }else if(conn.getResponseCode() == 401){
                Toast.makeText(getBaseContext(), "Acceso denegado", Toast.LENGTH_LONG).show();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
