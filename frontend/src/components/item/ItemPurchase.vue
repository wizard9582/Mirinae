<template>
  <div>
    <h-breadcrumb
      title="상품 구매하기"
      description="선택한 상품을 구매합니다."
    ></h-breadcrumb>
    <div class="container">
      <div class="row">
        <div class="col-md-8 mx-auto">
          <div class="card">
            <div class="card-header">상품 구매하기</div>
            <div class="card-body">
              <div class="form-group">
                <div class="row">
                  <img :src="getImg($route.params.image)" class="col-3" />
                  <div class="col-9" style="vertical-align: middle;">
                    <h2>
                      {{ $route.params.name }}
                    </h2>
                    <h4 class="alert alert-primary">
                      {{ $route.params.price }} CASH
                    </h4>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label
                  ><b>내 지갑 잔액</b>
                  <span class="ml-2 text-primary">* 보증금 20 CASH</span></label
                ><br />
                <div v-if="wallet.id === undefined">
                  <p class="text-danger">지갑이 없습니다.</p>
                </div>
                <div v-else>
                  <p>{{ wallet["balance"] }} ETH</p>
                  <p>
                    {{ wallet["cash"] }} CASH
                    <span
                      v-if="wallet.cash === undefined"
                      class="ml-3 text-primary"
                      >지갑 정보 조회중...</span
                    >
                    <span
                      v-else-if="
                        Number(wallet.cash) < Number($route.params.price) + 20
                      "
                      class="ml-3 text-danger"
                      >잔액이 부족합니다.</span
                    >
                  </p>
                </div>
              </div>
              <div class="form-group">
                <label><b>지갑 개인키</b></label>
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
                    class="btn btn-sm btn-primary"
                    v-on:click="purchase"
                    v-bind:disabled="!canPurchase"
                  >
                    {{ purchasing ? "구매 요청 중입니다." : "구매하기" }}
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
import * as walletService from "@/api/wallet.js";
import { weiToEth } from "@/utils/ethereumUnitUtils.js";
import { getLocalImg } from "@/utils/imgLoader.js";
import { purchaseItem } from "@/utils/itemInventory.js";
import { create } from "@/api/purchase.js";
//import BN from 'bn.js';

export default {
  name: "ItemPurchase",
  data() {
    return {
      purchasing: false,
      auction: {},
      wallet: {},
      input: {
        privateKey: ""
      },
      userId: this.$store.state.user.id
    };
  },
  methods: {
    purchase() {
      const vm = this;
      this.purchasing = true;
      /**
       * TODO: PJTⅢ 과제3 Req.1-3 [상품 구매]
       * 이더리움에 에스크로 생성 후 DB에 등록
       */
    },
    convertWeiToEth(value) {
      if (value) {
        return weiToEth(value.toString());
      }

      return value;
    },
    getImg(name) {
      if (name) {
        return getLocalImg(name);
      }
      return null;
    }
  },
  computed: {
    canPurchase() {
      return (
        this.input.privateKey.length > 0 &&
        this.$route.params.price <= this.wallet.cash
      );
    }
  },
  mounted: function() {
    const vm = this;
    // 내 지갑 정보 조회
    walletService.findByUserId(vm.userId, function(response) {
      const wallet = response.data;
      wallet["balance"] = Number(wallet["balance"]) / 10 ** 18;
      wallet["cash"] = Number(wallet["cash"]);
      vm.wallet = wallet;
    });
  }
};
</script>

<style></style>
