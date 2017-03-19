package com.nut.nwhacks.network;

/**
 * Created by skelly on 3/18/17.
 */

import io.moj.java.sdk.model.VehicleMeasure;
import io.moj.java.sdk.model.response.ListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Retrofit interface for the Mojio REST API.
 * https://docs.moj.io/#/document/view/doc_android_model
 */
public interface MojioApi {
/*
    @GET("vehicles")
    Call<ListResponse<Vehicle>> getVehicles();

    @GET("vehicles/{id}")
    Call<Vehicle> getVehicle(@Path("id") String id);

    @PUT("vehicles/{id}")
    Call<Vehicle> updateVehicle(@Path("id") String id, @Body Vehicle vehicle);

    @GET("vehicles/{id}/history/states")
    Call<ListResponse<VehicleMeasure>> getTripHistory(@Path("id") String id);
*/
    @GET("vehicles/{id}/history/states")
    Call<ListResponse<VehicleMeasure>> getTripHistory(@Path("id") String id, @Query("top") int top);

}
