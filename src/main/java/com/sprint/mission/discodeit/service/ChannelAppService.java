package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.web.controller.channel.dto.ChannelCreateRequestDTO;
import com.sprint.mission.discodeit.web.controller.channel.dto.PrivateChannelCreateRequestDTO;
import com.sprint.mission.discodeit.web.controller.channel.dto.res.ChannelFindResponseDTO;
import java.util.List;
import java.util.UUID;

public interface ChannelAppService {
    Channel makePublicChannel(ChannelCreateRequestDTO channelCreateRequestDTO);
    Channel makePrivateChannel(PrivateChannelCreateRequestDTO privateChannelCreateRequestDTO);
    ChannelFindResponseDTO findChannel(UUID channelId);
    List<Channel> findAllByUserId(UUID userId);
}
