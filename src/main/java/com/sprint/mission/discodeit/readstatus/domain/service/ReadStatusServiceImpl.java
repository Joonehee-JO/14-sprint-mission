package com.sprint.mission.discodeit.readstatus.domain.service;

import com.sprint.mission.discodeit.readstatus.domain.entity.ReadStatus;
import com.sprint.mission.discodeit.readstatus.domain.repository.map.MapReadStatusRepository;
import com.sprint.mission.discodeit.global.exception.CustomErrorCode;
import com.sprint.mission.discodeit.global.exception.CustomException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ReadStatusServiceImpl implements ReadStatusService{
    private final MapReadStatusRepository mapReadStatusRepository;

    @Override
    public ReadStatus createReadStatus(ReadStatus readStatus) {
        // 동일 필드 방지 검증
        if(validCreatable(readStatus.getUserId(), readStatus.getChannelId())){
            return mapReadStatusRepository.saveEntity(readStatus);
        }

        throw new CustomException(CustomErrorCode.READ_STATUS_DUPLICATE);
    }

    @Override
    public ReadStatus findReadStatusById(UUID readStatusId) {

        return mapReadStatusRepository.findById(readStatusId)
            .orElseThrow(() ->  new CustomException(CustomErrorCode.READ_STATUS_NOT_FOUND));
    }

    @Override
    public List<ReadStatus> findAllReadStatusByChannelId(UUID channelId) {

        return mapReadStatusRepository.findAllEntityByChannelId(channelId);
    }

    @Override
    public List<ReadStatus> findReadStatusByUserId(UUID userId) {

        return mapReadStatusRepository.findAllReadStatusByUserId(userId);
    }

    @Override
    public void deleteReadStatusById(UUID readStatusId) {

        this.findReadStatusById(readStatusId);

        mapReadStatusRepository.deleteEntity(readStatusId);
    }

    @Override
    public void deleteReadStatusByChannelId(UUID channelId) {

        mapReadStatusRepository.deleteReadStatusByChannelId(channelId);
    }

    @Override
    public ReadStatus updateReadStatusReadTime(UUID readStatusId, Instant updateTime) {

        ReadStatus readStatus = this.findReadStatusById(readStatusId);
        readStatus.updateReadTime(updateTime);

        return mapReadStatusRepository.saveEntity(readStatus);
    }

    private boolean validCreatable(UUID userId, UUID channelId){
        return mapReadStatusRepository.findReadStatusByUserIdAndChannelId(userId, channelId)
            .isEmpty();
    }
}
