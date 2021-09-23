package com.a506.mirinae.service;

import com.a506.mirinae.domain.category.Category;
import com.a506.mirinae.domain.category.CategoryRepository;
import com.a506.mirinae.domain.category.CategoryRes;
import com.a506.mirinae.domain.donation.Donation;
import com.a506.mirinae.domain.donation.DonationRepository;
import com.a506.mirinae.domain.donation.DonationReq;
import com.a506.mirinae.domain.funding.*;
import com.a506.mirinae.domain.user.User;
import com.a506.mirinae.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundingService {
    private final UserRepository userRepository;
    private final FundingRepository fundingRepository;
    private final DonationRepository donationRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public List<FundingRes> getFundingList(String categoryName, Pageable pageable) {
        List<Funding> funding;
        List<FundingRes> fundingResList = new ArrayList<>();
        if(categoryName.equals("all")) {
            funding = fundingRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize())).getContent();
        }
        else {
            funding = fundingRepository.findByCategory_Name(categoryName, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize())).getContent();
        }
        for(Funding f : funding) {
            fundingResList.add(new FundingRes(donationRepository.findDonationByFundingId(f.getId())));
        }
        return fundingResList;
    }

    @Transactional
    public FundingIdRes createFunding(FundingReq fundingReq, Long id) {
        String wallet = "null";
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 User가 없습니다. user ID=" + id));
        Category category = categoryRepository.findById(fundingReq.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다. 카테고리 ID=" + fundingReq.getCategoryId()));
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

    @Transactional
    public Boolean joinFunding(DonationReq donationReq, String JWT) {
        User user = User.builder().build();   //JWT로 user 변환
        Funding funding = fundingRepository.findById(donationReq.getFundingId())
                .orElseThrow(() -> new IllegalArgumentException("해당 펀딩이 없습니다. 펀딩 ID=" + donationReq.getFundingId()));
        String tx_id = "null"; //블록체인 구현 후 tx id 받기
        Donation donation = donationRepository.save(donationReq.toEntity(user, funding, tx_id));
        if(donation==null)
            return false;
        else
            return true;
    }

    @Transactional
    public Boolean checkFundingOwner(Long fundingId, String JWT) {
        User user = User.builder().build();   //JWT로 user 변환
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 펀딩이 없습니다. 펀딩 ID=" + fundingId));

        if(funding.getUser().getId() == user.getId())
            return true;
        else
            return false;
    }

    @Transactional
    public FundingDetailRes detailFunding(Long fundingId) {
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 펀딩이 없습니다. 펀딩 ID=" + fundingId));

        FundingRes fundingRes = new FundingRes(donationRepository.findDonationByFundingId(funding.getId()));

        return new FundingDetailRes(funding.getUser().getNickname(), fundingRes, funding.getCreatedDatetime(), funding.getStartDatetime(), funding.getEndDatetime());
    }

    @Transactional
    public Boolean deleteFunding(Long fundingId, String JWT) {
        if(!checkFundingOwner(fundingId, JWT))
            return false;
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 펀딩이 없습니다. 펀딩 ID=" + fundingId));
        try {
            fundingRepository.delete(funding);
        }
        catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
