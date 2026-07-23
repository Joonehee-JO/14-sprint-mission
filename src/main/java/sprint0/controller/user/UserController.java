package sprint0.controller.user;

import global.annotation.Comment;
import sprint0.service.user.UserService;

/*
    유저 서비스 관련 컨트롤러
 */
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Comment("유저 생성 컨트롤러 // 메서드명을 뭐로 해야할지 모르겠음")
    public Long initUser(UserDTO userDTO) throws Exception{
        return userService.initUser(userDTO.getName());
    }
}
