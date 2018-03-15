package com.jastley.warmindfordestiny2.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by jastl on 15/03/2018.
 */

public interface BungieAPI {

    //OAuth
    @Headers("Accept: application/json")
    @POST("Platform/App/OAuth/Token/")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType,
            @Field("code") String code
    );


//    Get profile summary
//    @Headers("X-API-Key: " + apiKey.apiKey)
//    @GET("/Platform/Destiny2/{console}/Profile/{membershipId}?components=100")
//    Call<BungieResponse> getFoundPlayer(@Path("console") String consoleId, @Path("membershipId") String membershipId);


}
