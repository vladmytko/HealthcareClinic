package com.vladyslav.HealthcareClinic.service.impl;

import com.vladyslav.HealthcareClinic.dto.Response;
import com.vladyslav.HealthcareClinic.dto.TaskDTO;
import com.vladyslav.HealthcareClinic.dto.requests.TaskRequest;
import com.vladyslav.HealthcareClinic.entity.Patient;
import com.vladyslav.HealthcareClinic.entity.Staff;
import com.vladyslav.HealthcareClinic.entity.Task;
import com.vladyslav.HealthcareClinic.exception.OurException;
import com.vladyslav.HealthcareClinic.repo.PatientRepository;
import com.vladyslav.HealthcareClinic.repo.StaffRepository;
import com.vladyslav.HealthcareClinic.repo.TaskRepository;
import com.vladyslav.HealthcareClinic.service.interfac.ITaskService;
import com.vladyslav.HealthcareClinic.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private StaffRepository staffRepository;


    @Override
    public Response addNewTask(TaskRequest taskRequest) {

        Response response = new Response();

        try {
            // Validate required fields
            if (taskRequest.getPatientId() == null || taskRequest.getStaffId() == null) {
                throw new OurException("Patient ID and Staff ID cannot be null.");
            }

            Task task = new Task();

            // Validate and fetch Patient
            Patient patient = patientRepository.findById(taskRequest.getPatientId())
                    .orElseThrow(() -> new OurException("Patient not found with ID: " + taskRequest.getPatientId()));

            // Validate and fetch Staff
            Staff staff = staffRepository.findById(taskRequest.getStaffId())
                    .orElseThrow(() -> new OurException("Staff not found with ID: " + taskRequest.getStaffId()));



            task.setPatient(patient);
            task.setStaff(staff);
            task.setDescription(taskRequest.getDescription());
            task.setTimestamp(taskRequest.getTimestamp());
            task.setPrice(taskRequest.getPrice());
            task.setCompleted(taskRequest.isCompleted());

            // Save new task
            Task savedTask = taskRepository.save(task);
            TaskDTO taskDTO = Utils.mapTastEntityToTaskDTO(savedTask);

            // Set response
            response.setStatusCode(201);
            response.setMessage("successful");
            response.setTaskDTO(taskDTO);

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error creating new task " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getTaskById(Long taskId) {

        Response response = new Response();

        try {
            // Fetch task from database
            Task task = taskRepository.findById(taskId).orElseThrow(()-> new OurException("Task Not Found"));

            // Map task entity to task DTO
            TaskDTO taskDTO = Utils.mapTastEntityToTaskDTO(task);

            // Set response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setTaskDTO(taskDTO);
        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error getting task by Id" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllTasks() {

        Response response = new Response();

        try {
            //Fetch tasks from database
            List<Task> taskList = taskRepository.findAll();

            // Map task list entity to task list DTO
            List<TaskDTO> taskDTOList = Utils.mapTaskListEntityToTaskListDTO(taskList);

            // Set response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setTaskList(taskDTOList);
        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error getting all tasks" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getTaskByStaffId(Long staffId) {
        Response response = new Response();

        try {
            //Fetch tasks from database
            List<Task> taskList = taskRepository.findTaskByStaffId(staffId);
            // Map task list entity to task list DTO
            List<TaskDTO> taskDTOList = Utils.mapTaskListEntityToTaskListDTO(taskList);

            // Set response
            response.setStatusCode(201);
            response.setMessage("successful");
            response.setTaskList(taskDTOList);
        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error getting task by staff id " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getTaskByPatientId(Long patientId) {
        Response response = new Response();

        try {
            //Fetch tasks from database
            List<Task> taskList = taskRepository.findTaskByPatientId(patientId);
            // Map task list entity to task list DTO
            List<TaskDTO> taskDTOList = Utils.mapTaskListEntityToTaskListDTO(taskList);

            // Set response
            response.setStatusCode(201);
            response.setMessage("successful");
            response.setTaskList(taskDTOList);
        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error getting task by patient id " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response deleteTaskById(Long taskId) {

        Response response = new Response();

        try {
            //Fetch tasks from database
            taskRepository.findById(taskId).orElseThrow(() -> new OurException("Task Not Found"));

            // Delete task
            taskRepository.deleteById(taskId);

            // Set response
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error deleting task" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response updateTask(Long taskId, String description, String price) {

        Response response = new Response();

        try {
            // Fetch task from database
            Task task = taskRepository.findById(taskId).orElseThrow(()-> new OurException("Task Not Found"));

            // Update the task details
            if(description != null) task.setDescription(description);
            if(price != null) task.setPrice(price);


            // Save the updated task
            Task updatedTask = taskRepository.save(task);

            // Map to DTO
            TaskDTO taskDTO = Utils.mapTastEntityToTaskDTO(updatedTask);

            // Prepare successful response
            response.setStatusCode(200);
            response.setMessage("Task updated successfully");
            response.setTaskDTO(taskDTO);
        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response taskCompleted(Long taskId) {

        Response response = new Response();

        try {
            // Fetch task from database
            Task task = taskRepository.findById(taskId).orElseThrow(()-> new OurException("Task Not Found"));

            // Update 'isCompleted'
            task.setCompleted(true);

            // Save updated task
            Task updatedTask = taskRepository.save(task);

            // Map updated task to task DTO
            TaskDTO taskDTO = Utils.mapTastEntityToTaskDTO(updatedTask);

            // Set response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setTaskDTO(taskDTO);
        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error " + e.getMessage());
        }

        return response;
    }
}
