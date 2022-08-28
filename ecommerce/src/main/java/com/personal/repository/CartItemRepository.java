package com.personal.repository;

import com.personal.domain.CartDetailDto;
import com.personal.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    /** 생성자의 파라미터 순서는 DTO 클래스에 명시한 순으로 넣어야 함 */
    @Query("select new com.personal.domain.CartDetailDto(ci.id, i.itemName, i.price," +
            "ci.count, im.imgUrl) " +
        "from CartItem ci, ItemImg im " +
        "join ci.item i " +
        "where ci.cart.id = :cartId " +
        "and im.item.id = ci.item.id " +
        "and im.representImg = 'Y' " +  // 상품의 대표 이미지만 가지고 옴
        "order by ci.regDate desc"
    )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}
