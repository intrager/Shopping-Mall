package com.personal.controller;

import com.personal.domain.CartItemDto;
import com.personal.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartItemDto cartItemDto,
                                              BindingResult bindingResult, Principal principal) {
        /** 장바구니에 담을 상품의 정보를 받는 cartItemDto 객체에 데이터 바인딩 시 에러가 있는지 검사함 */
        if(bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String email = principal.getName(); // 현재 로그인한 회원의 이메일 정보를 변수에 저장
        Long cartItemId;

        /** 화면으로부터 넘어온 장바구니에 담을 상품 정보와 현재 로그인한 회원의 이메일 정보를 이용하여
         * 장바구니에 상품을 담는 로직을 호출함 */
        try {
            cartItemId = cartService.addCart(cartItemDto, email);
        } catch(Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }
}
