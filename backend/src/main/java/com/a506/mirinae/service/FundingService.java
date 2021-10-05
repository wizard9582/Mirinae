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

import java.time.LocalDateTime;
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
            if(f.getFundingState().equals(FundingState.ACCEPTED))
                if(f.getDonations().size()==0)
                    fundingResList.add(new FundingRes(f));
                else {
                    fundingResList.add(new FundingRes(donationRepository.findDonationByFundingId(f.getId())));
                }
        }
        return fundingResList;
    }

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
    public void joinFunding(DonationReq donationReq, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 User가 없습니다. user ID=" + id));
        Funding funding = fundingRepository.findById(donationReq.getFundingId())
                .orElseThrow(() -> new IllegalArgumentException("해당 펀딩이 없습니다. 펀딩 ID=" + donationReq.getFundingId()));
        if(funding.getStartDatetime().isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("해당 펀딩은 아직 시작되지 않았습니다!");
        if(funding.getEndDatetime().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("해당 펀딩은 이미 종료되었습니다!");
        if(!funding.getFundingState().equals(FundingState.ACCEPTED))
            throw new IllegalArgumentException("해당 펀딩은 승인되지 않았습니다!");

        donationReq.getKey();   // 지갑 비밀 키
        
        String tx_id = "null"; //블록체인 구현 후 tx id 받기
        donationRepository.save(donationReq.toEntity(user, funding, tx_id));
    }

    @Transactional
    public Boolean checkFundingOwner(Long fundingId, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 User가 없습니다. user ID=" + id));
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 펀딩이 없습니다. 펀딩 ID=" + fundingId));

        return funding.getUser().getId() == user.getId();
    }

    @Transactional
    public FundingDetailRes detailFunding(Long fundingId) {
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 펀딩이 없습니다. 펀딩 ID=" + fundingId));

        FundingRes fundingRes = new FundingRes(donationRepository.findDonationByFundingId(funding.getId()));

        return new FundingDetailRes(funding.getUser().getNickname(), fundingRes, funding.getCreatedDatetime(), funding.getStartDatetime(), funding.getEndDatetime(), funding.getFundingState());
    }

    @Transactional
    public void deleteFunding(Long fundingId, Long id) {
        if(!checkFundingOwner(fundingId, id))
            throw new IllegalArgumentException("삭제 권한이 없습니다!");
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(() -> new IllegalArgumentException("해당 펀딩이 없습니다. 펀딩 ID=" + fundingId));

        if(funding.getEndDatetime().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("종료된 펀딩은 삭제할 수 없습니다!");
        fundingRepository.delete(funding);
    }
}
