package com.and.wodness.api;
/**
 * Created by Ivan on 8/24/2017.
 */


import com.and.wodness.CustomEditText;
import com.and.wodness.model.LoginRequestInfo;
import com.and.wodness.model.ModifyRequestInfo;
import com.and.wodness.model.RegisterRequestInfo;
import com.and.wodness.model.ResetRequestInfo;
import com.and.wodness.model.UpdateRequestInfo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;
import rx.Observable;



public interface WodnessAPIService {
    @POST("api/user_login")
    Observable<LoginResponse> login(@Body LoginRequestInfo loginInfo);
    @POST("api/register")
    Observable<RegisterResponse> register(@Body RegisterRequestInfo registerInfo);
    @POST("api/reset")
    Observable<ResetResponse> reset(@Body ResetRequestInfo resetInfo);
    @POST("api/update_profile")
    Observable<UpdateResponse> update(@Body UpdateRequestInfo updateInfo);
    @Multipart
    @POST("api/fileUpload")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("email") RequestBody email);

    @GET
    Call<FBProfilePictureRedirectResponse> fbProfilePictureLink(@Url String profilePicUrl);

    @POST("api/modify_pw")
    Observable<ModifyResponse> modify(@Body ModifyRequestInfo modifyInfo);

//    @Multipart
//    @POST("api/fileUpload")
//    Call<UploadResponse> uploadImage(@Part MultipartBody.Part image);
//    @POST("api/register")
//    Observable<RegisterResponse> register(@Body RegisterRequestInfo registerInfo);
//    @GET("/api/user_login/{userid}")
//    Observable<SettingResponse> getSettingInfo(@Path("userid") String uid);

//    @GET("/enrich_mobile/WzWpdatatable4/getSettingsNew/{userid}")
//    Observable<SettingResponse> getSettingInfo(@Path("userid") String uid);
//
//    @POST("/enrich_mobile/WzWpdatatable4/updateSettingNew")
//    Observable<SettingResponse> updateSettingInfo(@Body SettingInfo settingInfo);
//
//    @GET("/enrich_mobile/DapUsers/getAvailableCardsTest/{userid}")
//    Observable<GetMyContactsResponse> getMyContacts(@Path("userid") String uid);
//
//    @Multipart
//    @POST("user/updateprofile")
//    Observable<ResponseBody> uploadAudioFile(@Part MultipartBody.Part audioFile);

//    @POST("/hooks/catch/772315/t2hhp8/")
//    Call<ResponseBody> sendSmsMsgTestNow(@Body SmsMsgTestNowRequestBody body);
//
//
//
//    @GET("/pages/upload_from_bucket.php?")
//    Call<ResponseBody> sendTranscription(@Query("Message") String message);
//
//    @GET("/private/setting/pages/activate_mvm_campaign.php?")
//    Call<ResponseBody> activateMVMCampaign(@Query("user_id") String userId, @Query("audio_filename") String audioFileName);
//
//    @GET("/enrich_mobile/HtJobs/deleteContact/{deleteIndexString}")
//    Observable<DeleteContactResponse> deleteContacts(@Path("deleteIndexString") String indexes);
//
//    @GET("/{account_id}/{fileName}")
//    Call<ResponseBody> downloadAudioFileUrlAsync(@Path("account_id") String strAccountId, @Path("fileName") String fileName);
//
//    @GET("/enrich-scanner/{fileName}")
//    Call<ResponseBody> downloadScannerImageFileUrlAsync(@Path("fileName") String fileName);

}