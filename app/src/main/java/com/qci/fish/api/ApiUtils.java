package com.qci.fish.api;

import com.qci.fish.BuildConfig;

/**
 * Created by raj on 3/16/2018.
 */

public class ApiUtils {

    private ApiUtils() {}

  //   public static final String BASE_URL = "https://hope.qcin.org:5002/api/";  // Live app url

   public static final String BASE_URL = BuildConfig.BASE_URL; // 13.233.90.40:5002

    // public static final String BASE_URL = "http://192.168.43.196:59017/api/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
