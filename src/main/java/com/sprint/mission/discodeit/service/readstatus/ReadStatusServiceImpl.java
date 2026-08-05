package com.sprint.mission.discodeit.service.readstatus;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReadStatusServiceImpl implements ReadStatusService{
    private final ReadStatusRepository readStatusRepository;
    private final ReadStatusService readStatusService;

    @Override
    public ReadStatus createReadStatus(ReadStatus readStatus) {
        if(Objects.isNull(readStatus)){
            throw new IllegalArgumentException("null x");
        }

        return readStatusRepository.saveEntity(readStatus);
    }

    @Override
    public ReadStatus findReadStatusById(UUID readStatusId) {
        if(Objects.isNull(readStatusId)){
            throw new IllegalArgumentException("null x");
        }

        return readStatusRepository.findById(readStatusId)
            .orElseThrow(() ->  new IllegalArgumentException("해당 리드스테이터스XX "));
    }

    @Override
    public List<ReadStatus> findAllReadStatusByChannelId(UUID channelId) {
        if(Objects.isNull(channelId)){
            throw new IllegalArgumentException("null x");
        }

        return readStatusRepository.findAllEntityByChannelId(channelId);
    }

    @Override
    public List<ReadStatus> findReadStatusByUserId(UUID userId) {
        if(Objects.isNull(userId)){
            throw new IllegalArgumentException("null x");
        }

        return readStatusRepository.findAllReadStatusByUserId(userId);
    }

    @Override
    public void deleteReadStatusById(UUID readStatusId) {
        if(Objects.isNull(readStatusId)){
            throw new IllegalArgumentException("null x");
        }

        this.findReadStatusById(readStatusId);

        readStatusRepository.deleteEntity(readStatusId);
    }

    @Override
    public void deleteReadStatusByChannelId(UUID channelId) {
        if(Objects.isNull(channelId)){
            throw new IllegalArgumentException("null x");
        }

        readStatusRepository.deleteReadStatusByChannelId(channelId);
    }

    @Override
    public ReadStatus updateReadStatusReadTime(UUID readStatusId) {
        if(Objects.isNull(readStatusId)){
            throw new IllegalArgumentException("null x");
        }

        ReadStatus readStatus = this.findReadStatusById(readStatusId);
        readStatus.updateReadTime();

        return readStatusRepository.saveEntity(readStatus);
    }
}
