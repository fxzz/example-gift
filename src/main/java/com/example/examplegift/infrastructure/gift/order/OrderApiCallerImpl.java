package com.example.examplegift.infrastructure.gift.order;


import com.example.examplegift.common.response.CommonResponse;
import com.example.examplegift.domain.gift.GiftCommand;
import com.example.examplegift.domain.gift.order.OrderApiCaller;
import com.example.examplegift.domain.gift.order.OrderApiCommand;
import com.example.examplegift.infrastructure.retrofit.RetrofitUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderApiCallerImpl implements OrderApiCaller {
    private final RetrofitUtils retrofitUtils;
    private final RetrofitOrderApi retrofitOrderApi;

    @Override
    public String registerGiftOrder(OrderApiCommand.Register request) {
        var call = retrofitOrderApi.registerOrder(request);
        return retrofitUtils.responseSync(call)
                .map(CommonResponse::getData)
                .map(RetrofitOrderApiResponse.Register::getOrderToken)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void updateReceiverInfo(String orderToken, GiftCommand.Accept request) {
        var call = retrofitOrderApi.updateReceiverInfo(orderToken, request);
        retrofitUtils.responseVoid(call);
    }


    // Retrofit 이란 HTTP 클라이언트
    //   var call = retrofitOrderApi.registerOrder(request);만 실행했을때는
    //   작동을 안하고 retrofitUtils.responseSync(call)를 호출 해야 실행됨
    //   정확하게 responseSync 내부에 call.execute

    // Retrofit 사용 하려면 의존성 추가 후에
    // Retrofit 객체를 생성 해야함 (이 프로젝트 에서는 RetrofitUtils)
    // RetrofitUtils 에 설명 참고
}
