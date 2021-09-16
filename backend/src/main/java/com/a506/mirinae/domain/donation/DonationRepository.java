package com.a506.mirinae.domain.donation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query(value = "SELECT d.user.id AS userId, d.user.nickname AS userNickname, d.user.profileImage AS userProfileImage, SUM(amount) AS amount " +
                    "FROM Donation d " +
                    "WHERE d.funding.id = :fundingId " +
                    "GROUP BY d.user"
            , nativeQuery = false)
    Page<RankingResInterface> findRankingByFundingId(Long fundingId, Pageable pageable);

    @Query(value = "SELECT d.user.id AS userId, d.user.nickname AS userNickname, d.user.profileImage AS userProfileImage, SUM(amount) AS amount "+
                    "FROM Donation d " +
                    "WHERE d.funding.category.id = :categoryId " +
                    "GROUP BY d.user"
            , nativeQuery = false)
    Page<RankingResInterface> findRankingByCategoryId(Long categoryId, Pageable pageable);
}
