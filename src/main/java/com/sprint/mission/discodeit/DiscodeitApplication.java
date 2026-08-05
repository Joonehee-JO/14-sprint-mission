package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.service.channel.ChannelService;
import com.sprint.mission.discodeit.service.message.MessageService;
import com.sprint.mission.discodeit.service.user.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class DiscodeitApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);
		// 서비스 초기화
		// TODO context에서 Bean을 조회하여 각 서비스 구현체 할당 코드 작성하세요.
		UserService userService;
		ChannelService channelService;
		MessageService messageService;



		userService = context.getBean(UserService.class);
		channelService = context.getBean(ChannelService.class);
		messageService = context.getBean(MessageService.class);

//		Channel channel1 = channelService.makeChannel("해위");
//		User user1 = userService.createUser("tester");
//		messageService.createMessage(user1.getId(), channel1.getId(), "dsadsasdads");
//		messageService.createMessage(user1.getId(), channel1.getId(), "ㅇㄴㅁㅇㄴㅁㅇㄴㅁ");
//		messageService.createMessage(user1.getId(), channel1.getId(), "ㅇㅁㅇㄴㅁㅁㄴ");
//
//		List<Message> messageList = messageService.findAllMessageByChannel(channel1.getId());
//		for (Message message : messageList) {
//			log.info("{}", message);
//		}
	}

}
