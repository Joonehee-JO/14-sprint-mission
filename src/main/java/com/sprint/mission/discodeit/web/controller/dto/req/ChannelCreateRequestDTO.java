package com.sprint.mission.discodeit.web.controller.dto.req;

import com.sprint.mission.discodeit.entity.ChannelType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ChannelCreateRequestDTO {
    String channelName;
}
