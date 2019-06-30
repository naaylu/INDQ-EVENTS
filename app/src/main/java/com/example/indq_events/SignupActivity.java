package com.example.indq_events;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class SignupActivity extends AppCompatActivity {
    Button btnlogin, btnconfirm;
    EditText _name,_lastName,_email,_password,_repeatPassword;
    RadioButton _female;
    String estado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Click confirm
        btnconfirm = findViewById(R.id.btn_confirmSignup);
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        //Navigation to Login
        btnlogin = findViewById(R.id.btn_layoutLogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        //Get values of Radiobuttons
        _female = findViewById(R.id.radio_femenino);
        if(_female.isChecked()){
            estado = "female";
        }else{
            estado = "male";
        }

    }
    // SIGNUP
    public void signup() {
        if (!validate()) {
            onSignupFailed();
            return;
        }
        else {
            getData();
        }
    }
    public void getData(){
        String sql = "http://api.events.indqtech.com/users";
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
            obj.put("firstName",_name.getText().toString());
            obj.put("lastName",_lastName.getText().toString() );
            obj.put("email", _email.getText().toString());
            obj.put("password", _password.getText().toString());
            obj.put("gender",estado);
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(obj.toString());
            os.flush();
            os.close();
            if (conn.getResponseCode() == 200){
                Toast.makeText(getBaseContext(), "Usuario registrado correctamente", Toast.LENGTH_LONG).show();
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }else if(conn.getResponseCode() == 403){
                Toast.makeText(getBaseContext(), "La cuenta con ese correo electrónico ya existe", Toast.LENGTH_LONG).show();
            }else if(conn.getResponseCode() == 400){
                Toast.makeText(getBaseContext(), "Datos invalidos", Toast.LENGTH_LONG).show();
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
        _name = findViewById(R.id.input_name);
        _lastName = findViewById(R.id.input_lastName);
        _email = findViewById(R.id.input_email);
        _password = findViewById(R.id.input_password);
        _repeatPassword = findViewById(R.id.input_repeatPassword);

        //Get values of variables
        String name = _name.getText().toString();
        String lastName = _lastName.getText().toString();
        String email = _email.getText().toString();
        String password = _password.getText().toString();
        String repeatPassword = _repeatPassword.getText().toString();


        //Validation Name
        if (name.isEmpty()) {
            _name.setError("Nombre vacio");
            valid = false;
        } else {
            _name.setError(null);
        }

        //Validation Name
        if (lastName.isEmpty()) {
            _lastName.setError("Apellido vacio");
            valid = false;
        } else {
            _lastName.setError(null);
        }

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

        //Validation Confirm Password
        if (password.equals(repeatPassword)){
            _repeatPassword.setError(null);
        }
        else {
            _repeatPassword.setError("La contraseña no coincide");
            valid = false;
        }

        return valid;
    }
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

    }

}
