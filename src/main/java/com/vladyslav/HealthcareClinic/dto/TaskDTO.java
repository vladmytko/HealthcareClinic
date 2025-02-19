package com.vladyslav.HealthcareClinic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;


import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class TaskDTO {
    private Long id;
    private String description;
    private LocalDate timestamp;
    private String price;
    private Long patientId;
    private String patientName;
    private Long staffId;
    private String staffName;
    private boolean isCompleted = false;

    public TaskDTO(Long id, String description, LocalDate timestamp, String price, Long patientId, String patientName, Long staffId, String staffName, boolean isCompleted) {
        this.id = id;
        this.description = description;
        this.timestamp = timestamp;
        this.price = price;
        this.patientId = patientId;
        this.patientName = patientName;
        this.staffId = staffId;
        this.staffName = staffName;
        this.isCompleted = isCompleted;
    }

    // GETTERS AND SETTERS


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
