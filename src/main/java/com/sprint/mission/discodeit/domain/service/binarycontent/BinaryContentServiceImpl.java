package com.sprint.mission.discodeit.domain.service.binarycontent;

import com.sprint.mission.discodeit.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.domain.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.global.exception.CustomErrorCode;
import com.sprint.mission.discodeit.global.exception.CustomException;
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

//    @Value("${file.dir}")
//    private String fileDir;

    @Value("${file.upload-dir}")
    private String fileDir;


    private String getFullPath(String filename) {
        return fileDir + filename;
    }

    //다중 파일 저장 - 채팅방에서 첨부파일 올릴 때
    @Override
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
    @Override
    public BinaryContent storeFile(MultipartFile multipartFile) {
        //어떤 api에서도 호출될 수 있으므로 비어있는지 검증을 실시함
        if(multipartFile.isEmpty()){
            throw new CustomException(CustomErrorCode.FILE_EMPTY);
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
            BinaryContent binaryContent = BinaryContent.init(filePathUrl, storeFileName, fileType);
            binaryContentRepository.save(binaryContent);

            return binaryContent;
        }catch (IOException e){
            log.error("파일 저장 실패", e);
            throw new CustomException(CustomErrorCode.FILE_STORE_FAILED);
        }
    }

    @Override
    public BinaryContent findStoreFile(UUID binaryContentUUID) {

        //안에 실제 저장소 주소 들어있음
        return binaryContentRepository.findById(binaryContentUUID)
            .orElseThrow(() -> new CustomException(CustomErrorCode.FILE_NOT_FOUND));
    }

    @Override
    public List<BinaryContent> findAllStoreFileByIdIn(List<UUID> fileIdList) {

        List<BinaryContent> binaryContentList = new ArrayList<>();
        fileIdList.stream()
            .forEach(id -> binaryContentList.add(this.findStoreFile(id)));

        return binaryContentList;
    }

    /*
        todo : 멤버 / 메시지 해당 url 필드 값 변경하기
     */
    @Override
    public void deleteStoreFileById(UUID binaryContentUUID) {

        BinaryContent storeFile = this.findStoreFile(binaryContentUUID);
        String filePath = storeFile.getPathUrl();

        File file = new File(filePath);
        boolean isDeleted = file.delete();

        log.info("파일 삭제 시작 - 경로 : {}", filePath);
        if(!isDeleted){
            throw new CustomException(CustomErrorCode.FILE_DELETE_FAILED);
        }

        binaryContentRepository.delete(binaryContentUUID);
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