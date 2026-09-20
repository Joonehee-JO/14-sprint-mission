package com.sprint.mission.discodeit.message.application;

import com.sprint.mission.discodeit.binarycontent.domain.entity.BinaryContent;
import com.sprint.mission.discodeit.message.domain.entity.Message;
import com.sprint.mission.discodeit.binarycontent.domain.service.BinaryContentService;
import com.sprint.mission.discodeit.message.domain.service.MessageService;
import com.sprint.mission.discodeit.user.domain.service.UserService;
import com.sprint.mission.discodeit.message.web.dto.MessageCreateRequestDTO;
import com.sprint.mission.discodeit.message.web.dto.MessageResponseDTO;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class MessageServiceApp {
    private final MessageService messageService;
    private final BinaryContentService binaryContentService;
    private final UserService userService;


    public MessageResponseDTO createMessage(MessageCreateRequestDTO messageCreateRequestDTO, List<MultipartFile> files){
        userService.findUserById(messageCreateRequestDTO.getAuthorId());
        Message message = Message.init(messageCreateRequestDTO.getAuthorId(), messageCreateRequestDTO.getChannelId(), messageCreateRequestDTO.getContent());

        /*
            1. dto 에 파일이 들어있는지 확인
            2. 있으면 바이너리컨텐트서비스 호출
            3. 메시지 서비스 호출
         */

        List<BinaryContent> storedBinaryContents;
        List<UUID> filteredBinaryContents = null;
        if(Objects.nonNull(files) && !files.isEmpty()){
            storedBinaryContents = binaryContentService.storeFiles(files);

            filteredBinaryContents = storedBinaryContents.stream()
                .map(BinaryContent::getId)
                .toList();
        }

//        if(Objects.nonNull(messageCreateRequestDTO.getImageList())){
//            storedBinaryContents = binaryContentService.storeFiles(messageCreateRequestDTO.getImageList());
//            //서비스 필요형태로 변환
//            filteredBinaryContents = storedBinaryContents.stream()
//                .map(BinaryContent::getId)
//                .toList();
//        }

        Message createdMessage = messageService.createMessage(message, filteredBinaryContents);
        return MessageResponseDTO.from(createdMessage);
    }


    public void deleteMessage(UUID messageId){
        Message message = messageService.findMessageById(messageId);
        messageService.deleteMessage(messageId);

        if(message.hasImageList()){
            message.getImageList()
                .forEach(binaryContentService::deleteStoreFileById);
        }
    }
}
