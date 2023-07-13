package com.example.examplegift.domain.gift;

public interface GiftReader {
    Gift getGiftBy(String giftToken);

    Gift getGiftByOrderToken(String orderToken);
}
