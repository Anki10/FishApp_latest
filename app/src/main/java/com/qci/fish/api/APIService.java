package com.qci.fish.api;



import com.qci.fish.RoomDataBase.sample.SampleEntity;
import com.qci.fish.pojo.ImageUploadResponse;
import com.qci.fish.pojo.LoginRequest;
import com.qci.fish.pojo.LoginResponse;
import com.qci.fish.pojo.SampleSyncResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by raj on 3/16/2018.
 */

public interface APIService {

    @POST("tokens")
    Call<LoginResponse> loginrequest(@Header("Content-Type") String Content_Type, @Body LoginRequest request);

    @PUT("sampleinfo_app")
    Call<SampleSyncResponse>DataSync(@Header("Content-Type") String Content_Type, @Header("Authorization") String Authorization, @Body SampleEntity pojo);

    @Multipart
    @POST("upload/s3")
    Call<ImageUploadResponse>ImageUploadRequest(@Header("Authorization") String Authorization, @Part MultipartBody.Part file);

    @GET
    Call<SampleEntity> getSampleInfo(@Header("Content-Type") String Content_Type, @Header("Authorization") String Authorization, @Url String url);

    @GET
    Call<SampleEntity>getSampleId(@Header("Content-Type") String Content_Type, @Header("Authorization") String Authorization, @Url String url);


}
