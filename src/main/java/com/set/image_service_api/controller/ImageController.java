package com.set.image_service_api.controller;

import java.util.List;

import com.set.image_service_api.dto.ImageDto;
import com.set.image_service_api.service.ImageQueryService;
import com.set.image_service_api.service.ImageUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageUploadService imageUploadService;
    private final ImageQueryService imageQueryService;

    public ImageController(
        ImageUploadService imageUploadService,
        ImageQueryService imageQueryService
    ) {
        this.imageUploadService = imageUploadService;
        this.imageQueryService = imageQueryService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageDto> uploadImage(
        @RequestPart("file") MultipartFile file,
        @RequestParam(value = "labels", required = false) List<String> labels
    ) {
        ImageDto savedImage = imageUploadService.upload(file, labels);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
    }

    @GetMapping
    public ResponseEntity<List<ImageDto>> getImagesByLabel(
        @RequestParam("label") String label
    ) {
        return ResponseEntity.ok(imageQueryService.getByLabel(label));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
