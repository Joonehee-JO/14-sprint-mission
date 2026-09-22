package com.sprint.mission.discodeit.user.web.dto.req;


import com.fasterxml.jackson.annotation.JsonProperty;

public record UserLoginRequestDTO(
    @JsonProperty("username") String email,
    String password
) {

}
