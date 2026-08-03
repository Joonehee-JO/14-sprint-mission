//package com.sprint.mission.discodeit.repository.file.user;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.sprint.mission.discodeit.entity.Channel;
//import com.sprint.mission.discodeit.repository.file.Channel.BasicChannelCrudRepositoryImpl;
//import com.sprint.mission.discodeit.repository.file.Channel.ChannelRepository;
//import java.util.List;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//
//@Slf4j
//class BasicChannelCrudRepositoryImplTest {
//    ChannelRepository channelRepository = new BasicChannelCrudRepositoryImpl();
//
//    @Test
//    void 채널_생성(){
//
//        Channel testChannel = Channel.makeChannel("test");
//        Channel saveChannel = channelRepository.saveEntity(testChannel);
//        log.info("{}", saveChannel);
//        //assertThat()
//        assertThat(channelRepository.findById(testChannel.getId())).isEqualTo(testChannel);
//    }
//
//    @Test
//    void 채널_업뎃()    {
//        List<Channel> channelList = channelRepository.findAllEntity();
//        for (Channel channel : channelList) {
//            log.info("여기 있어요 : {} ", channel);
//        }
//    }
//}