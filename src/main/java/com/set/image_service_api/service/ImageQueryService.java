package com.set.image_service_api.service;

import com.set.image_service_api.dto.ImageDto;
import com.set.image_service_api.dto.ImageStatus;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ImageQueryService {

    private final DynamoDbClient dynamoDbClient;
    private final String dynamoDbTableName;
    private final String s3BucketName;

    public ImageQueryService(
        @Value("${aws.region}") String awsRegion,
        @Value("${aws.dynamodb.table-name}") String dynamoDbTableName,
        @Value("${aws.s3.bucket-name}") String s3BucketName
    ) {
        this.dynamoDbClient = DynamoDbClient.builder()
            .region(Region.of(awsRegion))
            .build();
        this.dynamoDbTableName = dynamoDbTableName;
        this.s3BucketName = s3BucketName;
    }

    public List<ImageDto> getByLabel(String label) {
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Label is required");
        }

        QueryRequest request = QueryRequest.builder()
            .tableName(dynamoDbTableName)
            .keyConditionExpression("LabelValue = :label")
            .expressionAttributeValues(Map.of(
                ":label", AttributeValue.builder().s(label.trim()).build()
            ))
            .build();

        QueryResponse response = dynamoDbClient.query(request);

        List<ImageDto> images = new ArrayList<>();
        for (Map<String, AttributeValue> item : response.items()) {
            images.add(mapToImageDto(item));
        }

        return images;
    }

    private ImageDto mapToImageDto(Map<String, AttributeValue> item) {
        String labelValue = getString(item, "LabelValue");
        String imageName = getString(item, "ImageName");

        ImageDto image = new ImageDto();
        image.setId(toSafeId(imageName));
        image.setObject_path(imageName == null ? null : "s3://" + s3BucketName + "/" + imageName);
        image.setObject_size(null);
        image.setTime_added(null);
        image.setTime_updated(null);
        image.setLables(labelValue == null ? List.of() : List.of(labelValue));
        image.setStatus(ImageStatus.RECOGNITION_COMPLETED);

        return image;
    }

    private String getString(Map<String, AttributeValue> item, String key) {
        AttributeValue value = item.get(key);
        return value != null ? value.s() : null;
    }

    private Integer toSafeId(String value) {
        if (value == null) {
            return 0;
        }
        int hash = value.hashCode();
        return hash == Integer.MIN_VALUE ? 0 : Math.abs(hash);
    }

    @PreDestroy
    public void close() {
        dynamoDbClient.close();
    }
}
