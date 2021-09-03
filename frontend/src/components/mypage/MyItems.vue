<template>
  <div>
    <h-breadcrumb
      title="마이페이지"
      description="지갑을 생성하거나 상품 상태를 확인할 수 있습니다."
    ></h-breadcrumb>
    <div class="container">
      <my-page-nav></my-page-nav>
      <div id="my-item" class="row">
        <h4 class="col-md-12 mt-5">내가 판매한 거래</h4>
        <table
          v-if="saleTx.length > 0"
          id="sale-item-table"
          class="table table-bordered mt-3"
        >
          <thead class="thead-light">
            <tr>
              <th scope="col" style="width: 25%">생성 일자</th>
              <th scope="col" style="width: 50%">상품 정보</th>
              <th scope="col" style="width: 10%">금액 (CASH)</th>
              <th scope="col" style="width: 15%">거래상태</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="tx in saleTx" :key="tx.id">
              <td>{{ tx.createdAt }}</td>
              <td>
                <router-link :to="{ path: '/item/detail/' + tx.itemId }">
                  <div class="row">
                    <img
                      :src="imgPath(tx.image)"
                      class="col-2"
                      style="height: 70px;"
                    />
                    <p>{{ tx.itemName }}</p>
                  </div>
                </router-link>
              </td>
              <td>{{ tx.price }}</td>
              <td>
                <div>
                  {{ symbolToState(tx.state).explanation }}
                  <a
                    v-show="tx.state == eState.INITIAL.symbol"
                    @click.prevent="checkPaid(tx)"
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
                </div>
                <button
                  v-if="tx.state === eState.PAID.symbol"
                  @click="
                    $router.push({
                      name: 'escrow.sale.detail',
                      params: { tx: tx }
                    })
                  "
                  class="btn btn-outline-secondary mb-1"
                >
                  배송하기
                </button>
                <button
                  v-if="tx.state === eState.INITIAL.symbol"
                  @click="
                    $router.push({
                      name: 'escrow.sale.detail',
                      params: { tx: tx }
                    })
                  "
                  class="btn btn-outline-danger mb-1"
                >
                  취소하기
                </button>
                <button
                  v-if="tx.state === eState.PAID.symbol"
                  @click="
                    $router.push({
                      name: 'escrow.sale.detail',
                      params: { tx: tx }
                    })
                  "
                  class="btn btn-outline-danger mb-1"
                >
                  환불하기
                </button>
                <button
                  @click="
                    $router.push({
                      name: 'escrow.history',
                      params: {
                        id: tx.purchaseId,
                        escrowContractAddress: tx.contractAddress
                      }
                    })
                  "
                  class="btn btn-outline-primary"
                >
                  이력보기
                </button>
              </td>
            </tr>
          </tbody>
        </table>
        <div v-else class="col-sm-12 col-md-8 mt-3">
          <div class="alert alert-warning">
            판매 거래가 없습니다.
          </div>
        </div>

        <h4 class="col-md-12 mt-5">내가 구매한 거래</h4>
        <table
          v-if="purchaseTx.length > 0"
          id="sale-item-table"
          class="table table-bordered mt-3"
        >
          <thead class="thead-light">
            <tr>
              <th scope="col" style="width: 25%">구매일</th>
              <th scope="col" style="width: 50%">상품 정보</th>
              <th scope="col" style="width: 10%">금액 (CASH)</th>
              <th scope="col" style="width: 15%">거래상태</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="tx in purchaseTx" :key="tx.id">
              <td>{{ tx.createdAt }}</td>
              <td>
                <router-link :to="{ path: '/item/detail/' + tx.itemId }">
                  <div class="row">
                    <img
                      :src="imgPath(tx.image)"
                      class="col-2"
                      style="height: 70px;"
                    />
                    <p>{{ tx.itemName }}</p>
                  </div>
                </router-link>
              </td>
              <td>{{ tx.price }}</td>
              <td>
                <p class="mb-1">
                  {{ symbolToState(tx.state).explanation }}
                  <a
                    v-show="tx.state == eState.INITIAL.symbol"
                    @click.prevent="checkPaid(tx)"
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
                <button
                  v-if="tx.state == eState.INITIAL.symbol"
                  @click="
                    $router.push({
                      name: 'escrow.purchase.detail',
                      params: { tx: tx }
                    })
                  "
                  class="btn btn-outline-secondary mb-1"
                >
                  입금하기
                </button>
                <button
                  v-else-if="tx.state === eState.SENT.symbol"
                  @click="
                    $router.push({
                      name: 'escrow.purchase.detail',
                      params: { tx: tx }
                    })
                  "
                  class="btn btn-outline-secondary mb-1"
                >
                  구매확정
                </button>
                <button
                  v-if="tx.state === eState.INITIAL.symbol"
                  @click="
                    $router.push({
                      name: 'escrow.purchase.detail',
                      params: { tx: tx }
                    })
                  "
                  class="btn btn-outline-danger mb-1"
                >
                  취소하기
                </button>
                <button
                  v-if="tx.state === eState.PAID.symbol"
                  @click="
                    $router.push({
                      name: 'escrow.purchase.detail',
                      params: { tx: tx }
                    })
                  "
                  class="btn btn-outline-danger mb-1"
                >
                  환불하기
                </button>
                <button
                  @click="
                    $router.push({
                      name: 'escrow.history',
                      params: {
                        id: tx.purchaseId,
                        escrowContractAddress: tx.contractAddress
                      }
                    })
                  "
                  class="btn btn-outline-primary"
                >
                  이력보기
                </button>
              </td>
            </tr>
          </tbody>
        </table>
        <div v-else class="col-sm-12 col-md-8 mt-3">
          <div class="alert alert-warning">
            구매 거래가 없습니다.
          </div>
        </div>

        <h4 class="col-md-12 mt-5">판매 상품</h4>
        <table
          v-if="saleItems.length > 0"
          id="purchase-item-table"
          class="table table-bordered mt-3"
        >
          <thead class="thead-light">
            <tr>
              <th scope="col" style="width: 25%">등록일</th>
              <th scope="col" style="width: 50%">상품 정보</th>
              <th scope="col" style="width: 10%">금액 (CASH)</th>
              <th scope="col" style="width: 15%">상태</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="item in saleItems"
              :key="item.id"
              :class="{ 'table-secondary': !item.available }"
            >
              <td>{{ item.registeredAt }}</td>
              <td>
                <router-link :to="{ path: '/item/detail/' + item.id }">
                  <div class="row">
                    <img
                      :src="imgPath(item.image)"
                      class="col-2"
                      style="height: 70px;"
                    />
                    <p>
                      {{ item.name }}
                    </p>
                  </div>
                </router-link>
              </td>
              <td>{{ item.price }}</td>
              <td>
                <p>{{ item.available ? "판매중" : "판매 종료" }}</p>
                <button
                  v-if="item.available"
                  @click="deregister(item)"
                  type="button"
                  class="btn btn-outline-secondary"
                >
                  판매 종료
                </button>
              </td>
            </tr>
          </tbody>
        </table>
        <div v-else class="col-sm-12 col-md-8 mt-3">
          <div class="alert alert-warning">
            판매 상품이 없습니다.
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { findMySaleItems, remove } from "@/api/item.js";
import {
  findMySalePurchases as findSaleTx,
  findMyPurchases as findPurchaseTx,
  checkDeposit
} from "@/api/purchase.js";
import MyPageNav from "./MyPageNav.vue";
import { ESCROW_STATE } from "@/config/constants.js";
import { getLocalImg } from "@/utils/imgLoader.js";
import { getPrice, deregisterItem } from "@/utils/itemInventory.js";

export default {
  name: "MyItems",
  components: {
    MyPageNav
  },
  data() {
    return {
      eState: ESCROW_STATE,
      user: {
        id: this.$store.state.user.id,
        walletAddress: this.$store.state.user.walletAddress
      },
      saleTx: [], // 내가 판매한 거래
      purchaseTx: [], // 내가 구매한 거래
      saleItems: [] // 판매 상품
    };
  },
  methods: {
    symbolToState(state) {
      return ESCROW_STATE.symbolToState(state);
    },
    imgPath(name) {
      return getLocalImg(name);
    },
    deregister(item) {
      const privateKey = prompt(
        "해당 아이템을 판매 종료하시려면 개인키를 입력하세요."
      );
      if (privateKey) {
        /**
         * TODO: PJTⅢ 과제3 Req.1-2 [상품 판매 취소]
         * 이더리움의 상품 상태를 변경 후 DB를 변경
         */
      }
    },
    /**
     * TODO: PJTⅢ 과제3 Req.1-8 [입금 확인 요청]
     * 백엔드에 해당 구매 건에 대해 입금이 되었는지 확인 요청한다.
     */
    checkPaid(tx) {
    }
  },
  mounted() {
    const vm = this;
    // 판매 상품 목록 가져오기
    findMySaleItems(
      this.user.id,
      function(res) {
        // 판매 상품이 없음
        if (!res.data) {
          return;
        }

        vm.saleItems = res.data;
        vm.saleItems.forEach((i) => {
          // [스마트 컨트랙트] 가격 조회
          getPrice(
            i.id,
            function(price) {
              vm.$set(i, "price", price);
            },
            function(err) {
              console.error("가격 조회 실패:", err);
              alert("상품 가격 조회를 실패했습니다.");
            }
          );
        });
      },
      function(err) {
        console.error(err);
      }
    );
    /**
     * TODO: PJTⅢ 과제3 Req.1-4 [구매 목록 조회]
     * DB에서 구매 거래 목록을 가져온 뒤 컨트랙트에서 상품 가격을 조회
     */
    
    /**
     * TODO: PJTⅢ 과제3 Req.1-5 [판매 목록 조회]
     * DB에서 판매 거래 목록을 가져온 뒤 컨트랙트에서 상품 가격을 조회
     */
  }
};
</script>

<style>
.table td,
.table tr {
  text-align: center;
}
/* .badge-primary {
  color: #fff;
  background-color: #007bff;
}
.badge-info {
  color: #fff;
  background-color: #17a2b8;
}
.btn-show-history {
  background-color: #5c130e;
  color: white;
}
hr {
  border: 0;
  clear: both;
  display: block;
  width: 96%;
  background-color: black;
  height: 2px;
} */
</style>
