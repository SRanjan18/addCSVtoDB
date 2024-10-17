package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "source_master")
public class SourceMaster {


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Id
    @Column(name = "uuid", columnDefinition = "CHAR(36)")  // Ensure it's stored as a 36-character string
    private String uuid = UUID.randomUUID().toString();  // Store UUID as a String

    @Column(name = "source_id", unique = true, nullable = false)
    private int sourceId;

    // Getters and Setters

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
}