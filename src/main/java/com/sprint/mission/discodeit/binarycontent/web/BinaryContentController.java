package com.sprint.mission.discodeit.binarycontent.web;

import com.sprint.mission.discodeit.binarycontent.application.BinaryApplicationService;
import com.sprint.mission.discodeit.binarycontent.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.binarycontent.web.dto.res.BinaryContentResponseDTO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/binaryContents")
public class BinaryContentController {
    private final BinaryApplicationService binaryApplicationService;

    //todo
    @GetMapping("/{binaryContentId}")
    public ResponseEntity<BinaryContentResponseDTO> findBinaryContent(@PathVariable UUID binaryContentId) {

        BinaryContent storeFile = binaryApplicationService.findStoreFile(binaryContentId);

        // 응답규격에 맞게 일단 수정
        byte bytes[] = convertBinaryFile(storeFile);
        BinaryContentResponseDTO response = BinaryContentResponseDTO.of(storeFile, bytes.length, bytes);

        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<BinaryContentResponseDTO>> findBinaryContents(@RequestParam List<UUID> binaryContentIds){
        List<BinaryContent> binaryContentList = binaryApplicationService.findAllStoreFileByIdIn(
            binaryContentIds);

        List<BinaryContentResponseDTO> response = binaryContentList.stream()
            .map(binaryContent -> {
                byte bytes[] = convertBinaryFile(binaryContent);
                return BinaryContentResponseDTO.of(binaryContent, bytes.length, bytes);
            })
            .toList();

        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }

    private byte[] convertBinaryFile(BinaryContent binaryContent){
        try{
            return Files.readAllBytes(Paths.get(binaryContent.getPathUrl()));
        }catch (IOException e){
            log.error("convert error",e);
            throw new RuntimeException("파일 변환 실패");
        }
    }
}
