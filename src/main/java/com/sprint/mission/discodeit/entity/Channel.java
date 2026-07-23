package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")       //해야하나..?
public class Channel implements Serializable {
    private static final long serialVersionUID = 1L;

    final UUID id;
    final Long createdAt;
    Long updatedAt;
    String channelName;

    private Channel(String channelName) {
        this.id = UUID.randomUUID();
        this.channelName = channelName;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = createdAt;
    }

    public void update(String channelName){
        this.channelName = channelName;
        this.updatedAt = System.currentTimeMillis();
    }

    public static Channel makeChannel(String channelName){
        return new Channel(channelName);
    }
}
