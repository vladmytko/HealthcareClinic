package com.vladyslav.HealthcareClinic.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequest {
    @NotBlank(message = "Description is required")
    private String description;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate timestamp = LocalDate.now();

    @NotBlank(message = "Price is required")
    private String price;


    private boolean isCompleted = false;

    @NotNull(message = "Patient not selected")
    private Long patientId;

    @NotNull(message = "Staff not selected")
    private Long staffId;

    // GETTERS AND SETTERS


    public @NotBlank(message = "Description is required") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Description is required") String description) {
        this.description = description;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public @NotBlank(message = "Price is required") String getPrice() {
        return price;
    }

    public void setPrice(@NotBlank(message = "Price is required") String price) {
        this.price = price;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public @NotNull(message = "Patient not selected") Long getPatientId() {
        return patientId;
    }

    public void setPatientId(@NotNull(message = "Patient not selected") Long patientId) {
        this.patientId = patientId;
    }

    public @NotNull(message = "Staff not selected") Long getStaffId() {
        return staffId;
    }

    public void setStaffId(@NotNull(message = "Staff not selected") Long staffId) {
        this.staffId = staffId;
    }
}


