package com.sprint.mission.discodeit.service.binarycontent;

import com.sprint.mission.discodeit.entity.BinaryContent;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface BinaryContentService {
    List<BinaryContent> storeFiles(List<MultipartFile> multipartFiles);
    BinaryContent storeFile(MultipartFile multipartFile);
}
