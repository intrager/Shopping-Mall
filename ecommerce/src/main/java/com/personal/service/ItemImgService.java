package com.personal.service;

import com.personal.entity.ItemImg;
import com.personal.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemImgService {
    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        String originImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드
        if(!StringUtils.isEmpty(originImgName)) {
            imgName = fileService.uploadFile(itemImgLocation, originImgName, itemImgFile.getBytes());
            imgUrl = "/images/item/" + imgName; // 저장한 상품 이미지를 불러올 경로 설정
        }

        /** 상품 이미지 정보 저장
         * @Param {string} originImgName 업로드했던 상품 이미지 파일의 원래 이름
         * @Param {string} imgName 실제 로컬에 저장된 상품 이미지 파일의 이름
         * @Param {string} imgUrl 업로드 결과 로컬에 저장된 상품 이미지 파일을 불러오는 경로
         */
        itemImg.updateItemImg(originImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
        /** 상품 이미지를 수정한 경우 */
        if(!itemImgFile.isEmpty()) {
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                    .orElseThrow(EntityNotFoundException::new);

            /** 기존 이미지 파일 삭제 */
            if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
            }

            String originImgName = itemImgFile.getOriginalFilename();
            // 업데이트한 상품 이미지 파일 업로드
            String imgName = fileService.uploadFile(itemImgLocation, originImgName, itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;
            savedItemImg.updateItemImg(originImgName, imgName, imgUrl);
        }


    }
}
