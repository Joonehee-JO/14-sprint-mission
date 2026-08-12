package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.service.user.UserService;
import com.sprint.mission.discodeit.service.userstatus.UserStatusService;
import jakarta.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/*
    todo : 포스트맨 임시 테스트용 - 추후 삭제
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class TemporalDataInit {
    private final UserService userService;
    private final UserStatusService userStatusService;

    @PostConstruct
    public void init() {
        UUID defaultId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        User testUser = User.init("tester", "1234", "홍길동", 25);

        try{
            Field idField = User.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(testUser, defaultId);
        }catch (Exception e){
            log.error("sad",e);
        }

        userService.createUser(testUser);
        log.info("생성 완료 {}", testUser.getId());

        UserStatus userStatus = UserStatus.init(testUser.getId());
        userStatusService.createUserStatus(userStatus   );
        log.info("스테이터스 생성 완료 {}", userStatus.getUserId());
    }
}
