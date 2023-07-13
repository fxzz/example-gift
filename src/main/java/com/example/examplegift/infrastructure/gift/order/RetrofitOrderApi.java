package com.example.examplegift.infrastructure.gift.order;


import com.example.examplegift.common.response.CommonResponse;
import com.example.examplegift.domain.gift.GiftCommand;
import com.example.examplegift.domain.gift.order.OrderApiCommand;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitOrderApi {

    //POST 요청으로 api/v1/gift-orders/init 를 호출할것이고 요청은 @RequestBody 방식으로 사용하고 있고 리턴값은 Register 을 받는다
    //Call 객체는 Retrofit 에서 재공하는 응답을 받아주는 객체
    @POST("api/v1/gift-orders/init")
    Call<CommonResponse<RetrofitOrderApiResponse.Register>> registerOrder(@Body OrderApiCommand.Register request);

    @POST("api/v1/gift-orders/{orderToken}/update-receiver-info")
    Call<Void> updateReceiverInfo(@Path("orderToken") String orderToken, @Body GiftCommand.Accept request);
}
