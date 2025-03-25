package com.vladyslav.HealthcareClinic.entity;



import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Description is required")
    private String description;

     @NotNull(message = "Date is required")
     @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate timestamp; // Default to today's date

    @NotBlank(message = "Price is required")
    private String price;

    private boolean isCompleted = false;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
    private Patient patient; // Task is assigned to a patient

    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id", nullable = false)
    private Staff staff; // Task is created by a staff member


    // GETTERS AND SETTERS


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Description is required") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Description is required") String description) {
        this.description = description;
    }

    public @NotNull(message = "Date is required") LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NotNull(message = "Date is required") LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public @NotBlank(message = "Price is required") String getPrice() {
        return price;
    }

    public void setPrice(@NotBlank(message = "Price is required") String price) {
        this.price = price;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }
}
