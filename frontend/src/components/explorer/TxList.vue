<template>
  <div>
    <div class="row" v-if="!transactions || transactions.length == 0">
      <div class="col-md-8 mx-auto">
        <div class="alert alert-warning">
          No transaction recorded at. #{{ block && block.number }}
          blocks
        </div>
      </div>
    </div>
    <div class="row tx-info" v-for="item in transactions" :key="item.id">
      <div class="col-md-2">Tx</div>
      <div class="col-md-4">
        <router-link
          :to="{
            name: 'explorer.tx.detail',
            params: { hash: item.hash },
          }"
          class="tx-number"
          >{{ item.hash | truncate(10) }}</router-link
        >
        <p class="tx-timestamp">{{ item.timeSince }}</p>
      </div>
      <div class="col-md-6 addresses">
        <p>
          <label class="text-secondary">From</label>
          {{ item.from | truncate(10) }}
        </p>
        <p>
          <label class="text-secondary">To</label>
          {{ item.to | truncate(10) }}
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import {
  fetchLatestBlock,
  timeSince,
  REFRESH_TIMES_OF_TRANSACTIONS,
} from '../../utils/blockchainProvider.js';
import { createWeb3 } from "@/utils/itemInventory.js";

export default {
  props: [ 'countToShow' ],
  data() {
    return {
      transactions: [],
      block: {},
      blockHeight: null,
    };
  },
  methods: {
    fetchTxes: function() {
      /**
       * 1. 트랜잭션의 목록을 블록체인으로부터 직접 가져옵니다.
       * 2. 트랜잭션의 목록을 서버로부터 요청하여 가져옵니다.
       */
      var scope = this;
      fetchLatestBlock().then(function(height) {
        if (!scope.blockHeight || scope.blockHeight < height) {
          scope.blockHeight = height;
          scope.transactions.splice(0, scope.transactions.length);
          scope.initTxes(height);
        } else {
          scope.updateTimeSince();
        }
      });
    },
    async initTxes(height) {
      const scope = this;
      const web3 = createWeb3();
      let emitTxCount = false;
      let block;
      while (
        height >= 0 &&
        this.transactions.length < this.countToShow
      ) {
        block = await web3.eth.getBlock(height--);
        // 가장 최근 블록의 트랜잭션 개수
        if (!emitTxCount) {
          scope.$emit('updated', block.transactions.length);
          emitTxCount = true;
        }

        scope.block = block;
        block.transactions.forEach(function(tx) {
          var txInfo = {
            hash: tx,
            from: null,
            to: null,
            timeSince: timeSince(block.timestamp),
            timeStamp: block.timestamp,
          };
          scope.transactions.push(txInfo);
          web3.eth
            .getTransaction(tx)
            .then(function(txDetail) {
              txInfo.from = txDetail.from;
              txInfo.to = txDetail.to;
            })
            .catch(console.error);
        });
      }
    },
    /**
     * 트랜잭션 timeSince를 갱신한다.
     */
    updateTimeSince: function() {
      this.transactions.forEach((element) => {
        element.timeSince = timeSince(element.timeStamp);
      });
    },
  },
  mounted: function() {
    this.fetchTxes();
    this.$nextTick(function() {
      window.setInterval(() => {
        this.fetchTxes();
      }, REFRESH_TIMES_OF_TRANSACTIONS);
    });
  },
};
</script>

<style>
.tx-info {
  height: 80px;
  border-bottom: 1px solid #eee;
  margin: 0 3px;
  color: #343a40;
  padding: 10px 0;
}

.tx-info .addresses p {
  margin-bottom: 0;
}
</style>
