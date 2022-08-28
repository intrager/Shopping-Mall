package com.personal.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartItemDto {
    @NotNull(message = "상품은 필수로 들어가야 합니다")
    private Long itemId;

    @Min(value = 1, message = "최소 한 개 이상 담아주십시오")
    private int count;
}
