package com.sprint.mission.discodeit.service.application;

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
import global.exception.CustomErrorCode;
import global.exception.CustomException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/*
    todo: 복습용
    그럼 내가 했던 사고 > 계층을 쪼갤 경우 이전 계층에서 무조건 검증을 하고 들어온다가 아닌
    이 서비스의 메서드는 어떻게 호출될지 흐름을 보고(앱서비스의 호출? 다른 서비스의 호출? 관리자 컨트롤러의 호출? 등)
    이건 앱서비스 계층을 통해 들어온다가 확실시 됐다면
    검증을 앞에서 수행하도록 설계를 하는거도 내 선택이고
    아냐 이건 어디서든 검증을 수행하게 할거야 해서 내부에 검증을 해놓는다면 이거도 맞는 관점이다

    "도메인 서비스의 ID 기반 단건 연산은 해당 도메인 객체의 존재 여부를 서비스 내부에서 보장한다. 존재하지 않으면 예외를 발생시킨다."


    요구사항
 ↓
이 메서드의 책임은?
 ↓
누가 호출할 수 있지?
 ↓
이 메서드가 반드시 보장해야 하는 조건은?
 ↓
팀에서 정한 규칙은?
 ↓
가장 단순하고 일관적인 쪽 선택

내가 정한규칙

1. 예외는 요구사항을 만족할 수 없는 상태가 발생했을 때 발생시킨다.

2. 일반 인자의 null 검증은 호출 계층에서 보장한다는 전제로 도메인 서비스에서는 중복 검증하지 않는다.

3. 도메인 객체의 ID(PK) 존재 여부는 해당 도메인 서비스가 보장한다. 따라서 ID 기반 연산에서는 서비스 내부에서 존재 여부를 검증한다.


도메인 서비스는 자신이 담당하는 도메인의 일반적인 규칙을 검증한다.

특정 유스케이스에 종속된 전제조건과 그 실패 정책은 AppService가 결정한다.
 */
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
        Channel madeChannel = channelService.makeChannel(channel);


        List<UUID> userIdList = privateChannelCreateRequestDTO.getUserList();
        /*
            이 검증을 유저 도메인 서비스 안에 두면 도메인 서비스가 요구사항에 종속되는 것이라 느껴져 앱서비스에서 예외처리를 하도록함
         */
        if(userService.existAllByIdList(userIdList)){
            throw new CustomException(CustomErrorCode.USER_NOT_FOUND);
        }
        List<ReadStatus> readStatuses = userIdList.stream()
            .map(userId -> ReadStatus.init(userId, madeChannel.getId())
            )
            .toList();

        for (ReadStatus readStatus : readStatuses) {
            readStatusService.createReadStatus(readStatus);
        }

        return madeChannel;
    }

    //특정 채널을 조회하고 싶을 때
    public ChannelFindResponseDTO findChannel(UUID channelId) {
        /*
            1. 채널 찾기
            2. 프라이빗이라면 그 채널에 참여한 유저들 찾기
            3. 메시지 서비스에서 해당 채널의 마지막 메시지 찾기
         */
        Channel channel = channelService.findChannelById(channelId);

        List<UUID> userIdList = null;
        if(channel.isPrivate()){
            List<ReadStatus> readStatuses = readStatusService.findAllReadStatusByChannelId(channelId);
            userIdList = readStatuses.stream()
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
            .userIdList(userIdList).build();
    }

    //dto 없이 단일 인자만 받음
    public void deleteChannel(UUID channelId){
        //삭제가 되지않는건
        messageService.deleteMessageByChannelId(channelId);
        readStatusService.deleteReadStatusByChannelId(channelId);
        channelService.deleteChannel(channelId);
    }
}
