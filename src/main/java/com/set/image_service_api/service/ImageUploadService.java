package com.set.image_service_api.service;

import com.set.image_service_api.dto.ImageDto;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class ImageUploadService {

    private final RestClient imageBackendRestClient;

    public ImageUploadService(RestClient imageBackendRestClient) {
        this.imageBackendRestClient = imageBackendRestClient;
    }

    public ImageDto upload(MultipartFile file, List<String> labels) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file is required");
        }

        try {
            MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();

            bodyBuilder.part("file", new ByteArrayResource(file.getBytes()) {
                    @Override
                    public String getFilename() {
                        return Objects.requireNonNullElse(file.getOriginalFilename(), "image.bin");
                    }
                })
                .contentType(file.getContentType() != null
                    ? MediaType.parseMediaType(file.getContentType())
                    : MediaType.APPLICATION_OCTET_STREAM);

            if (labels != null) {
                for (String label : labels) {
                    if (label != null && !label.isBlank()) {
                        bodyBuilder.part("labels", label.trim());
                    }
                }
            }

            return imageBackendRestClient.post()
                .uri("/images")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(bodyBuilder.build())
                .retrieve()
                .body(ImageDto.class);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to read uploaded image", ex);
        }
    }
}
