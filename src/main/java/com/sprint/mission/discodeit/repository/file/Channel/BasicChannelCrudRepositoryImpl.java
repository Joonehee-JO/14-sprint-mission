//package com.sprint.mission.discodeit.repository.file.Channel;
//
//import com.sprint.mission.discodeit.entity.Channel;
//import com.sprint.mission.discodeit.repository.file.AbstractCrudRepository;
//import java.util.List;
//import java.util.UUID;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Repository;
//
////Set으로 할걸 그랬나봄...
////@Repository
//public class BasicChannelCrudRepositoryImpl extends AbstractCrudRepository<Channel> implements ChannelRepository{
//    private static final String filePath = "src/main/java/";
//    private static final String fileName = "channel.ser";
//
//    @Override
//    public void deleteEntity(UUID uuid) {
//        List<Channel> channelList = findAllEntity();
////        for (Channel channel : channelList) {
////            if(channel.getId().equals(uuid)) channelList.remove(channel);
////        }
//
//        //수정
//        channelList.removeIf(entity -> entity.getId().equals(uuid));
//    }
//
//    @Override
//    public Channel updateEntity(UUID uuid, String updateName) {
//        /*
//            피드백 -> 이 부분 널 던지는거 이상하다
//         */
//        //서비스 계층에서 해당 엔터티를 찾아 넘겨준걸 업데이트하는게 맞다고봄
//        Channel updateChannel = findById(uuid);
//
//        //서비스계층에서 확인된 개체를 던지므로 원래 널안씀
//        if(updateChannel != null){
//            updateChannel.update(updateName);
//            return updateChannel;
//        }
//
//        throw new RuntimeException("디비 오류");
//    }
//
//    @Override
//    public Channel findById(UUID uuid) {
//        List<Channel> channelList = findAllEntity();
//        return channelList.stream()
//            .filter(channel -> channel.getId().equals(uuid))
//            .findFirst()
//            .orElse(null);
//    }
//
//    @Override
//    protected String getFilePath() {
//        return filePath + fileName;
//    }
//}
