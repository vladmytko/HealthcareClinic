package com.vladyslav.HealthcareClinic.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.vladyslav.HealthcareClinic.exception.OurException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Service
public class AwsS3Service {
    private final String bucketName = "mutko95-hotel-images";

    @Value("${aws.s3.access.key}")
    private String awsS3AccessKey;

    @Value("${aws.s3.secret.key}")
    private String awsS3SecretKey;

    // Allowed file extensions for security
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp", "webp");

    public String saveImageToS3(MultipartFile photo) {

        try {
            // Validate file type (must be an image)
            // Prevents malicious uploads like .exe, .bat, or disguised files (e.g., .jpg that is actually a script)
            String contentType = photo.getContentType();
            if(contentType == null || !contentType.startsWith("image/")) {
                throw new OurException("Invalid file type. Only images are allowed");
            }

            // Validate file extension
            // Prevents users from renaming a harmful file (e.g., malware.exe -> malware.jpg
            String originalFilename = photo.getOriginalFilename();
            if(originalFilename == null || !isAllowedExtension(originalFilename)) {
                throw new OurException("Invalid file extension. Allowed: " + ALL