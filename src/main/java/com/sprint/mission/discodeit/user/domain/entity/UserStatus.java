package com.sprint.mission.discodeit.user.domain.entity;

import com.sprint.mission.discodeit.baseentity.BaseUpdatableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.Duration;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_statuses")
public class UserStatus extends BaseUpdatableEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "last_active_at", nullable = false)
    Instant lastActiveAt;

    @Transient
    @Builder.Default
    boolean online = false;

    static public UserStatus init(User user){
        return UserStatus.builder()
            .user(user)
            .lastActiveAt(Instant.now())
            .build();
    }

    public void login(){
        this.online = true;
        activateUser();
    }

    public void activateUser(){
        this.lastActiveAt = Instant.now();
    }

    public void activateUser(Instant activeAt){
        this.lastActiveAt = activeAt;               // ?
    }

    public boolean isActive(){
        if(!this.online) return false;

        Duration difference = Duration.between(this.lastActiveAt, Instant.now());
        return difference.toMinutes() < 5;
    }
}
