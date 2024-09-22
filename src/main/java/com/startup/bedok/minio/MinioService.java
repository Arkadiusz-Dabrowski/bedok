package com.startup.bedok.minio;

import com.startup.bedok.exceptions.PhotoServiceException;
import com.startup.bedok.global.SimpleResponse;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class MinioService {

    private final MinioClient minioClient;
    private final String bucketName = "bedok";

    public SimpleResponse addPhotoToMinio(String objectName, MultipartFile file) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                                    file.getInputStream(), file.getInputStream().available(), -1)
                            .contentType(file.getContentType())
                            .build());
        } catch (Exception e) {
            throw new PhotoServiceException(e.getMessage());
        }
        return new SimpleResponse("photos added sucesfully");

    }

    public SimpleResponse deletePhotoFromMinio(String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build()
            );
        } catch (Exception e) {
            throw new PhotoServiceException(e.getMessage());
        }
        return new SimpleResponse("photos deleted sucesfully");

    }

    public String getUrlOfPhoto(String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(1, TimeUnit.HOURS) // URL expiration time
                            .build()
            );
        } catch (Exception e) {
            throw new PhotoServiceException(e.getMessage());
        }
    }

    public Iterable<Result<Item>> getObjectsFromLocation(String location) {
        try {
            return minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucketName).prefix(location).recursive(true).build());
        } catch (Exception e) {
            throw new PhotoServiceException(e.getMessage());
        }
    }

    public void deleteObjectsFromLocation(String location) {
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucketName).prefix(location).recursive(true).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                deletePhotoFromMinio(item.objectName());
            }
        } catch (Exception e) {
            throw new PhotoServiceException(e.getMessage());
        }
    }


    public List<String> getListOfUrls(String location) {
        List<String> urls = new ArrayList<>();
        try {

            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucketName).prefix(location).recursive(true).build());

            for (Result<Item> result : results) {
                Item item = result.get();
                if (!item.isDir()) {
                    String objectName = item.objectName();
                    String url = minioClient.getPresignedObjectUrl(
                            GetPresignedObjectUrlArgs.builder()
                                    .method(Method.GET)
                                    .bucket(bucketName)
                                    .object(objectName)
                                    .expiry(1, TimeUnit.HOURS) // URL expiration time
                                    .build()
                    );
                    urls.add(url);
                }
            }
        } catch (MinioException e) {
            throw new PhotoServiceException(e.getMessage());
        } catch (Exception e) {
            throw new PhotoServiceException(e.getMessage());
        }
        return urls;
    }
}
