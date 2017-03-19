package com.nut.nwhacks.network;

/**
 * Created by skelly on 3/18/17.
 */


import io.moj.java.sdk.model.Vehicle;
import io.moj.java.sdk.model.response.ListResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Retrofit interface for the Mojio REST API.
 * https://docs.moj.io/#/document/view/doc_android_model
 */
public interface MojioApi {

    @GET("vehicles")
    Call<ListResponse<Vehicle>> getVehicles();

}
