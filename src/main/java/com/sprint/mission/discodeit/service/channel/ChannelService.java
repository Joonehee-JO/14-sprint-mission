package com.sprint.mission.discodeit.service.channel;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.web.controller.channel.dto.PrivateChannelCreateRequestDTO;
import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel makeChannel(String channelName);
    Channel makePrivateChannel(PrivateChannelCreateRequestDTO privateChannelCreateRequestDTO);
    Channel findChannelById(UUID channelId);
    Channel updateChannelName(UUID channelId, String updateName);
    void deleteChannel(UUID channelId);
    List<Channel> findAllChannel();
}
