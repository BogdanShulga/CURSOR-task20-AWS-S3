package com.edu.s3aws.service;

import com.edu.s3aws.models.User;

import java.util.List;

public interface AwsS3Service {

    void putObject(User user);

    List<String> listObjects();

    List<User> getObject(String fileName);

    void deleteObject(String fileName);

    void migrate();
}
