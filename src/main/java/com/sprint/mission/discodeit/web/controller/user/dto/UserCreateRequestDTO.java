제package com.sprint.mission.discodeit.web.controller.user.dto;

import com.sprint.mission.discodeit.entity.BinaryContent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequestDTO {
    String userId;
    String userPassword;
    String checkPassword;
    String name;
    String email;
    Integer age;

    //파일 이미지
    BinaryContent profileImage;
}
