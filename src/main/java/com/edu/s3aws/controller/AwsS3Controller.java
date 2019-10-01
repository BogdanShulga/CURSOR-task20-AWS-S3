package com.edu.s3aws.controller;

import com.edu.s3aws.models.User;
import com.edu.s3aws.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aws/s3")
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;


    @PostMapping
    public void putObject(@RequestBody User user) {

        awsS3Service.putObject(user);
    }

    @GetMapping("/all")
    public List<String> listObjects() {

        return awsS3Service.listObjects();
    }

    @GetMapping("/")
    public List<User> getObject(@RequestParam String fileName) {

        return awsS3Service.getObject(fileName);
    }

    @DeleteMapping
    public void deleteObject(@RequestParam String fileName) {

        awsS3Service.deleteObject(fileName);
    }

    @GetMapping("/migrate")
    public void migrate() {

        awsS3Service.migrate();
    }
}
