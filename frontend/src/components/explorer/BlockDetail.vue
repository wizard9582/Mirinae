<template>
  <div>
    <h-breadcrumb
      title="Block Explorer"
      description="블록체인에서 생성된 블록내역을 보여줍니다."
    ></h-breadcrumb>
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <div class="card shadow-sm">
            <div class="card-header">
              블록 <strong># {{ block.number }}</strong>
            </div>
            <table class="table">
              <tbody>
                <tr>
                  <th width="300">블록 height</th>
                  <td>{{ block.number }}</td>
                </tr>
                <tr>
                  <th>블록 해시</th>
                  <td>{{ block.hash }}</td>
                </tr>
                <tr>
                  <th>블록 생성 시간</th>
                  <td>{{ block.timestamp }}</td>
                </tr>
                <tr>
                  <th>Miner</th>
                  <td>
                    <a href="#">{{ block.miner }}</a>
                  </td>
                </tr>
                <tr>
                  <th>Nonce</th>
                  <td>{{ block.nonce }}</td>
                </tr>
                <tr>
                  <th>문제 난이도 (Difficulty)</th>
                  <td>{{ block.difficulty }}</td>
                </tr>
                <tr>
                  <th>블록 크기</th>
                  <td>{{ block.size }} bytes</td>
                </tr>
                <tr>
                  <th>gasLimit</th>
                  <td>{{ block.gasLimit }}</td>
                </tr>
                <tr>
                  <th>gasUsed</th>
                  <td>{{ block.gasUsed }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { createWeb3 } from "@/utils/itemInventory.js";

export default {
  data() {
    return {
      isValid: true,
      block: {
        number: 0
      }
    };
  },
  mounted: function() {
    var scope = this;
    var blockNumber = this.$route.params.blockNumber;
    const web3 = createWeb3();
    if (blockNumber) {
      web3.eth.getBlock(blockNumber).then(function(block) {
        scope.block.number = block.number;
        scope.block.hash = block.hash;
        scope.block.nonce = block.nonce;
        scope.block.miner = block.miner;
        scope.block.difficulty = Number(block.difficulty).toLocaleString();
        scope.block.size = Number(block.size).toLocaleString();
        scope.block.gasLimit = Number(block.gasLimit).toLocaleString();
        scope.block.gasUsed = Number(block.gasUsed).toLocaleString();
        scope.block.timestamp = new Date(block.timestamp * 1000);
      });
    } else {
      this.isValid = false;
    }
  }
};
</script>

<style></style>
