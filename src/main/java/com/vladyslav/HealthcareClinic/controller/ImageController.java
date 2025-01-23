package com.vladyslav.HealthcareClinic.controller;

import com.vladyslav.HealthcareClinic.dto.Response;
import com.vladyslav.HealthcareClinic.entity.Task;
import com.vladyslav.HealthcareClinic.service.interfac.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private IImageService imageService;

    @PostMapping("/add-image")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> addImage(
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "uploadedDate", required = false) LocalDate uploadedDate,
            @RequestParam(value = "staffId", required = false) Long staffId,
            @RequestParam(value = "patentId", required = false) Long patientId) {

        if(photo == null || photo.isEmpty() || description == null || description.isBlank() || uploadedDate == null || staffId == null || staffId.describeConstable().isEmpty() || patientId == null || patientId.describeConstable().isEmpty()){
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide values for all fields (photo, description, uploaded date, staff id and patient id");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }


        Response response = imageService.addNewImage(photo, description, uploadedDate, staffId, patientId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-image-by-id/{imageId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getImageById(@PathVariable Long imageId){
        Response response = imageService.getImageById(imageId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all-images")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllImages(){
        Response response = imageService.getAllImages();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-images-by-patient-id/{patientId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> getImagesByPatientId(@PathVariable Long patientId){
        Response response = imageService.getImageByPatientId(patientId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-images-by-staff-id/{staffId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> getImagesByStaffId(@PathVariable Long staffId){
        Response response = imageService.getImageByStaffId(staffId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("delete-image-by-id/{imageId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> deleteImageById(@PathVariable Long imageId){
        Response response = imageService.deleteImageByImageId(imageId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("update-image-by-id/{imageId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STAFF')")
    public ResponseEntity<Response> updateImage(@PathVariable Long imageId,
                                                @RequestParam(value = "photo", required = false) MultipartFile photo,
                                                @RequestParam(value = "description", required = false) String description,
                                                @RequestParam(value = "uploadedDate", required = false) LocalDate uploadedDate){


        Response response = imageService.updateImage(imageId, photo, description, uploadedDate);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }




}
