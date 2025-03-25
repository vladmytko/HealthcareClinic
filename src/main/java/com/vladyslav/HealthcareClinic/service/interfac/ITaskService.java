package com.vladyslav.HealthcareClinic.service.interfac;

import com.vladyslav.HealthcareClinic.dto.response.Response;
import com.vladyslav.HealthcareClinic.dto.requests.TaskRequest;

public interface ITaskService {

    Response addNewTask(TaskRequest taskRequest);

    Response getTaskById(Long taskId);

    Response getAllTasks();

    Response getTaskByStaffId(Long staffId);

    Response getTaskByPatientId(Long patientId);

    Response deleteTaskById(Long taskId);

    Response updateTask (Long taskId,
                         String description,
                         String price);


    Response markTaskAsCompleted (Long taskId, Boolean completed);

    }



