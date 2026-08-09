//package com.sprint.mission.discodeit.service.channel;
//
//import com.sprint.mission.discodeit.repository.UserRepository;
//import com.sprint.mission.discodeit.service.user.UserService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class ChannelServiceImplTest {
//    @Autowired private UserRepository userRepository;
//    @Autowired private UserService userService;
//
//    @Test
//    void checkBeanInjection() {
//        // 빈이 정상적으로 스프링 컨테이너에 등록되고 주입되었는지 검증
//        Assertions.assertThat(userRepository).isNotNull();
//        Assertions.assertThat(userService).isNotNull();
//
//        System.out.println(userRepository);
//        System.out.println(userService);
//    }
//}