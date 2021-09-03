// SPDX-License-Identifier: GPL-3.0
pragma solidity >=0.5.0 <0.7.0;

import "./SafeMath.sol";

interface IERC20 {

    function totalSupply() external view returns (uint256);
    function balanceOf(address account) external view returns (uint256);
    function transfer(address recipient, uint256 amount) external returns (bool);
    function allowance(address owner, address spender) external view returns (uint256);
    function approve(address spender, uint256 amount) external returns (bool);
    function transferFrom(address sender, address recipient, uint256 amount) external returns (bool);

    event Transfer(address indexed from, address indexed to, uint256 value);
    event Approval(address indexed owner, address indexed spender, uint256 value);
}

/** 
 * @title Cash
 * @notice Cash follows the erc20 standard and is used in the ecommerce service.
 */
contract Cash is IERC20{
    
    using SafeMath for uint256;
    
    /**
     * @notice constructor
     * totalSupply(inital supply), minter(owner)
     */
    constructor() public {
       
    }

    /**
     * optional function example
     * @notice mint
     * @param _receiver the receiver's address
     * @param _amount the amount of tokens
     */
    function mint(address _receiver, uint256 _amount) external 
    {
        // todo 
    }
    
    /**
     * @notice retrieves the balance that the account has
     * @param _account address
     * @return balance 
     */
    function balanceOf(address _account) external view returns (uint)		
    {
        // todo 
        return 0;
    }
    
    /**
     * @notice transfers the amount of token to the recipient
     * @param _recipient the receiver's address
     * @param _amount the amount of tokens
     * @return success or failure
     */
    function transfer(address _recipient, uint _amount) external returns (bool)
    {  
        // todo 
        return false;
    }

    /**
     * @notice retrieves the delegated balance 
     * @param _owner the onwer's address
     * @param _spender the delegator's address
     * @return the amount of allownace
     */
    function allowance(address _owner, address _spender) external view returns (uint)
    {
        // todo 
        return 0;	
    }  

    /**
     * @notice delegate the transfer
     * @param _spender the delegator's address
     * @param _amount the allowed amount of tokens
     * @return success or failure
     */    
    function approve(address _spender, uint _amount) external returns (bool)
    {
        // todo
        return false;
    }
    
    /**
     * @notice transfers the amount of token on behalf of the owner
     * @param _sender the sender's address
     * @param _recipient the receiver's address
     * @param _amount the amount of tokens
     * @return success of failure
     */     
    function transferFrom(address _sender, address _recipient, uint _amount) external returns (bool)
    {
        // todo
        return false;
    }

    /**
     * @notice buy tokens
     * msg.value should be greater than or equal to 0.1 ether
     * 1 eth = 100,000 cash
     * @return success or failure
     */      
    function buy() public payable returns(bool){
        // todo
        return false;
    }
    
}