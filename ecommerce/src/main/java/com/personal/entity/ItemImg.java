package com.personal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "item_img")
@Getter @Setter
public class ItemImg extends BaseEntity {

    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName; // 이미지 파일명
    private String originImgName;   // 원본 이미지 파일명
    private String imgUrl;  // 이미지 조회 경로
    private String representImg;    // 대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemImg(String originImgName, String imgName, String imgUrl) {
        this.originImgName = originImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
