package com.example.examplegift.domain.gift.order;

import com.example.examplegift.domain.gift.GiftCommand;

public interface OrderApiCaller {
    String registerGiftOrder(OrderApiCommand.Register request);

    void updateReceiverInfo(String orderToken, GiftCommand.Accept request);
}
