import { BLOCKCHAIN_URL } from "../config"; // todo check index.js가 import 되는지 확인하기
import Web3 from "web3";

const NUMBER_OF_CONTENTS_TO_SHOW = 10; // 한 번에 보여줄 정보의 개수
const REFRESH_TIMES_OF_OVERVIEW = 1000; // 개요 정보 갱신 시간 1초
const REFRESH_TIMES_OF_BLOCKS = 15000; // 블록 정보 갱신 시간 5초
const REFRESH_TIMES_OF_TRANSACTIONS = 15000; // 트랜잭션 정보 갱신 시간 15초

const web3 = new Web3(BLOCKCHAIN_URL);

// 가장 최근 블록 넘버를 비동기로 조회한다.
function fetchLatestBlock() {
  return web3.eth.getBlockNumber();
}

/*
    javascript 에서 URL 쿼리 스트링을 읽을 수 있게 해주는 함수
    https://test.com/?abc=123&def=456 의 URL을 아래와 같이 변환해서 리턴한다.

    [
        "abc": "123",
        "def": "456"
    ]
*/
function parseQueryString() {
  var values = [],
    item;
  var sliced = window.location.href.slice(
    window.location.href.indexOf("?") + 1
  );
  sliced = sliced.split("&");

  for (var i = 0; i < sliced.length; i++) {
    item = sliced[i].split("=");
    values.push(item[0]);
    values[item[0]] = item[1];
  }
  return values;
}

// from 블록 부터 end 블록까지 순차적으로 조회하여
// callback 함수를 실행한다.
function fetchBlocks(from, end, callback) {
  web3.eth.getBlock(from).then(function(block) {
    callback(block);

    var f = (from += 1);
    if (f <= end) {
      fetchBlocks(f, end, callback);
    }
  });
}

// timestamp 포맷을 사람이 읽을 수 있는 형태로 변환한다.
function timeSince(date) {
  var seconds = Math.floor((new Date() - date * 1000) / 1000);

  var interval = Math.floor(seconds / 31536000);

  if (interval > 1) {
    return interval + " years ago";
  }
  interval = Math.floor(seconds / 2592000);
  if (interval > 1) {
    return interval + " months ago";
  }
  interval = Math.floor(seconds / 86400);
  if (interval > 1) {
    return interval + " days ago";
  }
  interval = Math.floor(seconds / 3600);
  if (interval > 1) {
    return interval + " hours ago";
  }
  interval = Math.floor(seconds / 60);
  if (interval > 1) {
    return interval + " minutes ago";
  }
  return Math.floor(seconds) + " seconds ago";
}

export {
  NUMBER_OF_CONTENTS_TO_SHOW, // 한 번에 보여줄 정보의 개수
  REFRESH_TIMES_OF_OVERVIEW, // 개요 정보 갱신 시간 1초
  REFRESH_TIMES_OF_BLOCKS, // 블록 정보 갱신 시간 5초
  REFRESH_TIMES_OF_TRANSACTIONS, // 트랜잭션 정보 갱신 시간 15초
  fetchLatestBlock,
  parseQueryString,
  fetchBlocks,
  timeSince
};
