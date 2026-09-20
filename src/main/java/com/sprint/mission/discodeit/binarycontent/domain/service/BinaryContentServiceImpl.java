package com.sprint.mission.discodeit.binarycontent.domain.service;

import com.sprint.mission.discodeit.binarycontent.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.binarycontent.domain.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.binarycontent.domain.repository.MapBinaryContentRepository;
import com.sprint.mission.discodeit.global.exception.CustomErrorCode;
import com.sprint.mission.discodeit.global.exception.CustomException;
import com.sprint.mission.discodeit.global.util.file.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class BinaryContentServiceImpl implements BinaryContentService {
    private final BinaryContentRepository binaryContentRepository;
    private final FileUtils fileUtils;

    @Override
    public List<BinaryContent> storeFiles(List<MultipartFile> multipartFiles) {

        List<BinaryContent> storeFileResult = fileUtils.uploadFiles(multipartFiles);

        if (!storeFileResult.isEmpty()) {
            binaryContentRepository.saveAll(storeFileResult);
        }

        return storeFileResult;
    }

    @Override
    public BinaryContent storeFile(MultipartFile multipartFile) {
        BinaryContent uploadedFile = fileUtils.uploadFile(multipartFile);

        return binaryContentRepository.save(uploadedFile);
    }

    @Override
    public BinaryContent findStoreFile(UUID binaryContentUUID) {

        return binaryContentRepository.findById(binaryContentUUID)
            .orElseThrow(() -> new CustomException(CustomErrorCode.FILE_NOT_FOUND));
    }

    @Override
    public List<BinaryContent> findAllStoreFileByIdIn(List<UUID> fileIdList) {

        List<BinaryContent> binaryContents =
            binaryContentRepository.findAllById(fileIdList);

        if (binaryContents.size() != fileIdList.size()) {
            throw new CustomException(CustomErrorCode.FILE_NOT_FOUND);
        }

        return binaryContents;
    }

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

        binaryContentRepository.delete(storeFile);
    }

    @Override
    public List<BinaryContent> findAllStoreFile() {
        return binaryContentRepository.findAll();
    }

}