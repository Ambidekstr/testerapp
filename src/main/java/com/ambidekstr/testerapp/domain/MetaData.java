package com.ambidekstr.testerapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * Created by anatolii on 09.08.2016.
 */
@Entity
public class MetaData {

    @Id
    @Column(name = "UUID", nullable = false,updatable = false)
    private UUID uuid;

    @Column(name="creationTime",nullable = false)
    private String creationTime;

    @Column(name="size",nullable = false)
    private Long size;

    public MetaData() {
    }

    public MetaData(UUID uuid, String creationTime, Long size) {
        this.uuid = uuid;
        this.creationTime = creationTime;
        this.size = size;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
