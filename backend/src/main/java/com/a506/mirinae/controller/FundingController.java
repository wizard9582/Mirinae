package com.a506.mirinae.controller;

import com.a506.mirinae.domain.category.CategoryRes;
import com.a506.mirinae.domain.funding.*;
import com.a506.mirinae.domain.donation.DonationReq;
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
    public ResponseEntity<FundingSizeRes> getFundingList(@PathVariable("categoryName") String categoryName, final Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(fundingService.getFundingList(categoryName, pageable));
    }

    @ApiOperation(value = "펀딩 개설")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/")
    public ResponseEntity<FundingIdRes> createFunding(@ApiIgnore final Authentication authentication,
                                                      @RequestBody @ApiParam(value = "펀딩 이름, 카테고리 id, 펀딩 설명, 목표금액, " +
                                                              "타이틀 이미지, 설명 이미지, 시작일시, 종료일시") FundingReq fundingReq) {
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
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/join")
    public ResponseEntity<String> joinFunding(@ApiIgnore final Authentication authentication,
                                              @RequestBody @ApiParam(value = "펀딩 Id, 기부금액, private key") DonationReq donationReq) {
        if(authentication==null || !authentication.isAuthenticated())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Long id = ((User)authentication.getPrincipal()).getId();
        fundingService.joinFunding(donationReq, id);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @ApiOperation(value = "펀딩 작성자 본인 확인")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/owner/{fundingId}")
    public ResponseEntity<String> checkFundingOwner(@ApiIgnore final Authentication authentication,
                                                    @PathVariable("fundingId") Long fundingId){
        if(authentication==null || !authentication.isAuthenticated())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Expired");
        Long id = ((User)authentication.getPrincipal()).getId();
        if(fundingService.checkFundingOwner(fundingId, id))
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Denied");
    }

    @ApiOperation(value = "펀딩 상세")
    @GetMapping("/detail/{fundingId}")
    public ResponseEntity<FundingDetailRes> detailFunding(@PathVariable("fundingId") Long fundingId) {
        return ResponseEntity.status(HttpStatus.OK).body(fundingService.detailFunding(fundingId));
    }

    @ApiOperation(value = "펀딩 취소")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @DeleteMapping("/{fundingId}")
    public ResponseEntity<String> deleteFunding(@ApiIgnore final Authentication authentication,
                                                @PathVariable("fundingId") Long fundingId) {
        if(authentication==null || !authentication.isAuthenticated())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Long id = ((User)authentication.getPrincipal()).getId();
        fundingService.deleteFunding(fundingId, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("success");
    }
}
