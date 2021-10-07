package com.a506.mirinae.config;

import com.a506.mirinae.service.FundingService;
import com.a506.mirinae.util.FundingEndTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfig {
    private final JobBuilderFactory jobBuilderFactory; // Job 빌더 생성용
    private final StepBuilderFactory stepBuilderFactory; // Step 빌더 생성용
    private final FundingService fundingService;

    // JobBuilderFactory를 통해서 Job을 생성
    @Bean
    public Job smartContractJob() {
        return jobBuilderFactory.get("smartContractJob")
                .start(smartContractStep())  // Step 설정
                .build();
    }

    // StepBuilderFactory를 통해서 Step을 생성
    @Bean
    public Step smartContractStep() {
        return stepBuilderFactory.get("smartContractStep")
                .tasklet(new FundingEndTasklet(fundingService)) // Tasklet 설정
                .build();
    }
}
