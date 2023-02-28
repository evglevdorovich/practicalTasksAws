package com.example.s3practical_task.controller;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequiredArgsConstructor
public class S3Controller {

    private final AmazonS3 s3;
    private final AWSCredentials awsCredentialsProvider;
//2nd task
    @ResponseBody
    @GetMapping("/presignedUrl")
    public String getPresignedUrl() {
        var bucketName = "private-presigned-url-example";
        var objectKey = "download.jpg";

        var expiration = new Date();
        var expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectKey, HttpMethod.GET);
        generatePresignedUrlRequest.setExpiration(expiration);
        generatePresignedUrlRequest.setResponseHeaders(new ResponseHeaderOverrides().withContentDisposition("attachment"));
        var presignedUrl = s3.generatePresignedUrl(generatePresignedUrlRequest);
        System.out.println("Presigned URL with Content-Disposition: " + presignedUrl.toString());
        return presignedUrl.toString();
    }

    @GetMapping("/")
    public String uploadAnObjectPage() {

        return "upload";
    }


//3rd task
    @ResponseBody
    @PutMapping("/putObject")
    public String putUpload(@RequestBody String fileName) {
        var bucketName = "private-presigned-url-example";
        System.out.println(fileName);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName, HttpMethod.PUT);
        var presignedUrl = s3.generatePresignedUrl(generatePresignedUrlRequest);
        System.out.println("Presigned URL with Content-Disposition: " + presignedUrl.toString());
        return presignedUrl.toString();
    }


}
