package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.Channel.BasicChannelCrudRepositoryImpl;
import com.sprint.mission.discodeit.repository.file.message.BasicMessageCrudRepositoryImpl;
import com.sprint.mission.discodeit.repository.file.user.BasicUserCrudRepositoryImpl;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.file.BasicChannelServiceImpl;
import com.sprint.mission.discodeit.service.file.BasicMessageServiceImpl;
import com.sprint.mission.discodeit.service.file.BasicUserServiceImpl;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class JavaApplication {
//    static User setupUser(UserService userService) {
//        User user = userService.createUser("woody");
//        return user;
//    }
//
//    static Channel setupChannel(ChannelService channelService) {
//        Channel channel = channelService.makeChannel("공지공지");
//        return channel;
//    }
//
//    static void messageCreateTest(MessageService messageService, Channel channel, User author) {
//        Message message = messageService.createMessage(author.getId(), channel.getId(), "hawe");
//        System.out.println("메시지 생성: " + message.getId());
//    }

    public static void main(String[] args) {
        // 서비스 초기화
        // TODO Basic*Service 구현체를 초기화하세요.
        UserService userService;
        ChannelService channelService;
        MessageService messageService;

        // 셋업
        //User user = setupUser(new BasicUserServiceImpl(new BasicUserCrudRepositoryImpl()));
        //Channel channel = setupChannel(new BasicChannelServiceImpl(new BasicChannelCrudRepositoryImpl()));
        // 테스트
        //messageCreateTest(new BasicMessageServiceImpl(new BasicMessageCrudRepositoryImpl()), channel, user);
        userService = new BasicUserServiceImpl(new BasicUserCrudRepositoryImpl());
        channelService = new BasicChannelServiceImpl(new BasicChannelCrudRepositoryImpl());
        messageService = new BasicMessageServiceImpl(new BasicMessageCrudRepositoryImpl());

        Channel channel1 = channelService.makeChannel("해위");
        User user1 = userService.createUser("tester");
        messageService.createMessage(user1.getId(), channel1.getId(), "dsadsasdads");
        messageService.createMessage(user1.getId(), channel1.getId(), "ㅇㄴㅁㅇㄴㅁㅇㄴㅁ");
        messageService.createMessage(user1.getId(), channel1.getId(), "ㅇㅁㅇㄴㅁㅁㄴ");

        List<Message> messageList = messageService.findAllMessageByChannel(channel1.getId());
        for (Message message : messageList) {
            log.info("{}", message);
        }
    }
}