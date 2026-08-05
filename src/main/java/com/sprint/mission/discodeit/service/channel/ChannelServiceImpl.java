package com.sprint.mission.discodeit.service.channel;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/*
    todo : 복습용 -----------------
    클래스를 자꾸 쪼개고 계층을 분리하는 이유

    기존 C - S - R(D) 의 계층 > 서비스가 너무 과중한 업무를 지게됨. 중간에서 검증처리를 해주며 도메인 조립 및 다른 서비스를
    호출해야하기도 하고

    C - S1 - S2 - R(D) 의 계층 > 기존 서비스 리포지토리 계층은 자신의 할일만 하면됨. 컨트롤러는 디티오를 받아 서비스에 던지기만하면되고
    그리고 중간에 잡다한 업무는 S1 에서 담당하게됨. 다른 원자성 서비스들을 호출해서 연결되는 흐름을 완성시키고 데이터를 완성해 리턴해줌
    따라서 S2- R(D) 쪽은 자신의 비즈니스 로직만을 처리하는데 집중할 수 있게되고 멀티모듈 프로그램에서 컨트롤러와 S1 부분만 갈아끼기만
    하면됨.
 */


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

        /*
            todo 질문리스트 --------
            근데 이런 비즈니스적 요구사항 처리는 s2에서 하는게 맞나요?
            초기 요구사항이 퍼블릭 채널 프라이빗 채널 메서드를 구분하는거였는데
            프론트와 컨트롤러에서 이를 처리할수도 있다 생각해서요
         */
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
