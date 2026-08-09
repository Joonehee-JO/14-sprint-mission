package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;

import com.sprint.mission.discodeit.service.channel.ChannelService;
import com.sprint.mission.discodeit.service.message.MessageService;
import com.sprint.mission.discodeit.service.readstatus.ReadStatusService;
import com.sprint.mission.discodeit.service.user.UserService;
import com.sprint.mission.discodeit.web.controller.dto.req.ChannelCreateRequestDTO;
import com.sprint.mission.discodeit.web.controller.dto.req.ChannelUpdateRequestDTO;
import com.sprint.mission.discodeit.web.controller.dto.req.PrivateChannelCreateRequestDTO;
import com.sprint.mission.discodeit.web.controller.dto.res.ChannelFindResponseDTO;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChannelServiceApp {
    private final ChannelService channelService;
    private final ReadStatusService readStatusService;
    private final MessageService messageService;
    private final UserService userService;

    //퍼블릭 채널 저장
    public Channel makePublicChannel(ChannelCreateRequestDTO channelCreateRequestDTO) {
        Channel channel = Channel.init(channelCreateRequestDTO.getChannelName(), channelCreateRequestDTO.getChannelType());

        return channelService.makeChannel(channel);
    }

    //프라이빗 채널 저장
    public Channel makePrivateChannel(
        PrivateChannelCreateRequestDTO privateChannelCreateRequestDTO) {
        Channel channel = Channel.init(privateChannelCreateRequestDTO.getChannelName(), privateChannelCreateRequestDTO.getChannelType());

        /*
            1. 채널저장
            2. 유저아이디 리스트로 들어온 것 + 채널 아이디 정보로 리드스테이터스 개체 일일이 생성
            3. 생성된 모든 개체 리드스테이터스 저장
         */
        Channel makedChannel = channelService.makeChannel(channel);

        List<UUID> userIdList = privateChannelCreateRequestDTO.getUserList();
        List<ReadStatus> readStatuses = userIdList.stream()
            .map(userId -> ReadStatus.init(userId, makedChannel.getId())
            )
            .toList();

        for (ReadStatus readStatus : readStatuses) {
            readStatusService.createReadStatus(readStatus);
        }

        return makedChannel;
    }

    //특정 채널을 조회하고 싶을 때
    public ChannelFindResponseDTO findChannel(UUID channelId) {
        /*
            1. 채널 찾기
            2. 프라이빗이라면 그 채널에 참여한 유저들 찾기
            3. 메시지 서비스에서 해당 채널의 마지막 메시지 찾기
         */
        Channel channel = channelService.findChannelById(channelId);

        List<UUID> userIdlist = null;
        if(channel.getChannelType().equals(ChannelType.PRIVATE_CHANNEL)){
            List<ReadStatus> readStatuses = readStatusService.findAllReadStatusByChannelId(channelId);
            userIdlist = readStatuses.stream()
                .map(ReadStatus::getUserId)
                .toList();
        }


        Instant createdAt = null;
        Optional<Message> message = messageService.findLastMessageByChannelId(channel.getId());
        if(message.isPresent()){
            createdAt = message.get().getCreatedAt();
        }

        return ChannelFindResponseDTO.builder()
            .channelId(channel.getId()).channelName(channel.getChannelName())
            .channelType(channel.getChannelType()).latestMessageAt(createdAt)
            .userIdList(userIdlist).build();
    }


    /*
        DTO를 활용하여:
        [ ] 해당 채널의 가장 최근 메시지의 시간 정보를 포함합니다.
        [ ] PRIVATE 채널인 경우 참여한 User의 id 정보를 포함합니다.
        [ ] 특정 User가 볼 수 있는 Channel 목록을 조회하도록 조회 조건을 추가하고, 메소드 명을 변경합니다. findAllByUserId
        [ ] PUBLIC 채널 목록은 전체 조회합니다.
        [ ] PRIVATE 채널은 조회한 User가 참여한 채널만 조회합니다.
     */



    /*
        update
        [ ] DTO를 활용해 파라미터를 그룹화합니다.
        수정 대상 객체의 id 파라미터, 수정할 값 파라미터
        [ ] PRIVATE 채널은 수정할 수 없습니다.
        delete
        [ ] 관련된 도메인도 같이 삭제합니다.
        Message, ReadStatus
     */
    public Channel updateChannelName(ChannelUpdateRequestDTO channelUpdateRequestDTO){
        return channelService.updateChannelName(channelUpdateRequestDTO.getChannelId(),
            channelUpdateRequestDTO.getChannelName());
    }

    //dto 없이 단일 인자만 받음
    public void deleteChannel(UUID channelId){
        channelService.findChannelById(channelId);

        messageService.deleteMessageByChannelId(channelId);
        readStatusService.deleteReadStatusByChannelId(channelId);
        channelService.deleteChannel(channelId);
    }
}
