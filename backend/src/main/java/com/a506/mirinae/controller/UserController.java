package com.a506.mirinae.controller;

import com.a506.mirinae.domain.user.LoginReq;
import com.a506.mirinae.domain.user.LoginRes;
import com.a506.mirinae.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "소셜 로그인", notes = "구글/카카오/네이버 아이디를 통해 로그인한다.")
    public ResponseEntity<LoginRes> login(@RequestBody @ApiParam(value = "소셜로그인 정보(이메일, OauthType, nickname)") LoginReq loginReq){
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginReq));
    }

}
