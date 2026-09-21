package com.sprint.mission.discodeit.readstatus.domain.repository;

import com.sprint.mission.discodeit.readstatus.domain.entity.ReadStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReadStatusRepository extends JpaRepository<ReadStatus, UUID> {
    List<ReadStatus> findAllByChannelId(UUID channelId);

    // N+1 방지
    @Query("""
        select readStatus.user.id
        from ReadStatus readStatus
        where readStatus.channel.id = :channelId
        """)
    List<UUID> findUserIdsByChannelId(UUID channelId);
}
