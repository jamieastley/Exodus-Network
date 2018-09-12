package com.jastley.exodusnetwork.api;

import com.google.gson.JsonElement;

import com.jastley.exodusnetwork.BuildConfig;
import com.jastley.exodusnetwork.api.models.*;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
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
    Observable<AccessToken> getAccessToken(
        @Field("client_id") String clientId,
        @Field("client_secret") String clientSecret,
        @Field("grant_type") String grantType,
        @Field("code") String code
    );

    //Renew access_token
    @Headers("Accept: application/json")
    @POST("/Platform/App/OAuth/Token/")
    @FormUrlEncoded
    Observable<AccessToken> renewAccessToken(
        @Field("client_id") String clientId,
        @Field("client_secret") String clientSecret,
        @Field("grant_type") String grantType,
        @Field("refresh_token") String refreshToken
    );

    /** CHARACTER/ACCOUNT ENDPOINTS **/

    //Get membership data across all platforms for member
    @GET("/Platform/User/GetMembershipsForCurrentUser/")
    Observable<Response_GetCurrentUser> getMembershipsCurrentUser();

    //Get all characters
    @GET("/Platform/Destiny2/{membershipType}/Profile/{membershipId}/?components=200")
    Observable<JsonElement> getAllCharacters(@Path("membershipType") String membershipType,
                                 @Path("membershipId") String membershipId);

    //Get all characters (test)
    @GET("/Platform/Destiny2/{membershipType}/Profile/{membershipId}/?components=200")
    Observable<Response_GetAllCharacters> getAllCharactersGson(@Path("membershipType") String membershipType,
                                             @Path("membershipId") String membershipId);

    //Get character inventory(?components=201), with item instance data(?components=300), and equipped items(?components=205)
    @GET("/Platform/Destiny2/{membershipType}/Profile/{membershipId}/Character/{characterId}/?components=201&components=300&components=205")
    Observable<Response_GetCharacterInventory> getCharacterInventory(@Path("membershipType") String membershipType,
                                                                     @Path("membershipId") String membershipId,
                                                                     @Path("characterId") String characterId);

    //Get vault inventory, with instance data(?components=300)
    @GET("/Platform/Destiny2/{membershipType}/Profile/{membershipId}/?components=102&components=300")
    Observable<Response_GetCharacterInventory> getVaultInventory(@Path("membershipType") String membershipType,
                                                                 @Path("membershipId") String membershipId);

    //HistoricalStatsForAccount
    @GET("/Platform/Destiny2/{membershipType}/Account/{membershipId}/Stats/")
    Observable<Response_GetHistoricalStatsAccount> getHistoricalStatsAccount(@Path("membershipType") String membershipType,
                                                                             @Path("membershipId") String membershipId);

    //Mode-specific account-wide stats for account (0 required for entire account)
    @GET("/Platform/Destiny2/{membershipType}/Account/{membershipId}/Character/0/Stats/")
    Observable<Response_GetAllModesAccountStats> getAllModesAccountStats(@Path("membershipType") String membershipType,
                                                                         @Path("membershipId") String membershipId);

    //Get Clan Data
    @GET("/Platform/GroupV2/User/{membershipType}/{membershipId}/0/1/")
    Observable<Response_GetGroupsForMember> getClanData(@Path("membershipType") String membershipType, @Path("membershipId") String membershipId);

    //Get Faction progression for character(?components=202)
    @GET("/Platform/Destiny2/{membershipType}/Profile/{membershipId}/Character/{characterId}/?components=202")
    Observable<Response_FactionProgression> getFactionProgress(@Path("membershipType") String membershipType,
                                                               @Path("membershipId") String membershipId,
                                                               @Path("characterId") String characterId);

    //Fireteams/LFG
    @GET("/Platform/Fireteam/Search/Available/{platform}/{activityType}/{dateRange}/{slotFilter}/{page}/")
    Observable<Response_GetPublicFireteams> getPublicFireteams(@Path("platform") String platform,
                                                               @Path("activityType") String activityType,
                                                               @Path("dateRange") String dateRange,
                                                               @Path("slotFilter") String slotFilter,
                                                               @Path("page") String page);


    //Checklists - Account
    @GET("/Platform/Destiny2/{membershipType}/Profile/{membershipId}?components=104")
    Observable<Response_GetChecklists> getProfileChecklists(@Path("membershipType") String membershipType,
                                                            @Path("membershipId") String membershipId);

    //Checklists - Character
    @GET("Platform/Destiny2/{membershipType}/Profile/{membershipId}/Character/{characterId}?components=202")
    Observable<Response_GetCharacterChecklist> getCharacterChecklists(@Path("membershipType") String membershipType,
                                                                      @Path("membershipId") String membershipId,
                                                                      @Path("characterId") String characterId);


    /** ITEM MANAGEMENT **/

    //Transfer item to vault/character
    @POST("/Platform/Destiny2/Actions/Items/TransferItem/")
    Observable<Response_TransferEquipItem> transferItem(@Body TransferItemRequestBody transferBody);

    @POST("/Platform/Destiny2/Actions/Items/EquipItem/")
    Observable<Response_TransferEquipItem> equipItem(@Body EquipItemRequestBody equipBody);

    //Pull item from Postmaster
    @POST("/Platform/Destiny2/Actions/Items/PullFromPostmaster/")
    Observable<Response_TransferEquipItem> pullFromPostmaster(@Body PostmasterTransferRequest postmasterBody);

    //SetItem lock state
    @POST("/Platform/Destiny2/Actions/Items/SetLockState/")
    Observable<Response_TransferEquipItem> setLockState(@Body ItemLockStateBody lockStateBody);



    /** MANIFEST DATA **/

    //Get destiny.plumbing homepage to check manifest version/date (full URL overrides baseURL)
    @GET("https://destiny.plumbing/")
    Observable<Response_DestinyPlumbing> getDestinyPlumbing();

    //Get FactionDefinition data
    @GET("https://destiny.plumbing/en/raw/DestinyFactionDefinition.json")
    Observable<JsonElement> getFactionDefinitions();

    //Collectible Items/Weapons/Armor
    //@GET("reducedCollectibleInventoryItems.json")
    @GET("raw/DestinyInventoryItemDefinition.json")
    Observable<JsonElement> getCollectiblesDatabase();

    //Official Bungie Manifests
    @GET("/Platform/Destiny2/Manifest/")
    Observable<Response_GetBungieManifest> getBungieManifests();

    //Download .content manifest file
    @Streaming
    @GET
    Observable<ResponseBody> downloadUrlContent(@Url String fileUrl);


    /***** XUR ******/


    //Get Weekly Xur stock
//    @GET("/api/?request=history&for=xur")
//    @Headers("X-Api-Key: " + BuildConfig.braytechApiKey)
    @GET("https://api.braytech.org/")
    Observable<Response_GetXurWeekly> getXurWeeklyInventory(@Query("request") String request,
                                                            @Query("get") String get);

    @GET("/Platform/Destiny2/{membershipType}/Profile/{membershipId}/Character/{characterId}/Vendors/{vendorHash}/?components=400,402")
    Observable<Response_GetVendor> getVendorData(@Path("membershipType") String membershipType,
                                                 @Path("membershipId") String membershipId,
                                                 @Path("characterId") String characterId,
                                                 @Path("vendorHash") String vendorHash);

    /** ITEM INSTANCE INSPECT **/
    @GET("/Platform/Destiny2/{membershipType}/Profile/{membershipId}/Item/{itemInstanceId}/?components=302,304,305,307")
    Observable<Response_ItemInstance> getItemInstanceData(@Path("membershipType") String membershipType,
                                                          @Path("membershipId") String membershipId,
                                                          @Path("itemInstanceId") String itemId);


    /**** MILESTONES ***/

    @GET("/Platform/Destiny2/Milestones/")
    Observable<Response_GetMilestones> getMilestones();
}
