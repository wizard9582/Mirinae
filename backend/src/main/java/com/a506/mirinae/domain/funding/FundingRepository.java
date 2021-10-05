package com.a506.mirinae.domain.funding;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundingRepository extends JpaRepository<Funding, Long> {
    Page<Funding> findByCategory_Name(String name, Pageable pageable);
}
