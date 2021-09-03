pragma solidity ^0.5.12;

import "./Ownable.sol";

/**  
 * @title ItemInventory
 * 판매 가능한 상품 정보를 저장, 업데이트 하고 
 * 구매 발생 시 에스크로를 생성하는 스마트 컨트랙트 
 * @notice 아래의 변수와 함수를 활용하거나 변경하여 구현한다. 
 */
contract ItemInventory is Ownable{
    
    struct Item {
        uint32 itemId;
        uint32 price;
        address seller;
        uint256 registeredAt;
        uint256 deletedAt;
        bool available;
    }
    
    event NewEscrow(address indexed seller, address indexed buyer, uint itemId, uint purchaseId, address _address);
   
    address public admin;
   
    mapping (uint32 => Item) public items;

    // 생성된 모든 escrow 리스트 
    Escrow[] public escrows; 
    mapping (uint256 => Escrow) public purchaseIdToEscrow;  
    
    constructor() public {
        admin = msg.sender;
    }
    
    function setPaymentMethod(address _address) public onlyOwner {
        // todo 
    }
    
    function setPurchaseRecordContract(address _address) public onlyOwner {
        // todo 
    }
    
    function registerItem(uint32 itemId, uint32 price) public returns(uint32, uint32, address, uint256){
        // todo 
        return (0,  0, address(0), 0);
    }

    function purchaseItem(uint32 itemId) public returns(uint256){
        // todo 
        // 구매 정보에 따라 Escrow를 생성한다. 
        return 0;  
    }
    
    function deregisterItem(uint16 itemId) public returns(bool) {
        // todo 
        return false;
    }
}

/**  
 * @title Escrow
 * 구매 발생 시 ItemInventory에 의해 생성되며 스마트 컨트랙트 
 * 조건에 따라 상태를 변경하며 구매의 자동 지불, 이력 추가를 이행한다. 
 * @notice 아래의 변수와 함수를 활용하거나 변경하여 구현한다. 
 */
contract Escrow {
    
    // 에스크로의 상태 예
    enum State {Purchased, Paid, Sent, Complete, Cancelled}

    // 에스크로의 상태 변수의 예
    uint32 public itemId;
    address public seller;
    address public buyer;
    uint32 public price;
    State public state;
    uint public purchasedAt;
    uint private initialCash;
    uint public completeAt;
    
    /*
    * @dev 아래와 같이 추가 modifier를 사용하는 것을 권장함. 
    */
    modifier onlySeller {
        require(msg.sender == seller);
        _;
    }
    
     modifier onlyBuyer {
        require(msg.sender == buyer);
        _;
    }
    
    // ItemInventory에 의해 호출되는 생성자
    constructor(
        // 필요한 인자 추가 가능 
    ) public {
        // todo 
    }
    

    function checkDeposit() public returns(bool) {
        // todo 
        return false;
    }
    
    function send() public onlySeller {
        // todo 
    }
    
    function cancel() public {
        // todo 
    }
    
    function confirm() public onlyBuyer returns (bool){
        // todo 
        return false;
    }
    
}