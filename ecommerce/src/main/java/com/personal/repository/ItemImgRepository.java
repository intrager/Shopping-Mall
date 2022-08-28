package com.personal.repository;

import com.personal.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);
    ItemImg findByItemIdAndRepresentImg(Long itemId, String representImg);
}
