package com.sprint.mission.discodeit.repository.file.Channel;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.CrudRepository;
import com.sprint.mission.discodeit.repository.file.FileCrudRepository;
import java.util.UUID;

/*
    참조용 ... 맞나?
 */
public interface ChannelRepository extends FileCrudRepository<Channel, UUID> {

}
