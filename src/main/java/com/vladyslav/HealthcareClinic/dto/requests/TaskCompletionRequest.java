package com.vladyslav.HealthcareClinic.dto.requests;

import lombok.Data;

@Data
public class TaskCompletionRequest {
    private Boolean completed;

    // Getter and Setter

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
