package com.a506.mirinae.util;

import com.a506.mirinae.service.FundingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDateTime;

@Slf4j
@AllArgsConstructor
public class FundingEndTasklet implements Tasklet {
    private FundingService fundingService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        fundingService.fundingEnd();
        log.info("tasklet 실행됨" + LocalDateTime.now());
        return RepeatStatus.FINISHED;
    }
}
