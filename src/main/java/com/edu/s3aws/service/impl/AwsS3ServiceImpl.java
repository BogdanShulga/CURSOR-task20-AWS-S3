package com.edu.s3aws.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.edu.s3aws.models.User;
import com.edu.s3aws.service.AwsS3Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AwsS3ServiceImpl implements AwsS3Service {

    private final ObjectMapper objectMapper;

    @Value("${aws.bucket.name}")
    private String awsBucketName;

    @Value("${json.files.path}")
    private String path;

    @Value("${json.migrate.file.name}")
    private String migrateFile;

    private final AmazonS3 amazonS3;

    @Override
    public void putObject(User user) {

        String userJson = "default";

        try {
            userJson = objectMapper.writeValueAsString(List.of(user));
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
        }

        String fileName = user.getName() + "-" + user.getSurname() + ".json";

        amazonS3.putObject(
                awsBucketName,
                fileName,
                userJson
        );
    }

    @Override
    public List<String> listObjects() {

        List<S3ObjectSummary> objectSummaries = amazonS3.listObjects(awsBucketName).getObjectSummaries();

        return objectSummaries.stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getObject(String fileName) {

        S3Object amazonS3Object = amazonS3.getObject(awsBucketName, fileName);

        S3ObjectInputStream objectContent = amazonS3Object.getObjectContent();

        List<User> users = new ArrayList<>();
        try {
            users = objectMapper.readValue(objectContent.getDelegateStream(), new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return users;
    }

    @Override
    public void deleteObject(String fileName) {

        try {
            amazonS3.deleteObject(awsBucketName, fileName);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
    }

    @Override
    public void migrate() {

        amazonS3.putObject(
                awsBucketName,
                migrateFile,
                new File(path + migrateFile)
        );
    }
}
