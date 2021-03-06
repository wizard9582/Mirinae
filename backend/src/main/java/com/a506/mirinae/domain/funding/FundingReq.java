package com.a506.mirinae.domain.funding;

import com.a506.mirinae.domain.category.Category;
import com.a506.mirinae.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FundingReq {
    String title;
    Long categoryId;
    String content;
    Double goal;
    String thumbnail;
    String image;
    LocalDateTime startDatetime;
    LocalDateTime endDatetime;

    public Funding toEntity(User user, Category category) {
        return Funding.builder()
                .user(user)
                .category(category)
                .title(title)
                .content(content)
                .thumbnail(thumbnail)
                .image(image)
                .startDatetime(startDatetime)
                .endDatetime(endDatetime)
                .goal(goal)
                .fundingState(FundingState.WAITING)
                .isEnded(false)
                .build();
    }
}
