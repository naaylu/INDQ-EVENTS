package com.example.indq_events;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.http.multipart.MultipartEntity;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EventImageActivity extends AppCompatActivity {
    String title = null;
    String description = null;
    ImageView image;
    String _firstName,_lastName,_token,_file;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 10;
    String imageFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_image);

        //Data of introduction event
        Bundle extras = getIntent().getExtras();
        if (extras  != null) {
            _firstName= extras.getString("FirstName");
            _lastName=extras.getString("LastName");
            _token=extras.getString("Token");
            this.title= extras.getString("Title");
            this.description = extras.getString("Description");
        }

        //actionBar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Paso 2.Imagen");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#10618E")));

        image = findViewById(R.id.imageEvent);
    }
    public void clickImage(View view) {
        loadImage();
    }
    private void loadImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            image.setImageURI(path);
            imageFile = getPath(path);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:
                uploadImage(imageFile);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    uploadImage(imageFile);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    private String getPath(Uri path) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(path, filePathColumn, null, null, null);

        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

        String sPath = cursor.getString(columnIndex);

        cursor.close();
        return sPath;
    }

    private void uploadImage(String filePath) {
        Retrofit retrofit = NetworkClient.getRetrofitClient(this);

        EventApi api = retrofit.create(EventApi.class);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            imageFile = filePath;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        } else  {
            File file = new File(filePath);

            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);

            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);

            Call call = api.uploadImage(_token, part);

            call.enqueue(new Callback<ImageResponse>() {
                @Override
                public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                    _file= response.body().getFileName();
                    Intent i = new Intent(EventImageActivity.this, EventDateActivity.class);
                    i.putExtra("FirstName",_firstName);
                    i.putExtra("LastName",_lastName);
                    i.putExtra("Token",_token);
                    i.putExtra("Title",EventImageActivity.this.title);
                    i.putExtra("Description",EventImageActivity.this.description);
                    i.putExtra("Image",_file);
                    startActivity(i);
                    Log.d("RESPONSE", response.body().getFileName());
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    t.printStackTrace();
                    Log.d("ERROR", t.getMessage());
                    Log.d("STACK", Log.getStackTraceString(t));
                }
            });
        }

    }
}
