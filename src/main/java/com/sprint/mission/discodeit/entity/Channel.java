package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Channel implements Serializable, IdMapper {
    private static final long serialVersionUID = 1L;

    UUID id = UUID.randomUUID();
    String channelName;
    @Builder.Default
    ChannelType channelType = ChannelType.PUBLIC_CHANNEL;       //최초 채널 생성 시 기본값 퍼블릭으로 설정

    @Builder.Default
    Instant createdAt = Instant.now();
    @Builder.Default
    Instant updatedAt = Instant.now();

    public void update(String channelName){
        this.channelName = channelName;
        this.updatedAt = Instant.now();
    }
}
