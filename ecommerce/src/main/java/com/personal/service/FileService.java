package com.personal.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
        UUID uuid = UUID.randomUUID();  // Universally Unique Identifier
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        /** FileOutputStream는 바이트 단위의 출력을 내보내는 클래스.
         * 생성자로 파일이 저장될 위치와 파일의 이름을 넘긴 후 파일에 쓸 파일 출력 스트림을 만듦 */
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData); // write() : 파일 출력 스트림
        fos.close();

        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath); // 파일이 저장된 경로로 파일 객체 생성

        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하겠습니다");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
