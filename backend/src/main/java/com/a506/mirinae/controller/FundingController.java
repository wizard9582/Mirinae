package com.a506.mirinae.controller;

import com.a506.mirinae.domain.funding.FundingRes;
import com.a506.mirinae.service.FundingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/funding")
public class FundingController {
    private final FundingService fundingService;

    @ApiOperation(value = "전체펀딩리스트")
    @GetMapping("/{category_id}")
    public ResponseEntity<List<FundingRes>> getFundingList(@PathVariable("category_id") String categoryName, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(fundingService.getFundingList(categoryName, pageable));
    }

}
