package com.vladyslav.HealthcareClinic.service.interfac;

import com.vladyslav.HealthcareClinic.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface IImageService {

    Response addNewImage(MultipartFile photo, String description, LocalDate uploadedDate, Long patientId, Long staffId);

    Response getImageById(Long imageId);

    Response getAllImages();

    Response getImageByPatientId(Long patientId);

    Response getImageByStaffId(Long staffId);

    Response deleteImageByImageId(Long imageId);

    Response updateImage(Long imageId, MultipartFile photo, String description, LocalDate uploadedDate);


}
