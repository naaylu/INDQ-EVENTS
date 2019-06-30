package com.example.indq_events;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    Button btnlogin,btnsignup;
    EditText _email,_password;
    String _id,_token,_firstName,_lastName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Navigation to Signup
        btnsignup = findViewById(R.id.btn_signup);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

        //Navigation to Main
        btnlogin = findViewById(R.id.btn_login);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }
    public void login() {
        if (!validate()) {
            onSignupFailed();
            return;
        }
        else {
            getData();
        }
    }
    public void getData(){
        String sql = "http://api.events.indqtech.com/users/login";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conn;

        try {
            url = new URL(sql);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject obj = new JSONObject();
            obj.put("email", _email.getText().toString());
            obj.put("password", _password.getText().toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(obj.toString());
            os.flush();
            os.close();

            conn.setRequestMethod("GET");

            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = "";

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }

            json = response.toString().trim();

            JSONObject result = new JSONObject(json);
            _id = result.getString("id");
            _token = result.getString("token");
            _firstName = result.getString("firstName");
            _lastName = result.getString("lastName");
            if (conn.getResponseCode() == 200){
                Toast.makeText(getBaseContext(), "Sesión iniciada corectamente", Toast.LENGTH_LONG).show();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra("Id",_id);
                i.putExtra("Token",_token);
                i.putExtra("FirstName",_firstName);
                i.putExtra("LastName",_lastName);
                startActivity(i);
            }else if(conn.getResponseCode() == 400){
                Toast.makeText(getBaseContext(), "Credenciales invalidas", Toast.LENGTH_LONG).show();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public boolean validate(){
        boolean valid = true;

        //Initial variables
        _email = findViewById(R.id.input_email);
        _password = findViewById(R.id.input_password);

        //Get values of variables
        String email = _email.getText().toString();
        String password = _password.getText().toString();


        //Validation Email
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _email.setError("El correo electrónico ingresado es incorrecto");
            valid = false;
        } else {
            _email.setError(null);
        }

        //Validation Password
        if (password.isEmpty() || password.length() < 8) {
            _password.setError("La contraseña debe tener un mínimo de 8 caracteres");
            valid = false;
        } else {
            _password.setError(null);
        }


        return valid;
    }
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

    }
}
