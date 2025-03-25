package com.vladyslav.HealthcareClinic.controller;

import com.vladyslav.HealthcareClinic.dto.requests.TaskCompletionRequest;
import com.vladyslav.HealthcareClinic.dto.response.Response;
import com.vladyslav.HealthcareClinic.dto.requests.TaskRequest;
import com.vladyslav.HealthcareClinic.service.interfac.IPatientService;
import com.vladyslav.HealthcareClinic.service.interfac.IStaffService;
import com.vladyslav.HealthcareClinic.service.interfac.ITaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private IStaffService staffService;


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

    @PatchMapping("/update-task/{taskId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> updateTask(@PathVariable Long taskId,
                                               @RequestBody Map<String,String> requestBody){

        String description = requestBody.get("description");
        String price = requestBody.get("price");

        Response response = taskService.updateTask(taskId, description, price);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("/complete-task/{taskId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> markTaskAsCompleted(@PathVariable Long taskId,
                                                        @RequestBody TaskCompletionRequest request) {

        Boolean completed = request.getCompleted();

        if(completed == null) {
            return ResponseEntity.badRequest().body(new Response(400,"Completed status is required"));
        }

        Response response = taskService.markTaskAsCompleted(taskId, completed);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    // Staff and User retrieving tasks automatically

    @GetMapping("/patient-tasks")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<Response> getPatientTasks() {
        // Get patient ID automatically from the JWT token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fetchedEmail = authentication.getName(); // Get email from JWT token
        Long patientId = patientService.getPatientIdByEmail(fetchedEmail); // Find patient ID

        Response response = taskService.getTaskByPatientId(patientId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/staff-tasks")
    @PreAuthorize("hasAuthority('STAFF')")
    public ResponseEntity<Response> getStaffTasks() {

        // Get staff ID automatically from the JWT token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fetchedEmail = authentication.getName(); // Get email from JWT token
        Long staffId = staffService.getStaffIdByEmail(fetchedEmail); // Find patient ID

        Response response = taskService.getTaskByStaffId(staffId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
