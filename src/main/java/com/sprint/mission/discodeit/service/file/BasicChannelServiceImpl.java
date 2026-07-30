package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.file.Channel.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;


    @Override
    public Channel makeChannel(String channelName) {
        Channel channel = channelRepository.saveEntity(Channel.makeChannel(channelName));
        if(channel != null) return channel;

        throw new RuntimeException("채널 생성 실패");
    }

    @Override
    public Channel findChannelById(UUID channelId) {
        Channel channel = channelRepository.findById(channelId);
        if(channel != null) return channel;

        throw new RuntimeException("채널 찾기 실패")  ;
    }

    @Override
    public Channel updateChannelName(UUID channelId, String updateName) {
        Channel channel = channelRepository.updateEntity(channelId, updateName);
        if(channel != null) return channel;

        throw new RuntimeException("채널 업뎃 실패");
    }

    @Override
    public void deleteChannel(UUID channelId) {
        channelRepository.deleteEntity(channelId);
    }

    @Override
    public List<Channel> findAllChannel() {
        return channelRepository.findAllEntity();
    }
}
