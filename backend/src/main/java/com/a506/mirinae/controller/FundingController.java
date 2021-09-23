package com.a506.mirinae.controller;

import com.a506.mirinae.domain.category.CategoryRes;
import com.a506.mirinae.domain.funding.FundingDetailRes;
import com.a506.mirinae.domain.funding.FundingIdRes;
import com.a506.mirinae.domain.donation.DonationReq;
import com.a506.mirinae.domain.funding.FundingReq;
import com.a506.mirinae.domain.funding.FundingRes;
import com.a506.mirinae.domain.user.User;
import com.a506.mirinae.service.FundingService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/funding")
public class FundingController {
    private final FundingService fundingService;

    @ApiOperation(value = "전체펀딩리스트")
    @GetMapping("/{categoryName}")
    public ResponseEntity<List<FundingRes>> getFundingList(@PathVariable("categoryName") String categoryName, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(fundingService.getFundingList(categoryName, pageable));
    }

    @ApiOperation(value = "펀딩 개설")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/")
    public ResponseEntity<FundingIdRes> createFunding(@RequestBody @ApiParam(value = "펀딩 이름, 카테고리 id, 펀딩 설명, 목표금액, " +
                                                                         "타이틀 이미지, 설명 이미지, 시작일시, 종료일시") FundingReq fundingReq,
                                                      @ApiIgnore final Authentication authentication) {
        if(authentication==null || !authentication.isAuthenticated())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Long id = ((User)authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.OK).body(fundingService.createFunding(fundingReq, id));
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

    @ApiOperation(value = "펀딩 작성자 본인 확인")
    @GetMapping("/owner/{fundingId}")
    public ResponseEntity<String> checkFundingOwner(@PathVariable("fundingId") Long fundingId) {
        String JWT = "null"; // JWT 토큰
        if(JWT == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        if(fundingService.checkFundingOwner(fundingId, JWT))
            return ResponseEntity.status(HttpStatus.OK).body("true");
        else
            return ResponseEntity.status(HttpStatus.OK).body("false");
    }

    @ApiOperation(value = "펀딩 상세")
    @GetMapping("/detail/{fundingId}")
    public ResponseEntity<FundingDetailRes> detailFunding(@PathVariable("fundingId") Long fundingId) {
        return ResponseEntity.status(HttpStatus.OK).body(fundingService.detailFunding(fundingId));
    }

    @ApiOperation(value = "펀딩 취소")
    @DeleteMapping("/{fundingId}")
    public ResponseEntity<String> deleteFunding(@PathVariable("fundingId") Long fundingId) {
        String JWT = "null"; // JWT 토큰
        if(JWT == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        if(fundingService.deleteFunding(fundingId, JWT))
            return ResponseEntity.status(HttpStatus.OK).body("true");
        else
            return ResponseEntity.status(HttpStatus.OK).body("false");
    }
}
