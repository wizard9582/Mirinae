package com.a506.mirinae.domain.funding;

import java.time.LocalDateTime;

public interface FundingResInterface {
    Double getBalance();
    Long getFundingId();
    String getTitle();
    String getCategoryName();
    Double getGoal();
    String getThumbnail();
    LocalDateTime getStartDatetime();
    LocalDateTime getEndDatetime();
}
