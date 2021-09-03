import { createWeb3 } from "./itemInventory.js";

const web3 = createWeb3();

/**
 * 이더리움의 wei 단위를 ether로 변환
 * (Javascript 숫자형 특성 상 ~자리 이상의 수는 지수표현법으로 표기되므로 그보다 작은 수까지만 사용,
 *  지수 표현법으로 표현된 문자열은 지원 안됨)
 * @param {String|BN} value
 * @returns {String|BN} If a number, or string is given it returns a number string, otherwise a BN.js instance.
 */
export function weiToEth(value) {
  return web3.utils.fromWei(value, "ether");
}
/**
 * 이더리움의 ether 단위를 wei로 변환
 * @param {String|Number|BN} value
 * @returns {String|BN} If a number, or string is given it returns a number string, otherwise a BN.js instance.
 */
export function ethToWei(value) {
  return web3.utils.toWei(value);
}
