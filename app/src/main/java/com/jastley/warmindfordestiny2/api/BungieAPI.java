package com.jastley.warmindfordestiny2.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    //Renew access_token
    @Headers("Accept: application/json")
    @POST("Platform/App/OAuth/Token")
    @FormUrlEncoded
    Call<AccessToken> renewAccessToken(
        @Field("grant_type") String grantType,
        @Field("refresh_token") String refreshToken
    );

    //GetCurrentUser
//    @Headers("X-API-Key: "  + apiKey.apiKey)
//    @GET("/Platform/User/GetMembershipsForCurrentUser")
    @GET("Platform/User/GetMembershipsForCurrentUser/")
    Call<Response_GetCurrentUser> getCurrentUser();

    //Get profile summary
//    @Headers("X-API-Key: " + apiKey.apiKey)
    @GET("/Platform/Destiny2/{console}/Profile/{membershipId}?components=100")
    Call<Response_GetProfile> getProfile(@Path("console") String consoleId, @Path("membershipId") String membershipId);

    //Get character inventory
    @GET("Platform/Destiny2/{membershipType}/Profile/{membershipId}/Character/{characterId}/?components=201")
    Call<Response_GetCharacterInventory> getCharacterInventory(@Path("membershipType") String membershipType, @Path("membershipId") String membershipId, @Path("characterId") String characterId);
}
