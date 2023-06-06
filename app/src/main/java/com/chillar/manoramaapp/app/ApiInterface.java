package com.chillar.manoramaapp.app;

import com.chillar.manoramaapp.Retrofit.DashBoard.DashBoardDetails;
import com.chillar.manoramaapp.Retrofit.DeleteOrder.DeleteDetails;
import com.chillar.manoramaapp.Retrofit.FoodSessions.FoodSessionsDetails;
import com.chillar.manoramaapp.Retrofit.Login.LoginDetails;
import com.chillar.manoramaapp.Retrofit.OrderHistory.OrderHistoryDetails;
import com.chillar.manoramaapp.Retrofit.OrderItemsList.OrderItemsDetails;
import com.chillar.manoramaapp.Retrofit.OutletsList.OutletsDetails;
import com.chillar.manoramaapp.Retrofit.PlaceOrdeList.PlaceOrdersList_Class;
import com.chillar.manoramaapp.Retrofit.PreorderItems.PreOrderDetails;
import com.chillar.manoramaapp.Retrofit.PreorderLists.PreOrderList_Class;
import com.chillar.manoramaapp.Retrofit.placeOrder.PlaceOrderDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
//    @GET("movie/top_rated")
//    Call<HomeResponse> getHomeResponse(@Query("api_key") String apiKey);


    @GET("{path}")
    Call<LoginDetails> getlogin(@Path("path") String path, @Query("employeeCode") String userPhone, @Query("userPassword") String userPassword);

    @GET("{path}")
    Call<DashBoardDetails> getcurrntmnthtransaction(@Path("path") String path, @Query("employeeCode") String userPhone, @Query("userID") String userID);

    @GET("{path}")
    Call<OrderHistoryDetails> getorderhistory(@Path("path") String path, @Query("employeeCode") String userPhone,
                                              @Query("userID") String userID, @Query("fromDate") String fromDate, @Query("toDate") String toDate);

    @GET("{path}")
    Call<PlaceOrdersList_Class> placeorderlists(@Path("path") String path, @Query("employeeCode") String employeeCode, @Query("userID") String userID, @Query("branchOfficeID") String branchOfficeID);


    @GET("{path}")
    Call<OutletsDetails> getoutlets(@Path("path") String path, @Query("employeeCode") String userPhone, @Query("userID") String userID, @Query("branchOfficeID") String branchOfficeID);

    @GET("{path}")
    Call<FoodSessionsDetails> getfoodsessions(@Path("path") String path, @Query("employeeCode") String userPhone, @Query("userID") String userID, @Query("hardwareModuleID") String hardwareModuleID);

    @GET("{path}")
    Call<OrderItemsDetails> getorderitems(@Path("path") String path, @Query("employeeCode") String userPhone, @Query("userID") String userID, @Query("itemSaleTransactionID") String itemSaleTransactionID);

    @GET("{path}")
    Call<PreOrderDetails> getpreorder(@Path("path") String path, @Query("employeeCode") String userPhone,
                                      @Query("userID") String userID, @Query("preorderSessionTimingID") int preorderSessionTimingID,
                                      @Query("hardwareModuleID") int hardwareModuleID, @Query("orderDate") String orderDate);

    @GET("{path}")
    Call<PreOrderList_Class> getpreorder_new(@Path("path") String path, @Query("employeeCode") String employeeCode,
                                             @Query("userID") String userID, @Query("preorderSessionTimingID")
                                                  int preorderSessionTimingID, @Query("orderDate")
                                                  String orderDate, @Query("hardwareModuleID") int hardwareModuleID);

    @GET("{path}")
    Call<PlaceOrderDetails> getplaceorder(@Path("path") String path, @Query("employeeCode") String userPhone,
                                          @Query("userID") String userID, @Query("hardwareModuleID") int hardwareModuleID,
                                          @Query("preorderSessionTimingID") int preorderSessionTimingID, @Query("orderDate")
                                                  String orderDate, @Query("itemID") String itemID, @Query("itemQnty")
                                                  String itemQnty);

    @GET("{path}")
    Call<DeleteDetails> getorderdelete(@Path("path") String path, @Query("employeeCode") String userPhone, @Query("userID") String userID, @Query("itemSaleTransactionID") String itemSaleTransactionID, @Query("itemID") String itemID);

}