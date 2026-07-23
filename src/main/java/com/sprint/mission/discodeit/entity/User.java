package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    final UUID id;
    String name;
    final Long createdAt;
    Long updatedAt;


    private User(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        createdAt = System.currentTimeMillis();
        updatedAt = createdAt;
    }

    public void update(String name){
        this.name = name;
        updatedAt = System.currentTimeMillis();
    }

    public static User makeUser(String name){
        return new User(name);
    }
}
