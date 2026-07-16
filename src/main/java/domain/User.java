package domain;

import java.time.LocalDateTime;

public class User {
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;

    public User(Long userId, String name) {
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getName() {
        return name;
    }
}
