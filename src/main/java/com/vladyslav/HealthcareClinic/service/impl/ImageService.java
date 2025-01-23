package com.vladyslav.HealthcareClinic.service.impl;

import com.vladyslav.HealthcareClinic.dto.ImageDTO;
import com.vladyslav.HealthcareClinic.dto.Response;
import com.vladyslav.HealthcareClinic.dto.TaskDTO;
import com.vladyslav.HealthcareClinic.entity.Image;
import com.vladyslav.HealthcareClinic.entity.Patient;
import com.vladyslav.HealthcareClinic.entity.Staff;
import com.vladyslav.HealthcareClinic.entity.Task;
import com.vladyslav.HealthcareClinic.exception.OurException;
import com.vladyslav.HealthcareClinic.repo.ImageRepository;
import com.vladyslav.HealthcareClinic.repo.PatientRepository;
import com.vladyslav.HealthcareClinic.repo.StaffRepository;
import com.vladyslav.HealthcareClinic.service.AwsS3Service;
import com.vladyslav.HealthcareClinic.service.interfac.IImageService;
import com.vladyslav.HealthcareClinic.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public class ImageService implements IImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private StaffRepository staffRepository;


    @Autowired
    private AwsS3Service awsS3Service;


    @Override
    public Response addNewImage(MultipartFile photo, String description, LocalDate uploadedDate, Long patientId, Long staffId) {

        Response response = new Response();

        try {
            // Save image to S3 and get the URL
            String imageUrl = awsS3Service.saveImageToS3(photo);

            // Create image entity
            Image imageRecord = new Image();
            imageRecord.setImageUrl(imageUrl);
            imageRecord.setDescription(description);
            imageRecord.setUploadDate(uploadedDate);

            // Fetch Patient Entity by ID
            Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(()-> new OurException("Patient not found with ID: " + patientId));

            // Fetch Staff entity by ID
            Staff staff = staffRepository.findById(staffId)
                    .orElseThrow(()-> new OurException("Staff not found with ID: " + staffId));

            // Link Patient and Staff to image entity
            imageRecord.setPatient(patient);
            imageRecord.setStaff(staff);

            // Save image
            Image savedImage = imageRepository.save(imageRecord);

            // Mat to DTO
            ImageDTO imageDTO = Utils.mapImageEntityToImageDTO(savedImage);

            // Set Response
            response.setStatusCode(201);
            response.setMessage("successful");
            response.setImageDTO(imageDTO);
        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error saving image " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getImageById(Long imageId) {
        Response response = new Response();

        try {
            // Fetch task from database
            Image image = imageRepository.findById(imageId).orElseThrow(()-> new OurException("Image Not Found"));

            // Map task entity to task DTO
           ImageDTO imageDTO = Utils.mapImageEntityToImageDTO(image);

            // Set response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setImageDTO(imageDTO);
        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error getting image by Id" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getAllImages() {
        Response response = new Response();

        try {
            //Fetch tasks from database
            List<Image> imageList = imageRepository.findAll();

            // Map task list entity to task list DTO
           List<ImageDTO> imageDTOList = Utils.mapImageListEntityToImageListDTO(imageList);

            // Set response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setImageList(imageDTOList);
        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error getting all images" + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getImageByPatientId(Long patientId) {
        Response response = new Response();

        try {
            //Fetch tasks from database
            List<Image> imageList = imageRepository.findImagesByPatientId(patientId);
            // Map task list entity to task list DTO
            List<ImageDTO> imageDTOList = Utils.mapImageListEntityToImageListDTO(imageList);

            // Set response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setImageList(imageDTOList);
        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error getting image by patient id " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response getImageByStaffId(Long staffId) {
        Response response = new Response();

        try {
            //Fetch tasks from database
            List<Image> imageList = imageRepository.findImagesByStaffId(staffId);
            // Map task list entity to task list DTO
            List<ImageDTO> imageDTOList = Utils.mapImageListEntityToImageListDTO(imageList);

            // Set response
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setImageList(imageDTOList);
        } catch (OurException e) {

            // Handle custom exception
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {

            // Handle unexpected error
            response.setStatusCode(500);
            response.setMessage("Error getting image by staff id " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response deleteImageByImageId(Long imageId) {
        Response response = new Response();

        try {
            //Fetch tasks from database
            imageRepository.findById(imageId).orElseThrow(() -> new OurException("Image Not Found"));

            // Delete task
            imageRepository.deleteById(imageId);

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
    public Response updateImage(Long imageId, MultipartFile photo, String description, LocalDate uploadedDate) {
        Response response = new Response();

        try {
            String imageUrl = null;

            if(photo != null && !photo.isEmpty()){
                imageUrl = awsS3Service.saveImageToS3(photo);
            }

            Image image = imageRepository.findById(imageId).orElseThrow(()-> new OurException("Image Not Found"));
            if(description != null) image.setDescription(description);
            if(uploadedDate != null) image.setUploadDate(uploadedDate);

            Image updaedImage = imageRepository.save(image);
            ImageDTO imageDTO = Utils.mapImageEntityToImageDTO(updaedImage);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setImageDTO(imageDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating image " + e.getMessage());
        }

        return response;
    }
}
