import Web3 from 'web3';
import {
  BLOCKCHAIN_URL,
  PURCHASE_RECORD_CONTRACT_ADDRESS
} from '../config';
import {
  PURCHASE_RECORD_CONTRACT_ABI
} from '../config/ABIs.js';

// Web3 Object 생성
export function createWeb3() {
  var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
  return web3;
}

// PurchaseRecord 컨트랙트의 인스턴스
export function createPurchaseRecordContract(web3) {
  var escrowContract = new web3.eth.Contract(
    PURCHASE_RECORD_CONTRACT_ABI,
    PURCHASE_RECORD_CONTRACT_ADDRESS,
  );

  return escrowContract;
}
/**
 * TODO: PJTⅢ 과제3 Req.1-11 [직접 이력 조회]
 * 에스크로의 이력 개수를 가져옴
 */
export function getRecordNumber(escrowContractAddress, onConfirm, onFail) {
  
}
/**
 * TODO: PJTⅢ 과제3 Req.1-11 [직접 이력 조회]
 * 에스크로의 N번째(recordNo) 이력을 가져옴
 */
export function getRecord(escrowContractAddress, recordNo, onConfirm, onFail) {
  
}
