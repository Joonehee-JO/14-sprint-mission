package com.sprint.mission.discodeit.global.util.file;

import com.sprint.mission.discodeit.binarycontent.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.global.exception.CustomErrorCode;
import com.sprint.mission.discodeit.global.exception.CustomException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public abstract class AbstractFileUtils implements FileUtils {

    @Override
    public BinaryContent uploadFile(MultipartFile file) {
        if(Objects.isNull(file) || file.isEmpty()){
            throw new CustomException(CustomErrorCode.FILE_EMPTY);
        }

        try{
            String originalFilename = file.getOriginalFilename();
            String contentType = file.getContentType();

            String storeFileName = createStoreFileName(originalFilename);
            String filePathUrl = getFullPath(storeFileName);
            log.info("filePathUrl ----------- {}", filePathUrl);

            String storedFile = storeFile(file, filePathUrl);
            log.info("storedFile ----------- {}", storedFile);

            return BinaryContent.init(storedFile, storeFileName, file.getSize(), contentType);
        }catch (IOException e){
            throw new CustomException(CustomErrorCode.FILE_STORE_FAILED);
        }
    }

    @Override
    public List<BinaryContent> uploadFiles(List<MultipartFile> files) {
        List<BinaryContent> storeFileResult = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                storeFileResult.add(uploadFile(file));
            }
        }

        return storeFileResult;
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    protected abstract String getFullPath(String fileName);
    protected abstract String storeFile(MultipartFile file, String storeFileName) throws IOException;
}
