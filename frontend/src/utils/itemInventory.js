/**
 * TODO: PJTⅢ 과제3
 * 스마트 컨트랙트와 상호작용할 함수들을 구현합니다.
 */
import Web3 from 'web3';
import {
  BLOCKCHAIN_URL,
  BLOCKCHAIN_WEBSOCKET_URL,
  ITEM_INVENTORY_CONTRACT_ADDRESS,
} from '../config';
import {
  ITEM_INVENTORY_CONTRACT_ABI,
  ESCROW_CONTRACT_ABI,
} from '../config/ABIs.js';

// Web3 Object 생성
export function createWeb3() {
  var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
  return web3;
}

// ItemInventory 컨트랙트의 인스턴스
export function createFactoryContract(web3) {
  var inventoryContract = new web3.eth.Contract(
    ITEM_INVENTORY_CONTRACT_ABI,
    ITEM_INVENTORY_CONTRACT_ADDRESS,
  );
  return inventoryContract;
}

// Escrow 컨트랙트의 인스턴스
export function createEscrowContract(web3, contractAddress) {
  var escrowContract = new web3.eth.Contract(
    ESCROW_CONTRACT_ABI,
    contractAddress,
  );
  return escrowContract;
}

/**
 *  TODO: PJTⅢ 과제3 Req.1-1 [상품 등록]
 */
export function registerItem(
  options,
  privateKey,
  onConfirm,
  onFail
) {
  
}

/**
 *  TODO: PJTⅢ 과제3 Req.1-2 [상품 판매 취소]
 */
export function deregisterItem(
  itemId,
  privateKey,
  onConfirm,
  onFail,
) {
  
}

/**
 *  TODO: PJTⅢ 과제3 Req.1-3 [상품 구매]
 */
export function purchaseItem(
  itemId,
  privateKey,
  onConfirm,
  onFail,
) {
  
}

/**
 *  TODO: PJTⅢ 과제3 Req.1-6 [거래 취소]
 */ 
export function cancel(
  escrowContractAddress,
  privateKey,
  onConfirm,
  onFail
) {

}

/**
 *  TODO: PJTⅢ 과제3 Req.1-9 [상품 발송]
 */
export function send(
  escrowContractAddress,
  privateKey,
  onConfirm,
  onFail
) {
  
}

/**
 *  TODO: PJTⅢ 과제3 Req.1-10 [구매 확정]
 */
export function confirm(
  escrowContractAddress,
  privateKey,
  onConfirm,
  onFail
) {
  
}


// 특정 상품의 가격 조회
export function getPrice(itemId, onConfirm, onFail) {
  var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
  var contract = createFactoryContract(web3);
  contract.methods
    .items(itemId)
    .call()
    .then(res => {
      onConfirm(res.price);
    })
    .catch(err => {
      onFail(err);
    });
}
