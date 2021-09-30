package com.a506.mirinae.controller;

import com.a506.mirinae.util.Uploader;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class UploadController {

    private final Uploader uploader;
    private final Long FILE_LIMIT_SIZE = (long)1e7;

    @PostMapping( value = "/{usage}", produces="application/json;charset=UTF-8")
    @ApiOperation(value = "이미지 등록", notes = "프로필 이미지를 S3원격 서버에 저장한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "success"),
            @ApiResponse(code = 400, message = "파일용량 초과 / 확장자 오류"),
            @ApiResponse(code = 401, message = "인증실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<String> uploadProfileImg(@ApiParam(value = "이미지 쓰임새 : [user, fundingInfo, fundingThumbnail]") @PathVariable("usage") String usage,
                                                   @RequestParam("file") MultipartFile file) throws IOException {
        if(file.getSize() > FILE_LIMIT_SIZE){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일용량 초과");
        }
        try {
            if(usage.equals("fundingInfo"))
                usage = "funding/info-image";
            else if(usage.equals("fundingThumbnail"))
                usage = "funding/thumbnail";
            return ResponseEntity.status(HttpStatus.CREATED).body(uploader.uploadS3Instance(file, "static/" + usage));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}