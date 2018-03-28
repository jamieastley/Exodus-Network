package com.jastley.warmindfordestiny2.api;

import com.google.gson.JsonElement;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONObject;

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
    @POST("/Platform/App/OAuth/Token/")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
        @Field("client_id") String clientId,
        @Field("client_secret") String clientSecret,
        @Field("grant_type") String grantType,
        @Field("code") String code
    );

    //Renew access_token
    @Headers("Accept: application/json")
    @POST("/Platform/App/OAuth/Token/")
    @FormUrlEncoded
    Call<AccessToken> renewAccessToken(
        @Field("client_id") String clientId,
        @Field("client_secret") String clientSecret,
        @Field("grant_type") String grantType,
        @Field("refresh_token") String refreshToken
    );

    //GetCurrentUser
    @GET("/Platform/User/GetMembershipsForCurrentUser/")
    Call<Response_GetCurrentUser> getCurrentUser();

    //Get profile summary
    @GET("/Platform/Destiny2/2/Profile/4611686018428911554/?components=200")
    Call<JsonElement> getProfile();//(@Path("console") String consoleId, @Path("membershipId") String membershipId);

    //Get character inventory
    @GET("/Platform/Destiny2/{membershipType}/Profile/{membershipId}/Character/{characterId}/?components=201")
    Call<Response_GetCharacterInventory> getCharacterInventory(@Path("membershipType") String membershipType, @Path("membershipId") String membershipId, @Path("characterId") String characterId);
}
