package com.sprint.mission.discodeit.message.web.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageUpdateRequestDTO {
    String newContent;
}
