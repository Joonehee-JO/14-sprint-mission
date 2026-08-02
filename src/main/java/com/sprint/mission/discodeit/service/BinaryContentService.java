package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.BinaryContent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface BinaryContentService {
    List<BinaryContent> storeFiles(List<MultipartFile> multipartFiles) throws IOException;
    BinaryContent storeFile(MultipartFile multipartFile) throws IOException;
}
