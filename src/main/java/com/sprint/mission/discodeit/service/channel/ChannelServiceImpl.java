package com.sprint.mission.discodeit.service.channel;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.readstatus.ReadStatusService;
import com.sprint.mission.discodeit.web.controller.channel.dto.PrivateChannelCreateRequestDTO;
import java.util.List;
import java.util.UUID;
import lombok.Locked.Read;
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

/*
    todo 채널 소유권자를 추가해야할 것 같긴함.
 */
@RequiredArgsConstructor
@Service
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;

    private final ReadStatusService readStatusService;

    //퍼블릭 채널 생성
    @Override
    public Channel makeChannel(String channelName) {
        Channel channel = Channel.builder().channelName(channelName)
            .channelType(ChannelType.PUBLIC_CHANNEL).build();

        return channelRepository.saveEntity(channel);
    }

    @Override
    public Channel makePrivateChannel(PrivateChannelCreateRequestDTO privateChannelCreateRequestDTO) {
        Channel channel = privateChannelCreateRequestDTO.toEntity();


        //일단 DTO 로 받은걸 일일이 전부 빌드해서 리드스테이터스 개체를 만들어 리드스테이터스 리포지토리에 저장
        List<UUID> userIdList = privateChannelCreateRequestDTO.getUserList();
        List<ReadStatus> readStatuses = userIdList.stream()
            .map(userId -> ReadStatus.builder()
                    .userId(userId)
                    .channelId(channel.getId())
                    .build())
            .toList();

        for (ReadStatus readStatus : readStatuses) {
            readStatusService.createReadStatus(readStatus);
        }

        return channelRepository.saveEntity(channel);
    }

    @Override
    public Channel findChannelById(UUID channelId) {
        return channelRepository.findById(channelId)
            .orElseThrow(() -> new RuntimeException("해당 ID의 채널이 존재하지 않습니다"));
    }

    @Override
    public Channel updateChannelName(UUID channelId, String updateName) {
        return null;
    }

    @Override
    public void deleteChannel(UUID channelId) {

    }

    @Override
    public List<Channel> findAllChannel() {
        return List.of();
    }
}
