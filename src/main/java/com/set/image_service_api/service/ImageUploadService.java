package com.set.image_service_api.service;

import com.set.image_service_api.dto.ImageDto;
import com.set.image_service_api.dto.ImageStatus;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageUploadService {

    private final S3Client s3Client;
    private final String s3BucketName;

    public ImageUploadService(
        @Value("${aws.region}") String awsRegion,
        @Value("${aws.s3.bucket-name}") String s3BucketName
    ) {
        this.s3Client = S3Client.builder()
            .region(Region.of(awsRegion))
            .build();
        this.s3BucketName = s3BucketName;
    }

    public ImageDto upload(MultipartFile file, List<String> labels) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file is required");
        }

        String objectKey = buildObjectKey(file.getOriginalFilename());

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                .bucket(s3BucketName)
                .key(objectKey)
                .contentType(file.getContentType())
                .build();

            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to read uploaded image", ex);
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to upload image to S3", ex);
        }

        Instant now = Instant.now();

        ImageDto image = new ImageDto();
        image.setId(toSafeId(objectKey));
        image.setObject_path("s3://" + s3BucketName + "/" + objectKey);
        image.setObject_size(String.valueOf(file.getSize()));
        image.setTime_added(now);
        image.setTime_updated(now);
        image.setLables(labels == null ? List.of() : labels);
        image.setStatus(ImageStatus.NEW);

        return image;
    }

    private String buildObjectKey(String originalFilename) {
        String cleanFileName = StringUtils.cleanPath(
            Objects.requireNonNullElse(originalFilename, "image.bin")
        );

        if (cleanFileName.isBlank()) {
            cleanFileName = "image.bin";
        }

        return "uploads/" + UUID.randomUUID() + "-" + cleanFileName;
    }

    private Integer toSafeId(String value) {
        int hash = value.hashCode();
        return hash == Integer.MIN_VALUE ? 0 : Math.abs(hash);
    }

    @PreDestroy
    public void close() {
        s3Client.close();
    }
}
