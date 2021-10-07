// 2021.09.13 HanQ 신한규
package com.a506.mirinae.domain.funding;

import com.a506.mirinae.domain.category.Category;
import com.a506.mirinae.domain.donation.Donation;
import com.a506.mirinae.domain.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.web3j.abi.datatypes.Bool;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Funding {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String wallet;

    private String thumbnail;

    private String image;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDatetime;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDatetime;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDatetime;

    @NotNull
    private Double goal;

    @Column(length = 32, columnDefinition = "varchar(32) default 'WAITING'")
    @Enumerated(EnumType.STRING)
    private FundingState fundingState = FundingState.WAITING;

    @NotNull
    private Boolean isEnded;

    @OneToMany(mappedBy = "funding", cascade = CascadeType.ALL)
    private List<Donation> donations = new ArrayList<>();

    @Builder
    public Funding(Long id, User user, Category category, String title, String content, String wallet, String thumbnail, String image,
                   LocalDateTime createdDatetime, LocalDateTime startDatetime, LocalDateTime endDatetime, Double goal, Boolean isEnded, FundingState fundingState) {
        this.id = id;
        this.user = user;
        this.category = category;
        this.title = title;
        this.content = content;
        this.wallet = wallet;
        this.thumbnail = thumbnail;
        this.image = image;
        this.createdDatetime = createdDatetime;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.goal = goal;
        this.isEnded = isEnded;
        this.fundingState = fundingState;
    }

    public void updateFundingState(FundingState fundingState) {
        this.fundingState = fundingState;
    }

    public void endFunding() {
        this.isEnded = true;
    }

    public void deleteDonation() {
        this.donations = new ArrayList<>();
    }
}
