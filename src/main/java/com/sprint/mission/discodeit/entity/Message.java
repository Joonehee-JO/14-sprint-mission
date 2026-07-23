package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")       //필요없을거같음. 그냥 리스트 전부 덮어버림
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    final UUID id;
    final UUID userId;
    final UUID channelId;
    final Long createdAt;
    Long updatedAt;
    String content;


    private Message(UUID userId, UUID channelId, String content) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.channelId = channelId;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = createdAt;
        this.content = content;
    }

    public void update(String content){
        this.content = content;
        this.updatedAt = System.currentTimeMillis();
    }

    public Message makeMessage(UUID userId, UUID channelId, String content){
        return new Message(userId,channelId,content);
    }
}
