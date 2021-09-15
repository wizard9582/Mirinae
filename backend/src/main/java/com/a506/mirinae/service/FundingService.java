package com.a506.mirinae.service;

import com.a506.mirinae.domain.donation.Donation;
import com.a506.mirinae.domain.donation.DonationRepository;
import com.a506.mirinae.domain.funding.Funding;
import com.a506.mirinae.domain.funding.FundingRepository;
import com.a506.mirinae.domain.funding.FundingRes;
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
}
