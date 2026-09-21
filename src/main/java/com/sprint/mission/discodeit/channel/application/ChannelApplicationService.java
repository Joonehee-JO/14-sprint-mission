package com.sprint.mission.discodeit.channel.application;

import com.sprint.mission.discodeit.channel.domain.entity.Channel;
import com.sprint.mission.discodeit.channel.domain.entity.ChannelType;
import com.sprint.mission.discodeit.channel.domain.repository.ChannelRepository;
import com.sprint.mission.discodeit.message.domain.entity.Message;
import com.sprint.mission.discodeit.message.domain.repository.MessageRepository;
import com.sprint.mission.discodeit.readstatus.domain.entity.ReadStatus;

import com.sprint.mission.discodeit.readstatus.domain.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.user.domain.entity.User;
import com.sprint.mission.discodeit.user.domain.repository.UserRepository;
import com.sprint.mission.discodeit.channel.web.dto.ChannelPublicCreateRequestDTO;
import com.sprint.mission.discodeit.channel.web.dto.ChannelPrivateCreateRequestDTO;
import com.sprint.mission.discodeit.channel.web.dto.ChannelFindResponseDTO;
import com.sprint.mission.discodeit.global.exception.CustomErrorCode;
import com.sprint.mission.discodeit.global.exception.CustomException;
import com.sprint.mission.discodeit.channel.web.dto.ChannelResponseDTO;
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

    public ChannelResponseDTO makePublicChannel(ChannelPublicCreateRequestDTO channelPublicCreateRequestDTO) {
        Channel channel = Channel.init(channelPublicCreateRequestDTO.name(), ChannelType.PUBLIC, channelPublicCreateRequestDTO.description());
        Channel saved = channelRepository.save(channel);

        return ChannelResponseDTO.from(saved);
    }

    @Transactional
    public ChannelResponseDTO makePrivateChannel(
        ChannelPrivateCreateRequestDTO channelPrivateCreateRequestDTO
    ) {
        Channel channel = Channel.init(null, ChannelType.PRIVATE, null);

        /*
            1. 채널저장
            2. 유저아이디 리스트로 들어온 것 + 채널 아이디 정보로 리드스테이터스 개체 일일이 생성
            3. 생성된 모든 개체 리드스테이터스 저장
         */
        Channel madeChannel = channelRepository.save(channel);

        List<UUID> userIdList = channelPrivateCreateRequestDTO.participantIds();

        List<User> userList = userRepository.findAllById(userIdList);
        if(userIdList.size() != userList.size()) throw new CustomException(CustomErrorCode.USER_NOT_FOUND);

        Instant lastReadAt = Instant.now();
        List<ReadStatus> readStatuses = userList.stream()
            .map(user -> ReadStatus.init(user, madeChannel, lastReadAt)
            )
            .toList();

        readStatusRepository.saveAll(readStatuses);

        return ChannelResponseDTO.from(madeChannel);
    }

    //특정 채널을 조회하고 싶을 때
    @Transactional
    public ChannelFindResponseDTO findChannel(UUID channelId) {
        /*
            1. 채널 찾기
            2. 프라이빗이라면 그 채널에 참여한 유저들 찾기
            3. 메시지 서비스에서 해당 채널의 마지막 메시지 찾기
         */
        Channel channel = channelRepository.getByIdOrThrow(channelId);

        List<UUID> userIdList = List.of();
        if(channel.isPrivate()){
            userIdList = readStatusRepository.findUserIdsByChannelId(channelId);
        }

        Instant createdAt = messageRepository.findTopByChannelIdOrderByCreatedAtDesc(channelId)
            .map(Message::getCreatedAt)
            .orElse(null);

        return ChannelFindResponseDTO.builder()
            .channelId(channel.getId()).channelName(channel.getChannelName())
            .channelType(channel.getChannelType()).latestMessageAt(createdAt)
            .userIdList(userIdList).build();
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

        return accessibleChannels.stream()
            .map(ChannelResponseDTO::from)
            .toList();
    }

    @Transactional
    public Channel updateChannel(UUID channelId, String newName, String newDescription) {
        Channel channel = channelRepository.getByIdOrThrow(channelId);
        channel.updateChannelNameAndDescription(newName, newDescription);
        return channel;
    }
}
