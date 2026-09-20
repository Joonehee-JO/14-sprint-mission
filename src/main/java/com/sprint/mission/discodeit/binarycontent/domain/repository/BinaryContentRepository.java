package com.sprint.mission.discodeit.binarycontent.domain.repository;

import com.sprint.mission.discodeit.binarycontent.domain.entity.BinaryContent;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BinaryContentRepository extends JpaRepository<BinaryContent, UUID> {

}
