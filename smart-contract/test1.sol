// SPDX-License-Identifier: UNLICENSED

pragma solidity ^0.8.7;

contract test1 {
    address public owner;

    // 생성자
    constructor() {
        // contract 제작자 저장, 권한 확인용
        owner = msg.sender;
    }

    // 기부자주소, 기부금액, 작성된 블록 번호
    struct donation {
        address donator_address;
        uint price_wei;
        uint block_number;
    }

    // 펀딩ID, 목표금액, 종료일시, 모금주소, 기부내역, 진행상태, 취소여부, 개설자ID, 펀딩이름
    struct funding {
        uint funding_id;
        uint target_amount;
        uint current_amount;
        string close_time;
        address target_address;
        bool state_opened;
        bool state_aborted;
        bool state_success;
        string user_id;
        string title;
    }

    // 전체 펀딩, 기부 리스트 => Key : 펀딩ID
    mapping(uint => funding) private funding_list;
    mapping(uint => donation[]) private donation_list;

    // 펀딩 성공 여부 알림 (사용여부 확인 필요)
    event funding_open_success(uint funding_id);
    event funding_donate_success(uint funding_id, uint value);
    event funding_close_success(uint funding_id, bool success, string message);

    // 서비스 제공자 확인
    modifier onlyOwner() {
        require(owner == msg.sender, "Not Owner, Denied");
        _;
    }

    // 펀딩 여는 함수, OwnerOnly
    function openFunding(uint _funding_id, uint _target_amount, string memory _close_time, address _target_address, string memory _user_id, string memory _title) external onlyOwner {
        funding_list[_funding_id] = funding(_funding_id, _target_amount, 0, _close_time, _target_address, true, false, false, _user_id, _title);
        emit funding_open_success(_funding_id);
    }
    // 기부 함수
    function donateFunding(uint _funding_id) external payable {
        // 열려있는 펀딩에 대해 가능
        require(funding_list[_funding_id].state_opened == true, "Closed Funding");

        // 기부내역 기록
        _recordDonation(_funding_id, msg.sender, msg.value);
        emit funding_donate_success(_funding_id, msg.value);
    }

    // 펀딩 종료 함수, OwnerOnly 
    function closeFunding(uint _funding_id) external onlyOwner {
        if(funding_list[_funding_id].state_success) {
            _FundingSuccess(_funding_id);
        }
        else {
            _FundingFail(_funding_id);
        }
    }

    // 기부 내역 저장
    function _recordDonation(uint _funding_id, address _donator_address, uint _price_wei) private {
        // 기부내역 작성
        donation_list[_funding_id].push(donation(_donator_address, _price_wei, block.number));

        // 펀딩 현재 모금액 업데이트
        funding_list[_funding_id].current_amount += _price_wei;

        // 펀딩 성공여부 확인
        if(funding_list[_funding_id].state_success == false) {
            if(funding_list[_funding_id].current_amount >= funding_list[_funding_id].target_amount) {
                funding_list[_funding_id].state_success = true;
            }
        }
    }

    // 모금 성공, OwnerOnly
    function _FundingSuccess(uint _funding_id) private {
        funding_list[_funding_id].state_opened = false;

        // 모금된 금액 주소로 전송
        payable(funding_list[_funding_id].target_address).transfer(funding_list[_funding_id].current_amount);

        emit funding_close_success(_funding_id, true, "Success Funding");
    }

    // 모금 실패, OwnerOnly
    function _FundingFail(uint _funding_id) private {
        // 실패 기록
        funding_list[_funding_id].state_opened = false;
        funding_list[_funding_id].state_aborted = true;

        // 모금된 금액 모두 환불
        for(uint i = 0; i < donation_list[_funding_id].length; i++) {
            donation memory temp = donation_list[_funding_id][i];
            payable(temp.donator_address).transfer(temp.price_wei);
        }

        emit funding_close_success(_funding_id, false, "Fail Funding");
    }

    // 펀딩 상태 확인
    // true = open, false = closed
    function checkFunding(uint _funding_id) public view returns (bool) {
        if(funding_list[_funding_id].state_opened == true) { return true; }
        else { return false; }
    }

    // 기부 확인
    // 내가 기부한 금액 리턴
    function checkDonation(uint _funding_id) public view returns (uint) {
        for(uint i = 0; i < donation_list[_funding_id].length; i++) {
            if( payable(donation_list[_funding_id][i].donator_address) == msg.sender ) {
                return donation_list[_funding_id][i].price_wei;
            }
        }
        return 0;
    }

    // 새 유저를 위한 수도꼭지, OwnerOnly
    function faucet(address payable _user_address) external onlyOwner {
        _user_address.transfer(100 ether);
    }
}
