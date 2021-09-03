pragma solidity ^0.5.12;

import "./Ownable.sol";

/**
 * @title PurchaseRecord
 * 에스크로 상태 변경 시 이력을 추가 저장한다. 
 * @notice 아래의 변수와 함수를 활용하거나 변경하여 구현한다. 
 */

interface PurchaseRecordInterface {
    function addPurchase(address _address) external;
    function confirmDeposit(address _address) external;
    function sendItem(address _address) external;
    function cancelPurchase(address _address) external;
    function refund(address _address) external;
    function confirmPurchase(address _address) external;
}

contract PurchaseRecord is PurchaseRecordInterface, Ownable {
    
    // 구매 이력 상태의 예 
    enum State {Purchased, Paid, Sent, Complete, Cancelled, Refunded, End}
    
    struct Record {
        State state;
        uint256 when; 
        address by;
    }
    
    address public admin;
     
    // 이력 저장을 위한 상태 변수의 예 
    mapping (address => mapping(uint => Record)) escrowToRecords;
    mapping (address => uint8) escrowToRecordNo;
    
    modifier onlyEscrow {
        require(exists(msg.sender));
        _;
    }
    
    constructor() public {
        admin = msg.sender;
    }
    
    function addPurchase(address _address) external {
        // todo 
    }

    function confirmDeposit(address _address) external {
        // todo 
    }
    
    function sendItem(address _address) external {
        // todo 
    }
    
    function cancelPurchase(address _address) external {
        // todo 
    }
    
    function refund(address _address) external {
        // todo 
    }
    
    function confirmPurchase(address _address) external {
        // todo 
    }

    // todo 이력을 조회할 수 있는 함수가 반드시 필요함.
    // 상태변수의 타입을 고려하여 반환형을 추가하여 함수를 작성한다.
    function getRecord(
        // 필요한 인자 추가 가능
    ) public view 
        // 반환형 추가 
    {
    }
}