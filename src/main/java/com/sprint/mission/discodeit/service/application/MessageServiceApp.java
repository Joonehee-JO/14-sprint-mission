package com.sprint.mission.discodeit.service.application;

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
