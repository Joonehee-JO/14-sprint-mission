package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.ReadStatus.ReadStatusBuilder;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.channel.ChannelService;
import com.sprint.mission.discodeit.service.message.MessageService;
import com.sprint.mission.discodeit.service.readstatus.ReadStatusService;
import com.sprint.mission.discodeit.service.user.UserService;
import com.sprint.mission.discodeit.web.controller.channel.dto.ChannelCreateRequestDTO;
import com.sprint.mission.discodeit.web.controller.channel.dto.PrivateChannelCreateRequestDTO;
import com.sprint.mission.discodeit.web.controller.channel.dto.res.ChannelFindResponseDTO;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChannelAppServiceImpl implements ChannelAppService{
    private final ChannelService channelService;
    private final ReadStatusService readStatusService;
    private final MessageService messageService;
    private final UserService userService;

    //퍼블릭 채널 저장
    @Override
    public Channel makePublicChannel(ChannelCreateRequestDTO channelCreateRequestDTO) {
        Channel channel = channelCreateRequestDTO.toEntity();

        return channelService.makeChannel(channel);
    }

    //프라이빗 채널 저장
    @Override
    public Channel makePrivateChannel(
        PrivateChannelCreateRequestDTO privateChannelCreateRequestDTO) {
        Channel channel = privateChannelCreateRequestDTO.toEntity();

        /*
            1. 채널저장
            2. 유저아이디 리스트로 들어온 것 + 채널 아이디 정보로 리드스테이터스 개체 일일이 생성
            3. 생성된 모든 개체 리드스테이터스 저장
         */
        channelService.makeChannel(channel);

        List<UUID> userIdList = privateChannelCreateRequestDTO.getUserList();
        List<ReadStatus> readStatuses = userIdList.stream()
            .map(userId -> ReadStatus.builder()
                .userId(userId)
                .channelId(channel.getId())
                .build()
            )
            .toList();

        for (ReadStatus readStatus : readStatuses) {
            readStatusService.createReadStatus(readStatus);
        }

        return channel;
    }

    @Override
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
                .map(readStatus -> readStatus.getUserId())
                .toList();
        }

        Message message = messageService.findLastMessageByChannelId(channel.getId());
        Instant createdAt = null;
        if(Objects.nonNull(message)) {
            createdAt = message.getCreatedAt();
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
    @Override
    public List<Channel> findAllByUserId(UUID userId) {
        //검증
        User user = userService.findById(userId);

        /*
            1. 채널서비스로 모든 채널을 가져온다.              //디비 한번 접근
            2. 리드스테이터스에서 유저가 속한 모든 채널아이디를 가져온다. (이건 프라이빗임)
            3. 해당 채널 아이디를 채널 서비스에서 가져온다.      //가져온 모든 채널에 대해 퍼블릿 + 자기가 속한 걸 필터링한다.
            3. 해당 채널들을 묶어 반환한다.
         */

        List<Channel> channelList = channelService.findAllChannel();
        List<ReadStatus> readStatusList = readStatusService.findReadStatusByUserId(userId);
        Set<UUID> set = readStatusList.stream()
            .map(readStatus -> readStatus.getChannelId())
            .collect(Collectors.toSet());

        List<Channel> channels = channelList.stream()
            .filter(channel -> channel.getChannelType().equals(ChannelType.PUBLIC_CHANNEL)
            || set.contains(channel.getId()))
            .toList();

        return channels;
    }
}
