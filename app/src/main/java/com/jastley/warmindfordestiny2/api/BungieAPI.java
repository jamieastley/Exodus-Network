package com.jastley.warmindfordestiny2.api;

import com.google.gson.JsonElement;

import com.jastley.warmindfordestiny2.api.models.EquipItemRequestBody;
import com.jastley.warmindfordestiny2.api.models.TransferItemRequestBody;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.*;

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

    //Get character inventory, with instance data(?components=300)
    @GET("/Platform/Destiny2/{membershipType}/Profile/{membershipId}/Character/{characterId}/?components=201&components=300")
    Observable<Response_GetCharacterInventory> getCharacterInventory(@Path("membershipType") String membershipType, @Path("membershipId") String membershipId, @Path("characterId") String characterId);

    //Get vault inventory, with instance data(?components=300)
    @GET("/Platform/Destiny2/{membershipType}/Profile/{membershipId}/?components=102&components=300")
    Observable<Response_GetCharacterInventory> getVaultInventory(@Path("membershipType") String membershipType, @Path("membershipId") String membershipId);

    //Get destiny.plumbing homepage to check manifest version/date
    @GET("https://destiny.plumbing/")
    Observable<Response_DestinyPlumbing> getDestinyPlumbing();

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

    //Transfer item to vault/character
    @POST("/Platform/Destiny2/Actions/Items/TransferItem/")
    Observable<Response_TransferItem> transferItem(@Body TransferItemRequestBody transferBody);

    @POST("/Platform/Destiny2/Actions/Items/EquipItem/")
    Observable<Response_TransferItem> equipItem(@Body EquipItemRequestBody equipBody);


}
