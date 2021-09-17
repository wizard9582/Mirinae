// 2021.09.13 HanQ 신한규
package com.a506.mirinae.domain.donation;

import com.a506.mirinae.domain.funding.Funding;
import com.a506.mirinae.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Donation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_id")
    private Funding funding;

    @NotNull
    private Long amount;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDatetime;

    @NotNull
    private String txId;

    @Builder
    public Donation(Long id, User user, Funding funding, Long amount, LocalDateTime createdDatetime, String txId) {
        this.id = id;
        this.user = user;
        this.funding = funding;
        this.amount = amount;
        this.createdDatetime = createdDatetime;
        this.txId = txId;
    }
}
