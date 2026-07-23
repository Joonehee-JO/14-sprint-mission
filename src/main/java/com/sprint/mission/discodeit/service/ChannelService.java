package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel makeChannel(String channelName);
    //Channel findChannelByName(String channelName);
    Channel findChannelById(UUID channelId);
    Channel updateChannelName(UUID channelId, String updateName);
    void deleteChannel(UUID channelId);
    List<Channel> findAllChannel();
}
