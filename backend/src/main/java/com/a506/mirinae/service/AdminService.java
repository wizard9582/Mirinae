package com.a506.mirinae.service;

import com.a506.mirinae.domain.category.CategoryRepository;
import com.a506.mirinae.domain.donation.DonationRepository;
import com.a506.mirinae.domain.funding.Funding;
import com.a506.mirinae.domain.funding.FundingRepository;
import com.a506.mirinae.domain.funding.FundingRes;
import com.a506.mirinae.domain.funding.FundingState;
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
public class AdminService {
    private final UserRepository userRepository;
    private final FundingRepository fundingRepository;
    private final DonationRepository donationRepository;

    @Transactional
    public List<FundingRes> getNotAcceptedFundingList(Pageable pageable, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 User가 없습니다. user ID=" + id));
        if(!user.getIsAdmin())
            throw new IllegalArgumentException("관리자 계정이 아닙니다!");
        List<Funding> funding;
        List<FundingRes> fundingResList = new ArrayList<>();
        funding = fundingRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize())).getContent();
        for(Funding f : funding) {
            if(f.getFundingState().equals(FundingState.WAITING)) {
                if(f.getDonations().size()==0)
                    fundingResList.add(new FundingRes(f));
                else {
                    fundingResList.add(new FundingRes(donationRepository.findDonationByFundingId(f.getId())));
                }
            }
        }
        return fundingResList;
    }
}
