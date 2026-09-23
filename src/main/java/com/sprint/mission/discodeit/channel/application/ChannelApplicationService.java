package com.sprint.mission.discodeit.channel.application;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.channel.domain.entity.ChannelType;
import com.sprint.mission.discodeit.channel.domain.repository.ChannelRepository;
import com.sprint.mission.discodeit.channel.web.dto.req.ChannelUpdateRequestDTO;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.message.domain.repository.MessageRepository;
import com.sprint.mission.discodeit.readstatus.domain.entity.ReadStatus;

import com.sprint.mission.discodeit.readstatus.domain.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.user.domain.entity.User;
import com.sprint.mission.discodeit.user.domain.repository.UserRepository;
import com.sprint.mission.discodeit.channel.web.dto.req.ChannelPublicCreateRequestDTO;
import com.sprint.mission.discodeit.channel.web.dto.req.ChannelPrivateCreateRequestDTO;
import com.sprint.mission.discodeit.global.exception.CustomErrorCode;
import com.sprint.mission.discodeit.global.exception.CustomException;
import com.sprint.mission.discodeit.channel.web.dto.res.ChannelResponseDTO;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*

 */

@Slf4j
@RequiredArgsConstructor
@Service
public class ChannelApplicationService {
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;
    private final ChannelMapper channelMapper;

    public ChannelResponseDTO makePublicChannel(ChannelPublicCreateRequestDTO request) {
        Channel channel = Channel.init(
            request.name(),
            ChannelType.PUBLIC,
            request.description()
        );

        Channel saved = channelRepository.save(channel);

        return channelMapper.toResponse(saved, List.of(), null);
    }

    @Transactional
    public ChannelResponseDTO makePrivateChannel(
        ChannelPrivateCreateRequestDTO request
    ) {
        Channel channel = Channel.init(null, ChannelType.PRIVATE, null);

        /*
            1. 채널저장
            2. 유저아이디 리스트로 들어온 것 + 채널 아이디 정보로 리드스테이터스 개체 일일이 생성
            3. 생성된 모든 개체 리드스테이터스 저장
         */

        List<UUID> userIdList = request.participantIds();

        List<User> userList = userRepository.findAllById(userIdList);
        if(userIdList.size() != userList.size()) throw new CustomException(CustomErrorCode.USER_NOT_FOUND);

        Channel saved = channelRepository.save(channel);

        Instant lastReadAt = Instant.now();     // 채널 생성 시점 - 낫널
        List<ReadStatus> readStatuses = userList.stream()
            .map(user -> ReadStatus.init(user, saved, lastReadAt)
            )
            .toList();

        readStatusRepository.saveAll(readStatuses);


        return channelMapper.toResponse(saved, userList,null);
    }

    public void deleteChannel(UUID channelId){
        Channel channel = channelRepository.getByIdOrThrow(channelId);
        channelRepository.delete(channel);
        //messageService.deleteMessageByChannelId(channelId);
        //readStatusService.deleteReadStatusByChannelId(channelId);
        //channelService.deleteChannel(channelId);
    }

    /*
        입장 가능한 채널 목록
     */
    @Transactional
    public List<ChannelResponseDTO> findAllChannelByUserId(UUID userId){
        userRepository.validateExistsById(userId);

        List<Channel> accessibleChannels = channelRepository.
            findAccessibleChannelsByUserId(userId, ChannelType.PUBLIC);

        List<UUID> privateChannelIds = accessibleChannels.stream()
            .filter(Channel::isPrivate)
            .map(Channel::getId)
            .toList();

        /*
            1. 모든 퍼블릭 채널과 마지막 메시지를 합쳐서 가져오는 쿼리문을 실행한다
            2. 유저아이디로 해당 유저가 입장한 프라이빗 채널의 모든 입장인원의 상태와 마지막 메세지를 하나의 쿼리문으로 가져온다. 근데 이 때 리드스테이터스와 유저테이블의 연결을 어떻게해야할지 아직 모르겠음
            그리고 위의 쿼리들은 페치조인으로 가져온다, 채널 별로 리스폰스 dto 를 생성해야하는데 한번에 가져오지 않으면 n+1 문제가 발생한다.
            현재 유저와 유저스테이터스는 양방향 관계이다
         */

        return null;
    }

    @Transactional
    public ChannelResponseDTO updateChannel(UUID channelId, ChannelUpdateRequestDTO request) {
        Channel channel = channelRepository.getByIdOrThrow(channelId);
        channel.updateChannelNameAndDescription(request.newName(), request.newDescription());

        return channelMapper.toResponse(channel, List.of(), null);
    }
}
