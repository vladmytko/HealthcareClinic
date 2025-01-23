package com.vladyslav.HealthcareClinic.utils;

import com.vladyslav.HealthcareClinic.dto.*;
import com.vladyslav.HealthcareClinic.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    // Map User entity to UserDTO
    public static UserDTO mapUserEntityToUserDTO(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(user.getId(), user.getEmail(), user.getRole());
    }

    // Map Patient entity to PatientDTO
    public static PatientDTO mapPatientEntityToPatientDTO(Patient patient) {
        if (patient == null) {
            return null;
        }

        return new PatientDTO(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPhoneNumber(),
                patient.getDateOfBirth(),
                patient.getAddress(),
                patient.getDiagnosis(),
                patient.getConditions(),
                patient.getUser() != null ? patient.getUser().getEmail() : null
        );
    }

    // Map Staff entity to StaffDTO
    public static StaffDTO mapStaffEntityToStaffDTO(Staff staff){
        if(staff == null) {
            throw new IllegalStateException("Staff must have an associated user");
        }

        return new StaffDTO(
                staff.getId(),
                staff.getFirstName(),
                staff.getLastName(),
                staff.getPhoneNumber(),
                staff.getDateOfBirth(),
                staff.getAddress(),
                staff.getSpecialisation(),
                staff.getUser() != null ? staff.getUser().getEmail() : null
        );
    }

    //Map Task Entity to TaskDTO
    public static TaskDTO mapTastEntityToTaskDTO(Task task){
        if(task == null) {
            return null;
        }

        return new TaskDTO(
                task.getId(),
                task.getDescription(),
                task.getTimestamp(),
                task.getPatient() != null ? task.getPatient().getId() : null,
                task.getPatient() != null ? task.getPatient().getFirstName() + " " + task.getPatient().getLastName() : "Unknown staff",
                task.getStaff() != null ? task.getStaff().getId() : null,
                task.getStaff() != null ? task.getStaff().getFirstName() + " " + task.getStaff().getLastName() : "Unknown staff",
                task.isCompleted()
        );
    }

    // Map Image entity to ImageDTO
    public static ImageDTO mapImageEntityToImageDTO(Image image){
        if(image == null){
            return null;
        }

        return new ImageDTO(
                image.getId(),
                image.getDescription(),
                image.getUploadDate(),
                image.getImageUrl(),
                image.getPatient() != null ? image.getPatient().getId() : null
        );
    }

    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList) {
        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }

    public static List<StaffDTO> mapStaffListEntityToStaffListDTO(List<Staff> staffList) {
        return staffList.stream().map(Utils::mapStaffEntityToStaffDTO).collect(Collectors.toList());
    }

    public static List<PatientDTO> mapPatientListEntityToPatientListDTO(List<Patient> patientList) {
        return patientList.stream().map(Utils::mapPatientEntityToPatientDTO).collect(Collectors.toList());
    }

    public static List<TaskDTO> mapTaskListEntityToTaskListDTO(List<Task> taskList) {
        return taskList.stream().map(Utils::mapTastEntityToTaskDTO).collect(Collectors.toList());
    }

    public static List<ImageDTO> mapImageListEntityToImageListDTO(List<Image> imageList) {
        return imageList.stream().map(Utils::mapImageEntityToImageDTO).collect(Collectors.toList());
    }
}
