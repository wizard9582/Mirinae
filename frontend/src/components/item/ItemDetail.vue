<template>
  <div>
    <h-breadcrumb
      title="상품 상세 보기"
      description="등록된 상품의 상세 내역을 볼 수 있습니다."
    ></h-breadcrumb>
    <div class="container">
      <div class="row">
        <div class="col-md-8 mx-auto">
          <div class="card">
            <div class="card-body">
              <div class="form-group">
                <h3>
                  <a href="">{{ item.category | symbolToFullName }}</a> >
                  {{ item.name }}
                </h3>
              </div>
              <img
                class="center"
                :src="getImg(item.image)"
                style="max-height: 500px;"
              />
              <div class="form-group">
                <h4 class="alert alert-primary">{{ item.price }} CASH</h4>
              </div>
              <div class="form-group">
                <label id="user" class="text-secondary">판매자</label>
                <p>
                  {{ item.seller.name }}({{ item.seller.email }})
                </p>
              </div>
              <div class="form-group">
                <label class="text-secondary">상품 등록일</label>
                <p>{{ item.registeredAt }}</p>
              </div>
              <div class="form-group">
                <label id="explanation" class="text-secondary">상품 설명</label>
                <p v-if="item.explanation.length > 0">{{ item.explanation }}</p>
                <p v-else>-</p>
              </div>
              <div class="form-group">
                <label id="state" class="text-secondary">상태</label><br />
                <p>{{ item.available ? "판매중" : "판매 종료" }}</p>
              </div>
              <div class="row" v-if="userId !== item.seller.id">
                <div class="col-md-12 text-right">
                  <router-link
                    :to="{
                      name: 'item.purchase',
                      params: { id: item.id, seller: item.seller, image: item.image, name: item.name, price: item.price },
                    }"
                    class="btn btn-lg btn-primary"
                    >구매하기</router-link
                  >
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
// import * as itemService from "../../api/item.js";
// import * as auctionService from "../../api/auction.js";
// import * as userService from "../../api/user.js";
import { findById as findUserById } from '@/api/user.js';
import { weiToEth } from '@/utils/ethereumUnitUtils.js';
// import { ITEM_STATUS } from "../../config/constants.js";
import { getLocalImg } from '@/utils/imgLoader.js';
import { getPrice } from '@/utils/itemInventory.js';
import { findById } from '@/api/item.js';
import { CATEGORY } from '@/utils/category.js';

export default {
  name: 'ItemDetail',
  data() {
    return {
      item: {
        id: null,
        name: '',
        category: null,
        explanation: '',
        available: null,
        state: '',
        seller: {
          id: null,
          name: '',
          email: '',
        },
        image: null,
        price: null,
        registeredAt: null,
      },
      userId: this.$store.state.user.id,
    };
  },
  methods: {
    goBack: function() {
      // 이전 페이지로 이동한다.
      this.$router.go(-1);
    },
    convertWeiToEth(value) {
      if (value) {
        return weiToEth(value.toString()) + ' ETH';
      } else {
        return '-';
      }
    },
    getImg(name) {
      if (name) {
        return getLocalImg(name);
      }
      return null;
    },
  },
  filters: {
    symbolToFullName(symbol) {
      return CATEGORY[symbol];
    },
  },
  created() {
    this.item.id = this.$route.params.id;
  },
  mounted: function() {
    const vm = this;

    // [DB] 상품 상세 정보 조회
    findById(
      this.item.id,
      function(res) {
        const result = res.data;
        vm.item.name = result.name;
        vm.item.category = result.category;
        vm.item.explanation = result.explanation ? result.explanation : '';
        vm.item.available = result.available;
        vm.item.seller.id = result.seller;
        vm.item.image = result.image;
        vm.item.registeredAt = result.registeredAt;

        // 판매자 정보
        findUserById(result.seller, function(res) {
          const result = res.data;
          vm.item.seller.name = result.name;
          vm.item.seller.email = result.email;
        });
      },
      function(error) {
        console.error(error);
        alert('DB에서 상품 상세 정보 조회를 가져올 수 없습니다.');
      },
    );
    // [Smart Contract] 가격 조회
    getPrice(
      this.item.id,
      function(price) {
        vm.item.price = price;
      },
      function(err) {
        alert('상품 가격 조회에 실패했습니다.');
        console.error('가격 조회 실패:', err);
      },
    );
  },
};
</script>

<style>
img.center {
  display: block;
  margin: 2rem auto;
}
</style>
