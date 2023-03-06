package com.example.s3practical_task.controller;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Sync {

    private final AmazonS3 s3;

    public void sync(String folderPath) {
        var bucketPath = "production-useast1-example";
        var fileNameFile = new HashMap<String, File>();
        sync(folderPath, fileNameFile);
        for (var entry : fileNameFile.entrySet()) {
            if (s3.doesObjectExist(bucketPath, entry.getKey())) {
                if (s3.getObject(bucketPath, entry.getKey()).getObjectMetadata().getContentLength() != entry.getValue().length()) {
                    s3.putObject(bucketPath, entry.getKey(), entry.getValue());
                }
            } else {
                s3.putObject(bucketPath, entry.getKey(), entry.getValue());
            }
        }
        s3.listObjects(bucketPath).getObjectSummaries().forEach(summary -> {
            if (fileNameFile.get(summary.getKey()) == null) {
                s3.deleteObject(bucketPath, summary.getKey());
            }
        });
    }

    public void sync(String folderPath, HashMap<String, File> fileNameFile) {
        String ignoredName = new File(folderPath).getName();
        sync(folderPath, "", ignoredName, fileNameFile);
    }

    public void sync(String folderPath, String folderPrefix, String folderInitName, HashMap<String, File> fileNameFile) {
        File folder = new File(folderPath);
        var files = folder.listFiles();

        if (files == null) {
            return;
        }

        String folderName = folder.getName();
        if (!Objects.equals(folderInitName, folderName)) {
            folderPrefix +="/" + folder.getName();
        }

        for (var file : files) {
            if (file.isFile()) {
                getLocalFileS3(file, folderPrefix, fileNameFile);
                continue;
            }
            sync(file.getAbsolutePath(), folderPrefix, folderInitName, fileNameFile);
        }
    }

    private void getLocalFileS3(File file, String folderPrefix, Map<String, File> localFileS3List) {
        var objectNameWithSlash = folderPrefix + "/" + file.getName();
        var objectName = objectNameWithSlash.substring(1);
        localFileS3List.put(objectName, file);
    }
}
