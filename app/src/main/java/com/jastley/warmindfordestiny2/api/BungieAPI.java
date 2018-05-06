package com.jastley.warmindfordestiny2.api;

import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by jamie1192 on 15/03/2018.
 */

public interface BungieAPI {

    String baseURL = "https://www.bungie.net";
    String plumbingURL = "https://destiny.plumbing/en/";

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
    @GET("/Platform/Destiny2/{membershipType}/Profile/{membershipId}/?components=200")
    Call<JsonElement> getProfile(@Path("membershipType") String membershipType, @Path("membershipId") String membershipId);

    //Get character inventory
    @GET("/Platform/Destiny2/{membershipType}/Profile/{membershipId}/Character/{characterId}/?components=201&components=300")
    Observable<Response_GetCharacterInventory> getCharacterInventory(@Path("membershipType") String membershipType, @Path("membershipId") String membershipId, @Path("characterId") String characterId);

    //Collectable Items/Weapons/Armor
//    @GET("reducedCollectableInventoryItems.json")
    @GET("raw/DestinyInventoryItemDefinition.json")
    Call<JsonElement> getCollectablesDatabase();

    //HistoricalStatsForAccount
    @GET("/Platform/Destiny2/{membershipType}/Account/{membershipId}/Stats/")
    Observable<Response_GetHistoricalStatsAccount> getHistoricalStatsAccount(@Path("membershipType") String membershipType, @Path("membershipId") String membershipId);


    //Get Clan Data
    @GET("/Platform/GroupV2/User/{membershipType}/{membershipId}/0/1/")
    Observable<Response_GetGroupsForMember> getClanData(@Path("membershipType") String membershipType, @Path("membershipId") String membershipId);
}
