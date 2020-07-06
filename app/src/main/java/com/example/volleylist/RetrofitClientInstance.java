//package com.example.volleylist;
//
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class RetrofitClientInstance {
//
//    private static Retrofit retrofit;
//    private static String BASE_URL = "http://192.168.1.107:8888/android/include/v1/";
//    private static RetrofitClientInstance mInstance;
//
//    public static Retrofit getRetrofitInstance(){
//        if (retrofit == null){
//            retrofit = new retrofit2.Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .client( UnsafeOkHttpClient.getUnsafeOkHttpClient())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        return retrofit;
//    }
//    public static synchronized RetrofitClientInstance getInstance(){
//        if (mInstance == null){
//            mInstance = new RetrofitClientInstance();
//        }
//        return mInstance;
//    }
//    public GetDataService getApi(){
//        return retrofit.create(GetDataService.class);
//    }
//
//}
