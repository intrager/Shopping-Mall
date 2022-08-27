package com.personal.service;

import com.personal.domain.ItemFormDto;
import com.personal.entity.Item;
import com.personal.entity.ItemImg;
import com.personal.repository.ItemImgRepository;
import com.personal.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        /** 상품 등록
         * 상품 등록 폼으로부터 입력 받은 데이터를 이용하여 item 객체를 생성
         */
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        // 이미지 등록
        for(int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            /** 첫 번째 이미지일 경우, 대표 상품 이미지 여부 값을 Y로 세팅.
             *  나머지는 N으로 설정
             */
            if(i == 0)
                itemImg.setRepresentImg("Y");
            else
                itemImg.setRepresentImg("N");
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }
        return item.getId();
    }
}
