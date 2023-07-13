package com.example.examplegift.infrastructure.gift;


import com.example.examplegift.common.exception.InvalidParamException;
import com.example.examplegift.domain.gift.Gift;
import com.example.examplegift.domain.gift.GiftStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GiftStoreImpl implements GiftStore {
    private final GiftRepository giftRepository;

    @Override
    public Gift store(Gift gift) {
        if (gift == null) throw new InvalidParamException();
        return giftRepository.save(gift);
    }
}
