package com.sprint.mission.discodeit.binarycontent.domain.entity;

import com.sprint.mission.discodeit.baseentity.BaseEntity;
import com.sprint.mission.discodeit.baseentity.BaseUpdatableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "binary_contents")
public class BinaryContent extends BaseEntity {
    @Column(name = "path_url", nullable = false)
    String pathUrl;

    @Column(name = "file_name", nullable = false)
    String fileName;

    @Column(nullable = false)
    long size;

    @Column(name = "content_type", nullable = false)
    String contentType;


    static public BinaryContent init(String pathUrl, String fileName, long size, String contentType){
        return BinaryContent.builder()
            .pathUrl(pathUrl)
            .fileName(fileName)
            .size(size)
            .contentType(contentType)
            .build();
    }

    public void updatePathUrl(String filePath){
        this.pathUrl = filePath;
    }
}
