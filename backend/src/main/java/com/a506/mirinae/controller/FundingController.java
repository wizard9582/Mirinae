package com.a506.mirinae.controller;

import com.a506.mirinae.domain.category.CategoryRes;
import com.a506.mirinae.domain.funding.FundingIdRes;
import com.a506.mirinae.domain.donation.DonationReq;
import com.a506.mirinae.domain.funding.FundingReq;
import com.a506.mirinae.domain.funding.FundingRes;
import com.a506.mirinae.service.FundingService;
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

    @ApiOperation(value = "펀딩 개설")
    @PostMapping("/")
    public ResponseEntity<FundingIdRes> createFunding(@RequestBody @ApiParam(value = "펀딩 이름, 카테고리 이름, 펀딩 설명, 목표금액, " +
                                                                         "타이틀 이미지, 설명 이미지, 시작일시, 종료일시") FundingReq fundingReq) {
        String JWT = "null"; // JWT 토큰
        FundingIdRes fundingIdRes = fundingService.createFunding(fundingReq, JWT);
        if(fundingIdRes.getFunding_id()==null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.status(HttpStatus.OK).body(fundingService.createFunding(fundingReq, JWT));
    }

    @ApiOperation(value = "펀딩 카테고리리스트")
    @GetMapping("/category")
    public ResponseEntity<List<CategoryRes>> getCategoryList() {
        return ResponseEntity.status(HttpStatus.OK).body(fundingService.getCategoryList());
    }

    @ApiOperation(value = "펀딩 참여")
    @PostMapping("/join")
    public ResponseEntity<String> joinFunding(@RequestBody @ApiParam(value = "펀딩 Id, 기부금액") DonationReq donationReq) {
        String JWT = "null"; // JWT 토큰
        if(fundingService.joinFunding(donationReq, JWT))
            return ResponseEntity.status(HttpStatus.OK).body("success");
        else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
