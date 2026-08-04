package com.sprint.mission.discodeit.service.binarycontent;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import global.exception.CustomErrorCode;
import global.exception.CustomException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class BinaryContentServiceImpl implements BinaryContentService {
    private final BinaryContentRepository binaryContentRepository;

    @Value("${file.dir}")
    private String fileDir;

    private String getFullPath(String filename) {
        return fileDir + filename;
    }

    //다중 파일 저장 - 채팅방에서 첨부파일 올릴 때
    public List<BinaryContent> storeFiles(List<MultipartFile> multipartFiles) {

        List<BinaryContent> storeFileResult = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }

        return storeFileResult;
    }

    /*
        멀티파트 타입으로 들어온 걸 내 바이너리 컨텐트 필드에 맞게 전부 추출하고
        저장소에 그대로 저장한 뒤 필드를 구성하여 바이너리 컨텐트 객체를 생성 후 이걸 DB 에 저장하면 됨.
     */
    public BinaryContent storeFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new CustomException(CustomErrorCode.INVALID_USER_DUPLICATE_EMAIL);    //todo 나중에 예외 생성
        }

        try{
            //필요 필드 추출
            String originalFilename = multipartFile.getOriginalFilename();  //사용자 원본 파일 명 - 스프링 제공
            String fileType = multipartFile.getContentType();


            //저장소 로직 - 따로 빼야할 듯 (서비스로 또 만들어야하나?)
            String storeFileName = createStoreFileName(originalFilename);   //실제 저장소에 저장될 파일 명
            String filePathUrl = getFullPath(storeFileName);
            multipartFile.transferTo(new File(filePathUrl)); //메모리에 올라와있는걸 파일로 저장

            //저장 완료 후 개체 빌드 및 리포지토리 호출
            BinaryContent binaryContent = BinaryContent.builder()
                .fileName(originalFilename)
                .fileType(fileType)
                .pathUrl(filePathUrl).build();
            binaryContentRepository.save(binaryContent);

            return binaryContent;
        }catch (IOException e){
            log.error("파일 저장 실패", e);
            throw new RuntimeException("파일 저장 실패");         //todo : 나중에 예외 생성
        }
    }

    //여기서만 사용하는 메서드 - 저장소에 저장될 서버 파일명
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}