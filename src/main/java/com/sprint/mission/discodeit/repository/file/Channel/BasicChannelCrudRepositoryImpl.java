package com.sprint.mission.discodeit.repository.file.Channel;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.file.AbstractCrudRepository;
import java.util.List;
import java.util.UUID;

//Set으로 할걸 그랬나봄...
public class BasicChannelCrudRepositoryImpl extends AbstractCrudRepository<Channel> implements ChannelRepository{
    private static final String filePath = "src/main/java/";
    private static final String fileName = "channel.ser";

    @Override
    public void deleteEntity(UUID uuid) {
        List<Channel> channelList = findAllEntity();
//        for (Channel channel : channelList) {
//            if(channel.getId().equals(uuid)) channelList.remove(channel);
//        }

        //수정
        channelList.removeIf(entity -> entity.getId().equals(uuid));
    }

    @Override
    public Channel updateEntity(UUID uuid, String updateName) {
        Channel updateChannel = findById(uuid);
        if(updateChannel != null){
            updateChannel.update(updateName);
        }

        return null;
    }

    @Override
    public Channel findById(UUID uuid) {
        List<Channel> channelList = findAllEntity();
        return channelList.stream()
            .filter(channel -> channel.getId().equals(uuid))
            .findFirst()
            .orElse(null);
    }

    @Override
    protected String getFilePath() {
        return filePath + fileName;
    }
}
