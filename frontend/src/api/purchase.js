import { createInstance } from "./index.js";

const instance = createInstance();

function create(body, success, fail) {
  instance
    .post("/api/purchases", JSON.stringify(body))
    .then(success)
    .catch(fail);
}

function findMySalePurchases(id, success, fail) {
  instance
    .get("/api/purchases/seller/" + id)
    .then(success)
    .catch(fail);
}

function findMyPurchases(id, success, fail) {
  instance
    .get("/api/purchases/buyer/" + id)
    .then(success)
    .catch(fail);
}

/**
 * TODO: API 서버에 입금확인 요청
 * 입금 금액이 조건을 충족하지 않으면 500 error response
 */
function checkDeposit(id, success, fail) {
  instance
    .put("/api/purchases/" + id + "/deposit")
    .then(success)
    .catch(fail);
}

/**
 * 
 * @param {Number} id purchaseId
 * @param {String} state S/C/X 중 하나
 */
function changeState(id, state, success, fail) {
  instance
    .put("/api/purchases/" + id + "/state/" + state)
    .then(success)
    .catch(fail);
}

/**
 * 거래 이력을 가져온다.
 * @param {Number} id purchaseId 
 */
function getHistory(id, success, fail) {
  instance
    .get("/api/purchases/history/" + id)
    .then(success)
    .catch(fail)
}

export {
  create,
  findMySalePurchases,
  findMyPurchases,
  checkDeposit,
  changeState,
  getHistory
}