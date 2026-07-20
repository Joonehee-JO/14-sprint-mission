package controller.user;

import java.util.Scanner;

/*
    유저 입력 DTO // 이름밖에 없긴 하지만...
 */
public class UserDTO {
    private String name;

    public UserDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
