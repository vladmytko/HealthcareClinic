package com.vladyslav.HealthcareClinic.service.interfac;

import com.vladyslav.HealthcareClinic.dto.Response;
import com.vladyslav.HealthcareClinic.entity.Patient;
import com.vladyslav.HealthcareClinic.entity.Staff;
import com.vladyslav.HealthcareClinic.entity.Task;

import java.time.LocalDate;

public interface ITaskService {

    Response addNewTask(String description,
                        LocalDate timestamp,
                        String price,
                        Long patientId,
                        Long staffId);

    Response getTaskById(Long taskId);

    Response getAllTasks();

    Response getTaskByStaffId(Long staffId);

    Response getTaskByPatientId(Long patientId);

    Response deleteTaskById(Long taskId);

    Response updateTask (Long taskId,
                         String description,
                         LocalDate timestamp,
                         String price,
                         Patient patient);


    Response isTaskCompleted (Long taskId, boolean isCompleted);



    }



