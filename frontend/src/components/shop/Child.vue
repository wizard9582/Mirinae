<template>
  <div>
    <h-shop-categories :category="'유아물품'"></h-shop-categories>
    <div id="artwork-list" class="container">
      <div class="row">
        <div class="col-md-12 text-right">
          <router-link to="/item/create" class="btn btn-outline-secondary"
            >상품 등록하기</router-link
          >
        </div>
      </div>
      <div class="row">
        <div
          class="col-md-3 artwork"
          v-for="item in items"
          v-bind:key="item.id"
        >
          <item-card :item="item" @clicked="onClickItem(item.id)"></item-card>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12 text-center">
          <nav class="bottom-pagination">
            <ul class="pagination">
              <li class="page-item disabled">
                <a class="page-link" href="#">이전</a>
              </li>
              <li class="page-item"><a class="page-link" href="#">1</a></li>
              <li class="page-item"><a class="page-link" href="#">2</a></li>
              <li class="page-item"><a class="page-link" href="#">3</a></li>
              <li class="page-item"><a class="page-link" href="#">4</a></li>
              <li class="page-item"><a class="page-link" href="#">5</a></li>
              <li class="page-item"><a class="page-link" href="#">6</a></li>
              <li class="page-item"><a class="page-link" href="#">7</a></li>
              <li class="page-item"><a class="page-link" href="#">8</a></li>
              <li class="page-item"><a class="page-link" href="#">9</a></li>
              <li class="page-item"><a class="page-link" href="#">10</a></li>
              <li class="page-item"><a class="page-link" href="#">다음</a></li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { findAll } from "@/api/item.js";
import { CATEGORIES } from "@/config/constants.js";
import HShopCategories from "./HShopCategories.vue";
import ItemCard from "./ItemCard.vue";
import { getPrice } from '@/utils/itemInventory.js';

export default {
  components: {
    HShopCategories,
    ItemCard
  },
  data() {
    return {
      items: []
    };
  },
  methods: {
    onClickItem(itemId) {
      this.$router.push({name: "item.detail", params: {
        id: itemId
      }});
    }
  },
  mounted: function() {
    const vm = this;

    findAll(function(response) {
      if (response.data.length > 0) {
        vm.items = response.data.filter(
          e =>
            e.category === CATEGORIES.CHILD
        );
        vm.items.forEach(i => {
          getPrice(
            i.id,
            function(price) {
              vm.$set(i, "price", price);
            },
            function(err) {
              console.error('가격 조회 실패:', err);
              alert("상품 가격 조회를 실패했습니다.");
            }
          )
        });
      }
    });
  }
};
</script>

<style></style>
