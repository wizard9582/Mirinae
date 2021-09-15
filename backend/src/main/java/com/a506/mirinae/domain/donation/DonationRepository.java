package com.a506.mirinae.domain.donation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    Page<Donation> findAllByFundingId(Long funding_id, Pageable pageable);
}
