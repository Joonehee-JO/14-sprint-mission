package com.sprint.mission.discodeit.repository.file.message;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.file.AbstractCrudRepository;
import java.util.List;
import java.util.UUID;

public class BasicMessageCrudRepositoryImpl extends AbstractCrudRepository<Message> implements MessageRepository{
    final private String filePath = "src/main/java/";
    private final String fileName = "message.ser";

    @Override
    public Message findById(UUID uuid) {
        List<Message> messageList = findAllEntity();
        for (Message message : messageList) {
            if(message.getId().equals(uuid))return message;
        }

        return null;
    }

    @Override
    public Message updateEntity(UUID uuid, String updateName) {
        Message updateMessage = findById(uuid);
        updateMessage.update(updateName);

        return updateMessage;
    }

    @Override
    public void deleteEntity(UUID uuid) {
        List<Message> messageList = findAllEntity();
        for (Message message : messageList) {
            if(message.getId().equals(uuid)){
                messageList.remove(message);
            }
        }
    }

    @Override
    public List<Message> findAllMessageByChannel(UUID channelID) {
        List<Message> messageList = findAllEntity();
        return messageList.stream()
            .filter(message -> message.getChannelId().equals(channelID))
            .toList();
    }

    @Override
    protected String getFilePath() {
        return filePath+fileName;
    }
}
