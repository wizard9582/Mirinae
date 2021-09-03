<template>
  <div>
    <h-breadcrumb
      title="거래 상세 정보"
      description="거래 상태를 확인하고 거래를 진행합니다. "
    ></h-breadcrumb>
    <div class="container">
      <div class="row">
        <div class="col-md-8 mx-auto">
          <div class="card">
            <div class="card-header">구매 거래</div>
            <div class="card-body">
              <div class="form-group">
                <div class="row">
                  <img :src="getImg()" class="col-3" />
                  <div class="col-9" style="vertical-align: middle;">
                    <h2>
                      {{ tx.itemName }}
                    </h2>
                    <h4 class="alert alert-primary">{{ tx.price }} CASH</h4>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label id="user" class="text-secondary">판매자</label>
                <div class="content">
                  <p>{{ tx.sellerName }}({{ tx.sellerEmail }})</p>
                </div>
              </div>
              <div class="form-group">
                <label class="text-secondary">구매 일자</label>
                <div class="content">
                  <p>{{ tx.createdAt }}</p>
                </div>
              </div>
              <div class="form-group">
                <label class="text-secondary">
                  내 지갑 잔액
                </label>
                <br />
                <div v-if="wallet.id === undefined" class="content">
                  <p class="text-danger">지갑이 없습니다.</p>
                </div>
                <div v-else class="content">
                  <p>{{ wallet.balance }} ETH</p>
                  <p>
                    {{ wallet.cash }} CASH
                    <span
                      v-if="wallet.cash === undefined"
                      class="ml-3 text-primary"
                      >지갑 정보 조회중...</span
                    >
                    <span
                      v-else-if="
                        tx.state == eState.INITIAL.symbol &&
                          Number(wallet.cash) < Number(tx.price) + 20
                      "
                      class="ml-3 text-danger"
                      >잔액이 부족합니다.</span
                    >
                  </p>
                </div>
              </div>
              <div class="form-group">
                <label class="text-secondary">
                  거래 상태
                </label>
                <br />
                <div class="content">
                  <p>
                    {{ getState }}
                    <a
                      @click.prevent="checkPaid"
                      v-show="tx.state == eState.INITIAL.symbol"
                      href=""
                    >
                      <svg
                        width="1em"
                        height="1em"
                        viewBox="0 0 16 16"
                        class="bi bi-arrow-clockwise"
                        fill="currentColor"
                        xmlns="http://www.w3.org/2000/svg"
                      >
                        <path
                          fill-rule="evenodd"
                          d="M3.17 6.706a5 5 0 0 1 7.103-3.16.5.5 0 1 0 .454-.892A6 6 0 1 0 13.455 5.5a.5.5 0 0 0-.91.417 5 5 0 1 1-9.375.789z"
                        />
                        <path
                          fill-rule="evenodd"
                          d="M8.147.146a.5.5 0 0 1 .707 0l2.5 2.5a.5.5 0 0 1 0 .708l-2.5 2.5a.5.5 0 1 1-.707-.708L10.293 3 8.147.854a.5.5 0 0 1 0-.708z"
                        />
                      </svg>
                    </a>
                  </p>
                </div>
              </div>
              <div v-if="tx.state == eState.INITIAL.symbol" class="form-group">
                <label class="text-secondary">
                  입금 금액 (CASH)
                  <span class="ml-2 text-primary"
                    >* 보증금을 20 CASH 이상 넣어주세요</span
                  >
                </label>
                <input
                  v-model="input.payAmount"
                  type="number"
                  class="form-control"
                />
              </div>
              <div
                v-if="
                  tx.state != eState.CONFIRMED.symbol &&
                    tx.state != eState.CANCELED.symbol
                "
                class="form-group"
              >
                <label class="text-secondary">지갑 개인키</label>
                <input
                  v-model="input.privateKey"
                  type="text"
                  class="form-control"
                  placeholder="지갑 개인키를 입력해주세요."
                />
              </div>

              <div class="row">
                <div class="col-md-6">
                  <button
                    class="btn btn-sm btn-outline-secondary"
                    v-on:click="$router.go(-1)"
                  >
                    이전으로 돌아가기
                  </button>
                </div>

                <div class="col-md-6 text-right">
                  <button
                    v-if="actionText.default"
                    class="btn btn-sm btn-primary"
                    v-on:click="doAction"
                    v-bind:disabled="processing"
                  >
                    {{ processing ? actionText.inProcess : actionText.default }}
                  </button>
                  <button
                    v-if="tx.state == eState.INITIAL.symbol"
                    class="btn btn-sm btn-danger ml-1"
                    @click="cancelTx"
                    v-bind:disabled="processing"
                  >
                    취소하기
                  </button>
                  <button
                    v-if="tx.state == eState.PAID.symbol"
                    class="btn btn-sm btn-danger ml-1"
                    @click="cancelTx"
                    v-bind:disabled="processing"
                  >
                    환불하기
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { findById as findUserById } from "@/api/user.js";
import * as walletService from "@/api/wallet.js";
import { getLocalImg } from "@/utils/imgLoader.js";
import { confirm, cancel } from "@/utils/itemInventory.js";
import { leaveDeposit } from "@/utils/cashContract.js";
import { ESCROW_STATE } from "@/config/constants.js";
import { checkDeposit, changeState } from "@/api/purchase.js";

export default {
  name: "PurchaseTxDetail",
  data() {
    return {
      tx: {
        buyerId: null,
        contractAddress: null,
        createdAt: null,
        id: null,
        image: null,
        itemId: null,
        itemName: "",
        price: null,
        purchaseId: null,
        sellerId: null,
        state: null
      },
      processing: false,
      wallet: {
        address: ""
      },
      input: {
        payAmount: null,
        privateKey: ""
      },
      actionText: {
        default: "",
        inProcess: ""
      },
      eState: ESCROW_STATE,
      userId: this.$store.state.user.id
    };
  },
  methods: {
    /**
     * 거래 상태 변경을 위한 버튼 클릭 이벤트 메서드
     * 거래 상태에 따라 동작을 다르게 수행함
     */
    doAction() {
      switch (ESCROW_STATE.symbolToState(this.tx.state)) {
        case ESCROW_STATE.INITIAL:
          this.pay();
          break;
        case ESCROW_STATE.SENT:
          this.confirmPurchase();
          break;
      }
    },
    /**
     * TODO: PJTⅢ 과제3 Req.1-8 [입금 확인 요청]
     * 백엔드에 해당 구매 건에 대해 입금이 되었는지 확인 요청한다.
     */
    checkPaid() {
    },
    pay() {
      const vm = this;
      this.processing = true; // [UI] '입금 요청 중'임을 표시

      /**
       * TODO: PJTⅢ 과제3 Req.1-7 [입금]
       * 토큰 전송하는 스마트 컨트랙트 호출
       */
    },
    /**
     * TODO: PJTⅢ 과제3 Req.1-10 [구매 확정]
     * 구매 확정하는 스마트 컨트랙트 호출 후 DB 데이터 업데이트
     */
    confirmPurchase() {
    },
    /**
     * TODO: PJTⅢ 과제3 Req.1-6 [거래 취소]
     * 이더리움에 거래 취소 요청 후 DB 업데이트
     */
    cancelTx() {
    },
    /**
     * 거래 상태에 따라 버튼 텍스트를 변경
     * 빈 문자열일 때 버튼은 보이지 않음
     */
    setActionText() {
      switch (ESCROW_STATE.symbolToState(this.tx.state)) {
        case ESCROW_STATE.INITIAL:
          this.actionText.default = "입금하기";
          this.actionText.inProcess = "입금 요청 중입니다...";
          break;
        case ESCROW_STATE.SENT:
          this.actionText.default = "구매확정";
          this.actionText.inProcess = "구매 확정 중입니다...";
          break;
        default:
          this.actionText.default = "";
          this.actionText.inProcess = "";
      }
    },
    getImg() {
      if (this.tx.image) {
        return getLocalImg(this.tx.image);
      }

      return null;
    },
    getWalletBalance() {
      const vm = this;
      walletService.findByUserId(vm.userId, function(response) {
        const wallet = response.data;
        wallet["balance"] = Number(wallet["balance"]) / 10 ** 18;
        wallet["cash"] = Number(wallet["cash"]);
        vm.wallet = wallet;
      });
    }
  },
  computed: {
    getState() {
      if (this.tx.state) {
        return ESCROW_STATE.symbolToState(this.tx.state).explanation;
      } else {
        return "상태 조회 중";
      }
    }
  },
  mounted: function() {
    const vm = this;
    this.tx = this.$route.params.tx;
    this.setActionText();
    // 내 지갑 정보 조회
    this.getWalletBalance();
    // [UI] 판매자 정보 조회
    findUserById(
      this.tx.sellerId,
      function(res) {
        vm.$set(vm.tx, "sellerName", res.data.name);
        vm.$set(vm.tx, "sellerEmail", res.data.email);
      },
      function(err) {
        console.error("사용자 정보 조회를 실패했습니다.", err);
        alert("판매자 정보 조회를 실패했습니다.");
      }
    );
  }
};
</script>

<style></style>
