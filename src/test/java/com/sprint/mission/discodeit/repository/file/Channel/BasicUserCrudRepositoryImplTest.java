package com.sprint.mission.discodeit.repository.file.Channel;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.file.user.BasicUserCrudRepositoryImpl;
import com.sprint.mission.discodeit.repository.file.user.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class BasicUserCrudRepositoryImplTest {
    UserRepository userRepository = new BasicUserCrudRepositoryImpl();

    @Test
    void 유저_생성(){
        User testUser = User.makeUser("tester");
        User checkUser = userRepository.saveEntity(testUser  );
        Assertions.assertThat(testUser).isEqualTo(checkUser);

        List<User> userList = userRepository.findAllEntity();
        for (User user : userList) {
            log.info("유저 여기있음 : {} ", user);
        }
    }
}