package com.a506.mirinae.domain.funding;

import com.a506.mirinae.domain.category.Category;
import com.a506.mirinae.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FundingReq {
    String title;
    String category_name;
    String content;
    Long goal;
    String thumbnail;
    String image;
    LocalDateTime startDatetime;
    LocalDateTime endDatetime;

    public Funding toEntity(User user, String wallet, Category category) {
        return Funding.builder()
                .user(user)
                .category(category)
                .title(title)
                .content(content)
                .wallet(wallet)
                .thumbnail(thumbnail)
                .image(image)
                .startDatetime(startDatetime)
                .endDatetime(endDatetime)
                .goal(goal)
                .build();
    }
}
