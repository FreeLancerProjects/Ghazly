package com.ghazly.services;


import com.ghazly.models.CategoryDataModel;
import com.ghazly.models.CityModel;
import com.ghazly.models.CreateOrderModel;
import com.ghazly.models.FoodListModel;
import com.ghazly.models.NeigboorModel;
import com.ghazly.models.OrderModel;
import com.ghazly.models.RestuarantDepartmentModel;
import com.ghazly.models.RestuarantModel;
import com.ghazly.models.SettingModel;
import com.ghazly.models.SingleRestaurantModel;
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

    @GET("api/category")
    Call<CategoryDataModel> getMainCategory(@Query("pagination") String pagination);
    @GET("api/cities")
    Call<CityModel> getMainCities();
    @GET("api/neighborhoods")
    Call<NeigboorModel> getNeigboor(@Query("city_id")String city_id);
    @GET("api/restaurant-departments")
    Call<RestuarantDepartmentModel> getrestaurantdepartments(@Query("pagination") String pagination,
                                                             @Query("restaurant_id") String restaurant_id


    );

    @GET("api/restaurant-by-cat")
    Call<RestuarantModel> getRestaurant(
            @Query("pagination") String pagination,
            @Query("category_id") String category_id,
            @Query("user_id") String user_id,
            @Query("limit_per_page") String limit_per_page,
            @Query("page") int page




    );
    @GET("api/restaurant-food-by-dep")
    Call<FoodListModel> getFoodList(
            @Query("pagination") String pagination,
            @Query("department_id") String department_id,
            @Query("restaurant_id") String restaurant_id,
            @Query("limit_per_page") String limit_per_page,
            @Query("page") int page




    );

    @GET("api/get-restaurant")
    Call<SingleRestaurantModel> getSingleAds(

            @Query("restaurant_id") String restaurant_id,
            @Query("user_id") String user_id
    );
    @POST("api/create-order")
    Call<ResponseBody> createOrder(@Header("Authorization") String user_token,
                                         @Body CreateOrderModel model
    );
    @GET("api/client-orders")
    Call<OrderModel> getOrders(@Header("Authorization") String user_token,
                               @Query("user_id") String user_id,
                               @Query("pagination") String pagination,
                               @Query("page") int page,
                               @Query("limit_per_page") int limit_per_page
    );

    @FormUrlEncoded
    @POST("/api/find-coupon")
    Call<UserModel> getCouponValue(@Header("Authorization") String user_token,
                                   @Field("coupon_num") String coupon_num

    );
    @FormUrlEncoded
    @POST("api/favorite-action")
    Call<ResponseBody> addFavoriteProduct(
            @Header("Authorization") String Authorization,
            @Field("restaurant_id") String restaurant_id,
            @Field("user_id") String user_id
            )
            ;

}