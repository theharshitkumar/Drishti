package com.example.Drishti;



import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Part;
//import retrofit2.http.body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;


public interface UserClient {
    @Multipart
    @POST("predict_general_object")

    Call<ServerResponse>uploadPhoto_general(
            @Part MultipartBody.Part photo
            );

    @Multipart
    @POST("predict_currency")

    Call<ServerResponse>uploadPhoto_currency(
            @Part MultipartBody.Part photo
    );
}