package sprint0.controller.user;

import sprint0.global.exception.CustomErrorCode;
import sprint0.global.exception.CustomException;

/*
    유저 입력 DTO // 이름밖에 없긴 하지만...
 */
public class UserDTO {
    private String name;

    public UserDTO(String name) {
        validName(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void validName(String name){
        if(name.isEmpty()) throw new CustomException(CustomErrorCode.INVALID_USER_NAME_EMPTY);
    }
}
