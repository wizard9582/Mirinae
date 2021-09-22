package com.a506.mirinae.controller;

import com.a506.mirinae.domain.user.LoginReq;
import com.a506.mirinae.domain.user.LoginRes;
import com.a506.mirinae.domain.user.User;
import com.a506.mirinae.domain.user.UserRes;
import com.a506.mirinae.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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

    @GetMapping("/")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "회원 정보")
    public ResponseEntity<UserRes> getUserInfo(@ApiIgnore final Authentication authentication) {
        if(authentication==null || !authentication.isAuthenticated())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Long id = ((User)authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserInfo(id));
    }
}
