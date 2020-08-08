package com.ghazly.services;


import com.ghazly.models.CategoryDataModel;
import com.ghazly.models.CityModel;
import com.ghazly.models.CreateOrderModel;
import com.ghazly.models.FavouriteRestuarantModel;
import com.ghazly.models.FoodListModel;
import com.ghazly.models.NeigboorModel;
import com.ghazly.models.OrderDataModel;
import com.ghazly.models.RestuarantDepartmentModel;
import com.ghazly.models.RestuarantModel;
import com.ghazly.models.SettingModel;
import com.ghazly.models.SingleOrderModel;
import com.ghazly.models.SingleRestaurantModel;
import com.ghazly.models.UserModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
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
    Call<NeigboorModel> getNeigboor(@Query("city_id") String city_id);

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

    @GET("api/search-restaurant")
    Call<RestuarantModel> getRestaurantfilter(
            @Query("pagination") String pagination,
            @Query("city_id") String city_id,
            @Query("neighbor_id") String neighbor_id,
            @Query("user_id") String user_id,
            @Query("limit_per_page") String limit_per_page,
            @Query("page") int page


    );

    @GET("api/my-favorites")
    Call<FavouriteRestuarantModel> getMyFavoriteProducts(
            @Header("Authorization") String Authorization,
            @Query("pagination") String pagination
    )
            ;

    @GET("api/search-restaurant")
    Call<RestuarantModel> Search(@Query("pagination") String pagination,
                                 @Query("user_id") int user_id,
                                 @Query("name") String search_name);

    @GET("api/search-food")
    Call<FoodListModel> getFoodList(
            @Query("pagination") String pagination,
            @Query("department_id") String department_id,
            @Query("restaurant_id") String restaurant_id,
            @Query("limit_per_page") String limit_per_page,
            @Query("page") int page,
            @Query("title") String title);


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
    Call<OrderDataModel> getOrders(@Header("Authorization") String user_token,
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

    @GET("api/one-order")
    Call<SingleOrderModel> getOrdersById(@Header("Authorization") String user_token,
                                         @Query("order_id") String order_id

    );

    @FormUrlEncoded
    @POST("api/cancel-order")
    Call<ResponseBody> DelteOrders(
            @Header("Authorization") String user_token,
            @Field("branch_id") String branch_id,
            @Field("order_id") String order_id,
            @Field("restaurant_id") String restaurant_id,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("api/update-profile-client")
    Call<UserModel> editprofile(
            @Header("Authorization") String user_token,
            @Field("name") String name

    );
}