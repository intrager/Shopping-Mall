package com.personal.service;

import com.personal.domain.CartItemDto;
import com.personal.entity.Cart;
import com.personal.entity.CartItem;
import com.personal.entity.Item;
import com.personal.entity.Member;
import com.personal.repository.CartItemRepository;
import com.personal.repository.CartRepository;
import com.personal.repository.ItemRepository;
import com.personal.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Long addCart(CartItemDto cartItemDto, String email) {
        /** 장바구니에 담을 상품 엔티티 조회 */
        Item item = itemRepository.findById(cartItemDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        /** 현재 로그인한 회원 엔티티 조회 */
        Member member = memberRepository.findByEmail(email);

        /** 현재 로그인한 회원의 장바구니 엔티티 조회 */
        Cart cart = cartRepository.findByMemberId(member.getId());
        /** 처음으로 장바구니에 상품을 담을 경우 해당 회원의 장바구니 엔티티 생성 */
        if(cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        /** 넣으려는 상품이 장바구니에 이미 들어가있는지 조회 */
        CartItem savedCartItem =
                cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());
        /** 장바구니에 이미 있던 상품일 경우, 기존 수량에 현재 장바구니에 담을 수량 만큼을 더함 */
        if(savedCartItem != null) {
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
        } else {
            /** 장바구니 엔티티, 상품 엔티티, 장바구니에 담을 수량을 이용하여 CartItem 엔티티 생성 */
            CartItem cartItem =
                    CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }
}
