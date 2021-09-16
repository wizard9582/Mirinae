package com.a506.mirinae.service;

import com.a506.mirinae.domain.category.Category;
import com.a506.mirinae.domain.category.CategoryRepository;
import com.a506.mirinae.domain.category.CategoryRes;
import com.a506.mirinae.domain.donation.Donation;
import com.a506.mirinae.domain.donation.DonationRepository;
import com.a506.mirinae.domain.funding.*;
import com.a506.mirinae.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundingService {
    private final FundingRepository fundingRepository;
    private final DonationRepository donationRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public List<FundingRes> getFundingList(String categoryName, Pageable pageable) {
        List<Funding> funding;
        List<FundingRes> fundingResList = new ArrayList<>();
        if(categoryName.equals("all")) {
            funding = fundingRepository.findAll(pageable).getContent();
        }
        else {
            funding = fundingRepository.findByCategory_Name(categoryName, pageable).getContent();
        }
        for(Funding f : funding) {
            Long balance = 0l;
            for (Donation d : f.getDonations()) {
                balance += d.getAmount();
            }
            FundingRes fundingRes = new FundingRes(f, balance);
            fundingResList.add(fundingRes);
        }
        return fundingResList;
    }

    @Transactional
    public FundingIdRes createFunding(FundingReq fundingReq, String JWT) {
        String wallet = "null";
        User user = null;   //JWT로 user 변환해주기
        Category category = categoryRepository.findByName(fundingReq.getCategory_name())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다. 카테고리=" + fundingReq.getCategory_name()));
        Funding funding = fundingRepository.save(fundingReq.toEntity(user, wallet, category));
        return new FundingIdRes(funding.getId());
    }

    @Transactional
    public List<CategoryRes> getCategoryList() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryRes> categoryResList = new ArrayList<>();
        for(Category c : categoryList) {
            categoryResList.add(new CategoryRes(c));
        }
        return categoryResList;
    }
}
