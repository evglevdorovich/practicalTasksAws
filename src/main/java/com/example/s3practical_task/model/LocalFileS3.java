package com.example.s3practical_task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocalFileS3 {
    String objectName;
    File file;


}
