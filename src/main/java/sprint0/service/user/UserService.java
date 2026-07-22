package sprint0.service.user;

import sprint0.global.annotation.Comment;

public interface UserService {
    @Comment("최초 유저 생성 - 이름 입력 후 아이디발급")
    Long initUser(String name);
}
