package com.sprint.mission.discodeit.channel.domain.service;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel makeChannel(Channel channel);
    Channel findChannelById(UUID channelId);
}
