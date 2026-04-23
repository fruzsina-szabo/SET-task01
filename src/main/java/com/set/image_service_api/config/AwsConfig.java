package com.set.image_service_api.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AwsConfig {
    @Value("${aws.s3.bucket-name}")
    private String s3BucketName;

    @Value("${aws.dynamodb.table-name}")
    private String dynamoDbTableName;

    public String getDynamoDbTableName() {
        return dynamoDbTableName;
    }

    public void setDynamoDbTableName(String dynamoDbTableName) {
        this.dynamoDbTableName = dynamoDbTableName;
    }

    public String getS3BucketName() {
        return s3BucketName;
    }

    public void setS3BucketName(String s3BucketName) {
        this.s3BucketName = s3BucketName;
    }
}
