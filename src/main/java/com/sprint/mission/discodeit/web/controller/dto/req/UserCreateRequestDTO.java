package com.sprint.mission.discodeit.web.controller.dto.req;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequestDTO {
    String email;
    String userPassword;
    //String checkPassword;
    String name;
}
