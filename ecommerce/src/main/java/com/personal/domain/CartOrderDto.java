package com.personal.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartOrderDto {
    private Long cartItemId;
    private List<CartOrderDto> cartOrderDtoList; // 장바구니에서 여러 개의 상품을 주문하기 때문에 List<> 사용
}
