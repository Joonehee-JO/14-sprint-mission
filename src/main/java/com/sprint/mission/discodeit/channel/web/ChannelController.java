package com.sprint.mission.discodeit.channel.web;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.channel.application.ChannelApplicationService;
import com.sprint.mission.discodeit.channel.web.dto.ChannelPublicCreateRequestDTO;
import com.sprint.mission.discodeit.channel.web.dto.ChannelUpdateRequestDTO;
import com.sprint.mission.discodeit.channel.web.dto.ChannelPrivateCreateRequestDTO;
import com.sprint.mission.discodeit.channel.web.dto.ChannelFindResponseDTO;
import com.sprint.mission.discodeit.channel.web.dto.ChannelResponseDTO;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/channels")
public class ChannelController {
    private final ChannelApplicationService channelApplicationService;

    @PostMapping("/public")
    public ResponseEntity<ChannelResponseDTO> makePublicChannel(@RequestBody ChannelPublicCreateRequestDTO channelPublicCreateRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(channelApplicationService.makePublicChannel(channelPublicCreateRequestDTO));
    }

    @PostMapping("/private")
    public ResponseEntity<ChannelResponseDTO> makePrivateChannel(@RequestBody ChannelPrivateCreateRequestDTO channelPrivateCreateRequestDTO){
        ChannelResponseDTO response = channelApplicationService.makePrivateChannel(channelPrivateCreateRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable UUID id){
        channelApplicationService.deleteChannel(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ChannelResponseDTO> updateChannel(@PathVariable UUID id, @RequestBody ChannelUpdateRequestDTO channelUpdateRequestDTO){
        Channel updatedChannel = channelApplicationService.updateChannel(id, channelUpdateRequestDTO.newName(),
            channelUpdateRequestDTO.newDescription());
        ChannelResponseDTO response = ChannelResponseDTO.from(updatedChannel);

        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ChannelResponseDTO>> findAllChannelByUserId(@RequestParam UUID userId){
        List<ChannelResponseDTO> response = channelApplicationService.findAllChannelByUserId(userId);

        return ResponseEntity.status(HttpStatus.OK)
            .body(response);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<ChannelFindResponseDTO> findPublicChannelInfo(@PathVariable UUID id){
        return ResponseEntity.ok(channelApplicationService.findChannel(id));
    }
}
