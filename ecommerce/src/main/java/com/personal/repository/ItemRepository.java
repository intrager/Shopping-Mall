package com.personal.repository;

import com.personal.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>,
        QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {
    List<Item> findByItemName(String itemName);
    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);
    List<Item> findByPriceLessThan(Integer price);
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    @Query(value = "select * from item i where i.item_detail like" +
            " %:itemDetail% order by i.price desc", nativeQuery = true) // 오류 오지는데 이게 되네
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
}
