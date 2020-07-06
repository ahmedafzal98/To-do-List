//package com.example.volleylist;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.http.Body;
//import retrofit2.http.Field;
//import retrofit2.http.FormUrlEncoded;
//import retrofit2.http.GET;
//import retrofit2.http.POST;
//import retrofit2.http.Query;
//
//public interface GetDataService {
//    @GET("user.php")
//    Call<List<JsonDataList>> getAllData();
//
//
//    @POST("user.php")
//    Call<JsonDataList> createPost(@Body JsonDataList jsonDataList);
//
//   @FormUrlEncoded
//    @POST("user.php")
//    Call<JsonDataList> createPost(
//            @Field("userId") String userId,
//            @Field("title") String title,
//            @Field("body") String body
//   );
//
//
//}
