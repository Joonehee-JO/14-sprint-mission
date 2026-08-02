package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


/*
    어떤 유저가 어디 채널을 언제 마지막으로 읽었는지 체크하고
    이로 그 이후의 메시지는 읽지않음으로 표시하기 위한 엔터티
 */
@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadStatus {
    UUID id = UUID.randomUUID();
    UUID userId;
    UUID channelId;
    Instant latestReadAt;
}
