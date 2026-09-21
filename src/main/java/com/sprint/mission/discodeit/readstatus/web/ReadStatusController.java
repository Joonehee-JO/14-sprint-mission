package com.sprint.mission.discodeit.readstatus.web;

import com.sprint.mission.discodeit.readstatus.domain.entity.ReadStatus;
import com.sprint.mission.discodeit.readstatus.application.ReadStatusApplicationService;
import com.sprint.mission.discodeit.readstatus.domain.service.ReadStatusService;
import com.sprint.mission.discodeit.readstatus.web.dto.ReadStatusCreateRequestDTO;
import com.sprint.mission.discodeit.readstatus.web.dto.ReadStatusUpdateRequestDTO;
import com.sprint.mission.discodeit.readstatus.web.dto.ReadStatusResponseDTO;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/readStatuses")
public class ReadStatusController {
    private final ReadStatusApplicationService readStatusApplicationService;
    private final ReadStatusService readStatusService;

    @GetMapping
    public ResponseEntity<List<ReadStatusResponseDTO>> findReadStatusesByUserId(@RequestParam UUID userId){
        List<ReadStatus> userReadStatuses = readStatusApplicationService.findReadStatusByUserId(userId);
        List<ReadStatusResponseDTO> response = ReadStatusResponseDTO.fromList(
            userReadStatuses);

        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }

    @PostMapping
    public ResponseEntity<ReadStatusResponseDTO> createReadStatus(@RequestBody ReadStatusCreateRequestDTO readStatusCreateRequestDTO){
        ReadStatusResponseDTO response = readStatusApplicationService.createReadStatus(
            readStatusCreateRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
    }

    @PatchMapping("/{readStatusId}")
    public ResponseEntity<ReadStatusResponseDTO> updateReadStatus(@PathVariable UUID readStatusId, @RequestBody ReadStatusUpdateRequestDTO readStatusUpdateRequestDTO){
        ReadStatus updatedReadStatus = readStatusApplicationService.updateReadStatusReadTime(readStatusId,
            readStatusUpdateRequestDTO.newLastReadAt());
        ReadStatusResponseDTO response = ReadStatusResponseDTO.from(updatedReadStatus);

        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }
}
