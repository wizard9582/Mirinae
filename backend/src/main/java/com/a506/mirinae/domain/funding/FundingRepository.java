package com.a506.mirinae.domain.funding;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FundingRepository extends JpaRepository<Funding, Long> {
    Page<Funding> findByCategory_NameAndFundingState(String name, FundingState fundingState, Pageable pageable);
    Page<Funding> findAllByFundingState(FundingState fundingState, Pageable pageable);
    List<Funding> findAllByIsEndedAndEndDatetimeBefore(Boolean isEnded, LocalDateTime currentDateTime);
}
