// 2021.09.13 HanQ 신한규
package com.a506.mirinae.domain.user;

import com.a506.mirinae.domain.donation.Donation;
import com.a506.mirinae.domain.funding.Funding;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String nickname;

    private String profileImage;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDatetime;

    private String wallet;

    private String sign;

    @NotNull
    @Column(columnDefinition = "boolean default false")
    private Boolean isAdmin;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OauthType oauthType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Funding> fundings = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Donation> donations = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Builder
    public User(long id, String email, String nickname, String profileImage,
                LocalDateTime createdDatetime, String wallet, String sign, Boolean isAdmin, OauthType oauthType) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.createdDatetime = createdDatetime;
        this.wallet = wallet;
        this.sign = sign;
        this.isAdmin = isAdmin;
        this.oauthType = oauthType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.id.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void updateUser(UpdateReq updateReq) {
        this.nickname = updateReq.getNickname();
        this.profileImage = updateReq.getImage();
    }

    public void updateWallet(WalletReq walletReq) {
        this.wallet = walletReq.getWalletAddress();
    }
}
