package com.vladyslav.HealthcareClinic.service.impl;

import com.vladyslav.HealthcareClinic.dto.Response;
import com.vladyslav.HealthcareClinic.dto.TaskDTO;
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
    public Response addNewTask(String description,
                               LocalDate timestamp,
                               String price,
                               Long patientId,
                               Long staffId) {

        Response response = new Response();

        try {
            // Check if task with same id exists, if yes this indicates issues with database
//            if(taskRepository.existsById(task.getId())) {
//                throw new OurException("Task with id " + task.getId() + " already exists");
//            }

            Task task = new Task();

            // Validate and fetch Patient
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new OurException("Patient not found with ID: " + patientId));

            // Validate and fetch Staff
            Staff staff = staffRepository.findById(staffId)
                    .orElseThrow(() -> new OurException("Staff not found with ID: " + staffId));

            task.setPatient(patient);
            task.setStaff(staff);
            task.setDescription(description);
            task.setTimestamp(timestamp);
            task.setPrice(price);

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
            response.setMessage("Error creating new task" + e.getMessage());
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
    public Response updateTask(Long taskId, String description, LocalDate timestamp, String price, Patient patient) {

        Response response = new Response();

        try {
            // Fetch task from database
            Task task = taskRepository.findById(taskId).orElseThrow(()-> new OurException("Task Not Found"));

            // Update the task details
            if(description != null) task.setDescription(description);
            if(timestamp != null) task.setTimestamp(timestamp);
            if(price != null) task.setPrice(price);
            if(patient != null) task.setPatient(patient);


            // Save the updated task
            taskRepository.save(task);

            // Map to DTO
            TaskDTO taskDTO = Utils.mapTastEntityToTaskDTO(task);

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
    public Response isTaskCompleted(Long taskId, boolean isCompleted) {

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
