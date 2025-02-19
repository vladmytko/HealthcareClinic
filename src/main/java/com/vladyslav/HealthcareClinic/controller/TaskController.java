package com.vladyslav.HealthcareClinic.controller;

import com.vladyslav.HealthcareClinic.dto.Response;
import com.vladyslav.HealthcareClinic.dto.requests.TaskRequest;
import com.vladyslav.HealthcareClinic.entity.Patient;
import com.vladyslav.HealthcareClinic.entity.Staff;
import com.vladyslav.HealthcareClinic.entity.User;
import com.vladyslav.HealthcareClinic.repo.StaffRepository;
import com.vladyslav.HealthcareClinic.repo.UserRepository;
import com.vladyslav.HealthcareClinic.service.interfac.ITaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private ITaskService taskService;


    @PostMapping("/add-task")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> addTask(@Valid  @RequestBody TaskRequest registerTaskRequest) {

        Response response = taskService.addNewTask(registerTaskRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-task-by-id/{taskId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getTaskById(@PathVariable Long taskId) {
        Response response = taskService.getTaskById(taskId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all-tasks")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllTasks() {
        Response response = taskService.getAllTasks();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-task-by-staff-id/{staffId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> getTaskByStaffId(@PathVariable Long staffId) {
        Response response = taskService.getTaskByStaffId(staffId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-task-by-patient-id/{patientId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> getTaskByPatientId(@PathVariable Long patientId) {
        Response response = taskService.getTaskByPatientId(patientId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete-task-by-id/{taskId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> deleteTask(@PathVariable Long taskId) {
        Response response = taskService.deleteTaskById(taskId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update-task/{taskId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> updateTask(@PathVariable Long taskId,
                                               @RequestParam(value = "description", required = false) String description,
                                               @RequestParam(value = "price", required = false) String price){
        Response response = taskService.updateTask(taskId, description, price);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



    @PutMapping("/completion-status/{taskId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> updateTaskCompletionStatus(
            @PathVariable Long taskId) {
        Response response = taskService.taskCompleted(taskId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
