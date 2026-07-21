package controller.user;

import java.util.Scanner;

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
        if(name.isEmpty()) throw new IllegalArgumentException("이름을 제대로 입력해주세요");
    }
}
