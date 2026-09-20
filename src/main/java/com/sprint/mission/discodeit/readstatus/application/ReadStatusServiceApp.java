package com.sprint.mission.discodeit.readstatus.application;

import com.sprint.mission.discodeit.readstatus.domain.entity.ReadStatus;
import com.sprint.mission.discodeit.channel.domain.service.ChannelService;
import com.sprint.mission.discodeit.readstatus.domain.service.ReadStatusService;
import com.sprint.mission.discodeit.user.domain.service.UserService;
import com.sprint.mission.discodeit.readstatus.web.dto.ReadStatusCreateRequestDTO;
import com.sprint.mission.discodeit.readstatus.web.dto.ReadStatusResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReadStatusServiceApp {
    private final ReadStatusService readStatusService;
    private final UserService userService;
    private final ChannelService channelService;

    public ReadStatusResponseDTO createReadStatus(ReadStatusCreateRequestDTO readStatusCreateRequestDTO){
        ReadStatus readStatus = ReadStatus.init(readStatusCreateRequestDTO.userId(),
            readStatusCreateRequestDTO.channelId(), readStatusCreateRequestDTO.lastReadAt());

        userService.findUserById(readStatus.getUserId());
        channelService.findChannelById(readStatus.getChannelId());
        ReadStatus createdReadStatus = readStatusService.createReadStatus(readStatus);

        return ReadStatusResponseDTO.from(createdReadStatus);
    }
}
