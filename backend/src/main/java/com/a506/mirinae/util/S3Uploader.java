package com.a506.mirinae.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader implements Uploader{

    private final static String TEMP_FILE_PATH = "classpath:";
    private final AmazonS3Client amazonS3Client;
    private final List<String> imageExt = Arrays.asList(".PNG", ".png", ".JPEG", ".jpeg", ".JPG", ".jpg", ".TIFF", ".tiff", ".BMP", ".bmp" , ".GIF", ".gif");

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private String s3Url = "https://mirinae.s3.ap-northeast-2.amazonaws.com/";
    @Override
    public String uploadS3Instance(MultipartFile multipartFile, String dirName) throws IOException {
        File convertedFile = convert(multipartFile);
        if(!imageExt.contains(getExt(convertedFile.getName()))) {
            removeNewFile(convertedFile);
            throw new UnsupportedOperationException("지원하지않는 확장자 입니다.");
        }
        return upload(convertedFile, dirName);
    }

    @Override
    public void deleteS3Instance(String url) {
        log.info("file url : " + url);
        if(url.indexOf(s3Url) == -1)
            return ;
        String fileName = url.replaceFirst(s3Url, "");
        amazonS3Client.deleteObject(bucket, fileName);
        log.info(fileName + " 삭제완료");
    }

    private String upload(File uploadFile, String dirName){

        String fileName = dirName + makeFileName(uploadFile.getName());
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }
    private String putS3(File uploadFile, String fileName){
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
    private void removeNewFile(File targetFile){
        if(targetFile.delete()) return ;
        log.info("임시 파일이 삭제 되지 못했습니다. 파일이름 {}", targetFile.getName());
    }

    private File convert(MultipartFile file) throws IOException{
        File convertFile = new File(TEMP_FILE_PATH + file.getOriginalFilename());

        if(convertFile.createNewFile()){
            try(FileOutputStream fos = new FileOutputStream(convertFile)){
                fos.write(file.getBytes());
            }
            return convertFile;
        }
        throw new IllegalArgumentException("파일 변환에 실패 했습니다. 파일이름 : " + file.getName());
    }

    private String makeFileName(String originFileName){
        UUID uuid = UUID.randomUUID();
        String date = (Date.valueOf(LocalDate.now())).toString();
        final String ext = getExt(originFileName);
        return ("/mirinae_" + date + "_" + uuid + ext) ;//+ "_" + uploadFile.getName();
    }

    private String getExt(String originFileName){
        return originFileName.substring(originFileName.lastIndexOf('.'));
    }
}
