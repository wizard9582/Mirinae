import Web3 from 'web3';
import { BLOCKCHAIN_URL, BLOCKCHAIN_WEBSOCKET_URL, CASH_CONTRACT_ADDRESS } from '@/config';
import { CASH_CONTRACT_ABI } from '@/config/ABIs.js';

function createCashContract(web3) {
  return new web3.eth.Contract(CASH_CONTRACT_ABI, CASH_CONTRACT_ADDRESS);
}

/**
 * TODO: PJTⅡ 과제3 Req.1-1 [토큰 구매]
 * 이더를 지불하고 캐시를 충전
 * @param {walletAddress, amount} options 
 */
export function buyCash(amount, privateKey, onConfirm, onFail) {

}

/**
 * TODO: PJTⅢ 과제3 Req.1-7 [입금]
 * 구매자가 에스크로에 돈 입금
 * 스마트 컨트랙트의 Transfer 이벤트를 리스닝하고 있다가 완료 또는 실패 시 이벤트 메서드를 호출
 * @param {escrowAddress, amount} options 
 */
export function leaveDeposit(options, walletAddress, privateKey, onConfirm, onFail) {
  
}

/**
 * TODO: PJTⅡ 과제3 Req.1-2 [토큰 잔액 조회]
 * 스마트 컨트랙트의 잔액 확인 함수 호출
 */
export function getBalance(walletAddress, onConfirm, onFail) {

}