<template>
  <div>
    <div class="row" v-if="!blocks || blocks.length == 0">
      <div class="col-md-8 mx-auto">
        <div class="alert alert-warning">
          No block.
        </div>
      </div>
    </div>
    <div v-else class="row block-info" v-for="item in blocks" :key="item.id">
      <div class="col-md-2">BK</div>
      <div class="col-md-4">
        <router-link
          :to="{
            name: 'explorer.block.detail',
            params: { blockNumber: item.number },
          }"
          class="block-number"
          >{{ item.number }}</router-link
        >
        <p class="block-timestamp">{{ item.timestamp }}</p>
      </div>
      <div class="col-md-6 text-right">
        <p class="block-num-transactions">{{ item.txCount }} Txes</p>
      </div>
    </div>
  </div>
</template>

<script>
import {
  fetchLatestBlock,
  fetchBlocks,
  timeSince,
  REFRESH_TIMES_OF_BLOCKS,
} from '../../utils/blockchainProvider.js';

export default {
  props: ['countToShow'],
  data() {
    return {
      lastReadBlock: 0,
      blocks: [],
    };
  },
  methods: {
    fetchBlocks: function() {
      /**
       * 1. 블록의 목록을 블록체인으로부터 직접 가져옵니다.
       * 2. 불록의 목록을 서버로부터 요청하여 가져옵니다.
       */
      var scope = this;

      // 최근 countToShow 개수 만큼의 블록 정보를 가져와서 계속 업데이트 한다.
      fetchLatestBlock().then(function(latestBlock) {
        if (scope.lastReadBlock == 0) {
          scope.lastReadBlock =
            latestBlock - scope.countToShow <= 0
              ? 0
              : latestBlock - scope.countToShow;
        }

        var from = scope.lastReadBlock;

        if (from < latestBlock) {
          fetchBlocks(from, latestBlock, function(block) {
            // 배열의 첫번째 요소에 데이터를 저장한다.
            scope.blocks.unshift({
              number: block.number,
              timestamp: timeSince(block.timestamp),
              txCount: block.transactions.length,
            });

            if (scope.blocks.length > scope.countToShow) {
              scope.blocks.pop();
            }
          });

          scope.lastReadBlock = latestBlock;
          // latestBlock이 변경되었음을 알리는 이벤트 발생
          scope.$emit('updated', latestBlock);
        }
      });
    },
  },
  mounted: function() {
    this.fetchBlocks();

    this.$nextTick(function() {
      window.setInterval(() => {
        this.fetchBlocks();
      }, REFRESH_TIMES_OF_BLOCKS);
    });
  },
};
</script>

<style>
.block-info {
  height: 80px;
  border-bottom: 1px solid #eee;
  margin: 0 3px;
  color: #343a40;
  padding: 10px 0;
}
</style>
