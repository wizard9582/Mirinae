package com.a506.mirinae.controller;

import com.a506.mirinae.domain.funding.FundingRes;
import com.a506.mirinae.domain.user.User;
import com.a506.mirinae.service.AdminService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/funding")
    @ApiOperation(value = "승인되지 않은 펀딩 리스트")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    public ResponseEntity<List<FundingRes>> getNotAcceptedFundingList(@ApiIgnore final Authentication authentication, Pageable pageable) {
        if(authentication==null || !authentication.isAuthenticated())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Long id = ((User)authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.OK).body(adminService.getNotAcceptedFundingList(pageable, id));
    }
}
