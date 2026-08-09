package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.binarycontent.BinaryContentService;
import com.sprint.mission.discodeit.service.channel.ChannelService;
import com.sprint.mission.discodeit.service.message.MessageService;
import com.sprint.mission.discodeit.web.controller.dto.req.CreateMessageRequestDTO;
import com.sprint.mission.discodeit.web.controller.dto.req.MessageUpdateRequestDTO;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageServiceApp {
    private final MessageService messageService;
    private final ChannelService channelService;
    private final BinaryContentService binaryContentService;

/*
    MessageService 고도화

    create
    [ ] 선택적으로 여러 개의 첨부파일을 같이 등록할 수 있습니다.
    [ ] DTO를 활용해 파라미터를 그룹화합니다.

    findAll
    [ ] 특정 Channel의 Message 목록을 조회하도록 조회 조건을 추가하고, 메소드 명을 변경합니다. findallByChannelId

    update
    [ ] DTO를 활용해 파라미터를 그룹화합니다.
    수정 대상 객체의 id 파라미터, 수정할 값 파라미터

    delete
    [ ] 관련된 도메인도 같이 삭제합니다.
    첨부파일(BinaryContent)
 */

    public Message createMessage(CreateMessageRequestDTO createMessageRequestDTO){
        Message message = Message.init(createMessageRequestDTO.getUserId(),createMessageRequestDTO.getChannelId(), createMessageRequestDTO.getContent());

        /*
            1. dto 에 파일이 들어있는지 확인
            2. 있으면 바이너리컨텐트서비스 호출
            3. 메시지 서비스 호출
         */

        List<BinaryContent> storedBinaryContents;
        List<UUID> filteredBinaryContents = null;
        if(Objects.nonNull(createMessageRequestDTO.getImageList())){
            storedBinaryContents = binaryContentService.storeFiles(createMessageRequestDTO.getImageList());
            //서비스 필요형태로 변환
            filteredBinaryContents = storedBinaryContents.stream()
                .map(BinaryContent::getId)
                .toList();
        }

        return messageService.createMessage(message, filteredBinaryContents);
    }


    public List<Message> findAllMessageByChannelId(UUID channelId){
        return messageService.findAllMessageByChannelId(channelId);
    }

//    update
//    [ ] DTO를 활용해 파라미터를 그룹화합니다.
//    수정 대상 객체의 id 파라미터, 수정할 값 파라미터
//
//        delete
//    [ ] 관련된 도메인도 같이 삭제합니다.
//    첨부파일(BinaryContent)

    public Message updateMessage(MessageUpdateRequestDTO messageUpdateRequestDTO){
        return messageService.updateMessageContent(messageUpdateRequestDTO.getMessageId(), messageUpdateRequestDTO.getContent());
    }

    public void deleteMessage(UUID messageId){
        Message message = messageService.findMessageById(messageId);
        messageService.deleteMessage(messageId);
        message.getImageList().stream()
                .forEach(binaryContentService::deleteStoreFileById);
    }
}
