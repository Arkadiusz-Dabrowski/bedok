package com.startup.bedok.advertisment.controller;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("files")
@RequiredArgsConstructor
public class TestController {

    private final MinioClient minioClient;


    @PostMapping("/")
    public String testMinio(@RequestParam MultipartFile file) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException{
            minioClient.putObject(
                    PutObjectArgs.builder().bucket("bdok").object("user/arek/" + file.getOriginalFilename()).stream(
                                    file.getInputStream(), file.getInputStream().available(), -1)
                            .contentType(file.getContentType())
                            .build());

        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket("bdok")
                        .object(file.getName())
                        .expiry(24, TimeUnit.HOURS) // URL expiration time
                        .build()
        );
    }

}
