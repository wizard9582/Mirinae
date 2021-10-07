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
import com.a506.mirinae.util.EthereumUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FundingService {
    private final UserRepository userRepository;
    private final FundingRepository fundingRepository;
    private final DonationRepository donationRepository;
    private final CategoryRepository categoryRepository;
    @Value("${blockchain.main.address}")
    private String address;
    
    @Value("${blockchain.main.owner}")
    private String owner;
    
    @Value("${blockchain.main.password}")
    private String password;
    
    @Value("${blockchain.main.contract}")
    private String contract;
    private EthereumUtil ethereumUtil = new EthereumUtil(address,contract);
    @Transactional
    public FundingSizeRes getFundingList(String categoryName, Pageable pageable) {
        List<Funding> funding;
        List<FundingRes> fundingResList = new ArrayList<>();
        if(categoryName.equals("all")) {
            funding = fundingRepository.findAllByFundingState(FundingState.ACCEPTED, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize())).getContent();
        }
        else {
            funding = fundingRepository.findByCategory_NameAndFundingState(categoryName, FundingState.ACCEPTED ,PageRequest.of(pageable.getPageNumber(), pageable.getPageSize())).getContent();
        }
        Long pageCount = (long) (fundingRepository.findAll().size() / pageable.getPageSize() + 1);
        for(Funding f : funding) {
            if(f.getDonations().size()==0)
                fundingResList.add(new FundingRes(f));
            else
                fundingResList.add(new FundingRes(donationRepository.findDonationByFundingId(f.getId())));
        }
        return new FundingSizeRes(fundingResList, pageCount);
    }

    public FundingIdRes createFunding(FundingReq fundingReq, Long id) {
        String wallet = "null";
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 User가 없습니다. user ID=" + id));
        Category category = categoryRepository.findById(fundingReq.getCategoryId())
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 카테고리가 없습니다. 카테고리 ID=" + fundingReq.getCategoryId()));
                
        // smart-contract openFunding 삽입 위치
        // 실패할 경우 분기처리 (Error Exception 추가)
        
        Funding funding = fundingRepository.save(fundingReq.toEntity(user, wallet, category));
        ethereumUtil.openFunding(funding.getId(), funding.getGoal().intValue(), user.getWallet(), user.getNickname(), 
        		funding.getTitle(), funding.getEndDatetime().toString(), owner, password);
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
        if (funding.getEndDatetime().isBefore(LocalDateTime.now())) {

            // smart-contract closeFunding 삽입 위치
            // 실패할 경우 분기처리 (Error Exception 추가)
        	ethereumUtil.closeFunding(funding.getId(), owner, password);
            throw new IllegalArgumentException("해당 펀딩은 이미 종료되었습니다!");
        }
        if(!funding.getFundingState().equals(FundingState.ACCEPTED))
            throw new IllegalArgumentException("해당 펀딩은 승인되지 않았습니다!");

        donationReq.getKey(); // 지갑 비밀 키

        // smart-contract doanteFunding 삽입 위치
        // 실패할 경우 분기처리 (Error Exception 추가)
        
        String tx_id = ethereumUtil.donateFunding(funding.getId(), user.getWallet(), 
        		donationReq.getAmount(), donationReq.getKey()); //블록체인 구현 후 tx id 받기
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
        FundingRes fundingRes;
        if(funding.getDonations().size()!=0)
            fundingRes = new FundingRes(donationRepository.findDonationByFundingId(funding.getId()));
        else
            fundingRes = new FundingRes(funding);

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

        // smart-contract abortFunding 삽입 위치
        // 실패할 경우 분기처리 (Error Exception 추가)
        ethereumUtil.abortFunding(funding.getId(), owner, password);
        fundingRepository.delete(funding);
    }

    @Transactional
    public void fundingEnd() {
        List<Funding> fundings = fundingRepository.findAllByIsEndedAndEndDatetimeBefore(false, LocalDateTime.now());
        log.info("종료될 펀딩 갯수 : " + fundings.size());
        for(Funding f : fundings) {
            f.endFunding();
            if(f.getDonations().size()!=0 && (double) donationRepository.findBalanceByFundingId(f.getId()).getBalance() < f.getGoal()) {
                for(Donation d: f.getDonations()){
                    donationRepository.delete(d);
                }
                f.deleteDonation();
            }
            // smart-contract closeFunding 삽입 위치
            ethereumUtil.closeFunding(f.getId(), owner, password);
            fundingRepository.save(f);
        }
    }
}
