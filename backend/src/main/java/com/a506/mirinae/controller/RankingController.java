package com.a506.mirinae.controller;

import com.a506.mirinae.domain.donation.FundingRankingRes;
import com.a506.mirinae.service.RankingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Ranking API")
@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/funding/{funding_id}")
    @ApiOperation(value = "펀딩 내 참여자 랭킹")
    public ResponseEntity<?> getFundingRanking(@PathVariable Long funding_id){
        return ResponseEntity.status(HttpStatus.OK).body(rankingService.getFundingRanking(funding_id));
    }
}
