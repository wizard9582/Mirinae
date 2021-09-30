package com.a506.mirinae.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface Uploader {
    String uploadS3Instance(MultipartFile multipartFile, String dirName) throws IOException;
    void deleteS3Instance(String fileName);
}
