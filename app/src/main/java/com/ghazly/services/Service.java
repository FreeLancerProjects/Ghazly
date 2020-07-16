package com.ghazly.services;


import com.ghazly.models.SettingModel;
import com.ghazly.models.UserModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Service {

    @GET("api/sttings")
    Call<SettingModel> getSetting();

    @FormUrlEncoded
    @POST("api/login-clint")
    Call<UserModel> login(@Field("phone_code") String phone_code,
                          @Field("phone") String phone

    );

    @FormUrlEncoded
    @POST("api/register-clint")
    Call<UserModel> signup(@Field("name") String name,
                           @Field("phone_code") String phone_code,
                           @Field("phone") String phone


    );
    @FormUrlEncoded
    @POST("api/update-firebase")
    Call<ResponseBody> updatePhoneToken(@Header("Authorization") String user_token,
                                        @Field("phone_token") String phone_token,
                                        @Field("user_id") int user_id,
                                        @Field("soft_type") String soft_type,
                                        @Field("user_type") String user_type

                                        );

    @FormUrlEncoded
    @POST("api/logout-client")
    Call<ResponseBody> logout(@Header("Authorization") String user_token,
                              @Field("phone_token") String phone_token,
                              @Field("user_id") int user_id,
                              @Field("soft_type") String soft_type


    );
}