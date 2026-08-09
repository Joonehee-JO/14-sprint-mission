package com.sprint.mission.discodeit.repository.map.channel;

import static org.junit.jupiter.api.Assertions.*;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.CrudRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ChannelMapCrudRepositoryImplTest {
    @Autowired
    private CrudRepository<Channel, UUID> crudRepository;


}