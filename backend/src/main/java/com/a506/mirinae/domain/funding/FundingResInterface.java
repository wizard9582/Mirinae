package com.a506.mirinae.domain.funding;

import java.time.LocalDateTime;

public interface FundingResInterface {
    Long getBalance();
    Long getFundingId();
    String getTitle();
    String getCategoryName();
    Long getGoal();
    String getThumbnail();
    LocalDateTime getStartDatetime();
    LocalDateTime getEndDatetime();
}
