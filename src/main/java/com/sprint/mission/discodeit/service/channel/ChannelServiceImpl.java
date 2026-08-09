package com.sprint.mission.discodeit.service.channel;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;

    @Override
    public Channel makeChannel(Channel channel) {
        if(Objects.isNull(channel)){
            throw new IllegalArgumentException("채널이 널입니다"); //todo
        }
        return channelRepository.saveEntity(channel);
    }

    @Override
    public Channel findChannelById(UUID channelId) {
        if(Objects.isNull(channelId)){
            throw new IllegalArgumentException("채널이 널입니다");
        }

        return channelRepository.findById(channelId)
            .orElseThrow(() -> new IllegalArgumentException("해당 ID의 채널이 존재하지 않습니다"));
    }

    @Override
    public Channel updateChannelName(UUID channelId, String updateName) {
        if(Objects.isNull(channelId) || Objects.isNull(updateName)){
            throw new IllegalArgumentException("인자는 널일 수 없음");
        }

        //채널 조회 검증
        Channel channel = this.findChannelById(channelId);

        if(channel.getChannelType().equals(ChannelType.PRIVATE_CHANNEL)){
            throw new IllegalArgumentException("프라이빗 채널은 수정이 불가");
        }
        channel.updateChannelName(updateName);

        return channelRepository.saveEntity(channel);
    }

    @Override
    public void deleteChannel(UUID channelId) {
        if(Objects.isNull(channelId)){
            throw new IllegalArgumentException("인자는 널일 수 없음");
        }

        Channel channel = this.findChannelById(channelId);
        channelRepository.deleteEntity(channel.getId());
    }

    @Override
    public List<Channel> findAllChannel() {
        return channelRepository.findAllEntity();
    }
}
