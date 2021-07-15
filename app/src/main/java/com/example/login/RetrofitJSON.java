package com.example.login;

import android.provider.SyncStateContract;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitJSON {

    //Probar si se reciben datos de la API
    @GET("http://testandroid.macropay.com.mx")
    Call<PostResponse> getPostResponse();


    @POST("http://testandroid.macropay.com.mx")
    Call<PostResponse> createPost(@Body RequestBody body);

}
