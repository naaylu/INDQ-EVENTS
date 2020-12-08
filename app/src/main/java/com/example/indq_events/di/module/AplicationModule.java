package com.example.indq_events.di.module;

import android.content.Context;
import android.util.Log;


import com.example.indq_events.BuildConfig;
import com.example.indq_events.data.local.auth.Auth;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module(includes = ViewModelModule.class)
public class AplicationModule {
    private static final String BASE_URL = BuildConfig.apiUrl;

    @Singleton
    @Provides
    static Retrofit provideRetrofit(final Auth auth, Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.interceptors().add(new ReadInterceptor());
        httpClient.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                String token = auth.getToken();
                Request request = chain.request().newBuilder().addHeader("Authorization", token).build();

                Response response = chain.proceed(request);
                ResponseBody body = response.peekBody(Long.MAX_VALUE);
                String sResponse = body.string();
                JsonObject jsonResponse = new JsonParser().parse(sResponse).getAsJsonObject();
                String status = jsonResponse.get("status").getAsString();

                return response;
            }
        });
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).client(httpClient.build())
                .build();
    }

   /* @Singleton
    @Provides
    static LoginService provideLoginService(Retrofit retrofit) {
        return retrofit.create(LoginService.class);
    }*/

    public static class ReadInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Log.i("ReadInterceptor", "inside intercept callback");
            Request request = chain.request();
            long t1 = System.nanoTime();
            String requestLog = String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers());
            if (request.method().compareToIgnoreCase("post") == 0) {
                requestLog = "\n" + requestLog + "\n" + bodyToString(request);
            }
            Log.d("TAG", "request" + "\n" + requestLog);
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            String responseLog = String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers());

            String bodyString = response.body().string();

            Log.d("TAG", "response only" + "\n" + bodyString);

            Log.d("TAG", "response" + "\n" + responseLog + "\n" + bodyString);

            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();

        }


        public static String bodyToString(final Request request) {
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "did not work";
            }
        }
    }
}

