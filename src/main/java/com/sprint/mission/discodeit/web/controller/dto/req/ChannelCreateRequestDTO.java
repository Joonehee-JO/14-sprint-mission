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
    ChannelType channelType;

    /*
        1. 프라이빗 생성 디티오 / 퍼블릭 생성 디티오 2개로 나눠 구현할지
        2. 컨트롤러에서 어떤 채널 타입이 들어온지 확인해서 서비스의 어떤 메서드를 호출할지
     */

}
