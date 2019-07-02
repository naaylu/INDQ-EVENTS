package com.example.indq_events;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface EventApi {
    @Multipart
    @POST("/images")
    Call<ImageResponse> uploadImage(@Header("Authorization") String authToken, @Part MultipartBody.Part file);
}