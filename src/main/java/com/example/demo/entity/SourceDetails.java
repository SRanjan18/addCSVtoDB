package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "source_details")
public class SourceDetails {


@Id
    @Column(name = "source_id", nullable = false, columnDefinition = "CHAR(36)")
    private String sourceId;  // Also store this as a string
    private String name;
    private String occupation;


    @Column(name = "dob")
    private String dob; // Date of birth in String format (consider using LocalDate)

    // Getters and Setters

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }




    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}