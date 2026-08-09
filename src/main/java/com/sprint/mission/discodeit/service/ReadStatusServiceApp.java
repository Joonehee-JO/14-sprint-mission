package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.channel.ChannelService;
import com.sprint.mission.discodeit.service.readstatus.ReadStatusService;
import com.sprint.mission.discodeit.service.user.UserService;
import com.sprint.mission.discodeit.web.controller.dto.req.ReadStatusCreateRequestDTO;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ReadStatusServiceApp {
    private final ReadStatusService readStatusService;
    private final UserService userService;
    private final ChannelService channelService;

    public ReadStatus createReadStatus(ReadStatusCreateRequestDTO readStatusCreateRequestDTO){
        //간접 검증부
        userService.findById(readStatusCreateRequestDTO.getUserId());
        channelService.findChannelById(readStatusCreateRequestDTO.getChannelId());
        ReadStatus readStatus = ReadStatus.init(readStatusCreateRequestDTO.getUserId(), readStatusCreateRequestDTO.getChannelId());

        return readStatusService.createReadStatus(readStatus);
    }

    public ReadStatus findReadStatusById(UUID readStatusId){
        return readStatusService.findReadStatusById(readStatusId);
    }

    public List<ReadStatus> findAllReadStatusByUserId(UUID userId){
        userService.findById(userId);

        return readStatusService.findReadStatusByUserId(userId);
    }

    public ReadStatus updateReadStatus(UUID readStatusId){
        return readStatusService.updateReadStatusReadTime(readStatusId);
    }

    public void deleteReadStatus(UUID readStatusId){
        readStatusService.deleteReadStatusById(readStatusId);
    }
}
