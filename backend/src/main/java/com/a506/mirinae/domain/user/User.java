// 2021.09.13 HanQ 신한규
package com.a506.mirinae.domain.user;

import com.a506.mirinae.domain.donation.Donation;
import com.a506.mirinae.domain.funding.Funding;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String email;

    private String nickname;

    private String profileImage;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDatetime;

    @NotNull
    private String wallet;

    @NotNull
    private String sign;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Funding> fundings = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Donation> donations = new ArrayList<>();

    @Builder
    public User(long id, String email, String nickname, String profileImage, LocalDateTime createdDatetime, String wallet, String sign) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.createdDatetime = createdDatetime;
        this.wallet = wallet;
        this.sign = sign;
    }
}
