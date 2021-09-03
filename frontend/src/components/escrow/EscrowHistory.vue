<template>
  <div>
    <h-breadcrumb
      title="거래 이력"
      description="거래 진행 이력을 볼 수 있습니다."
    ></h-breadcrumb>
    <div class="container">
      <div class="row">
        <div class="col mx-auto">
          <div class="card">
            <div class="card-header">거래 번호: {{ purchaseId }}</div>
            <div class="card-body" style="z-index: 0;">
              <step-flow :history="history"></step-flow>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import StepFlow from "@/components/common/StepFlow";
import { getHistory } from "@/api/purchase.js";
import { getRecordNumber, getRecord } from "@/utils/purchaseRecord.js";

export default {
  name: "EscrowHistory",
  components: { StepFlow },
  data() {
    return {
      history: [],
      purchaseId: null,
      escrowContractAddress: null,
      state: [
        "Purchased",
        "Paid",
        "Sent",
        "Complete",
        "Cancelled",
        "Refunded",
        "End"
      ]
    };
  },
  methods: {
    /**
     * TODO: PJTⅢ 과제3 Req.1-12 [이력 조회 요청]
     */
    getHistoryByAPI() {
    },
    /**
     * TODO: PJTⅢ 과제3 Req.1-11 [직접 이력 조회]
     */
    getHistoryByContract() {
    },
    /**
     * 스마트 컨트랙트에서 가져온 timestamp를 yyyy-MM-dd hh:mm:ss 문자열로 변환
     */
    formatDate(timestamp) {
      const date = new Date(timestamp * 1000);
      var year = date.getFullYear();
      var month = date.getMonth();
      var day = date.getDay();
      var hours = date.getHours();
      var minutes = "0" + date.getMinutes();
      var seconds = "0" + date.getSeconds();
      return (
        year +
        "-" +
        month +
        "-" +
        day +
        " " +
        hours +
        ":" +
        minutes.substr(-2) +
        ":" +
        seconds.substr(-2)
      );
    }
  },
  created() {
    this.purchaseId = this.$route.params.id;
    this.escrowContractAddress = this.$route.params.escrowContractAddress;
    // TODO: Req.1-11 직접 이력 조회 과제 수행 후 주석 제거하여 확인
    // this.getHistoryByContract();
    // TODO: Req.1-12 이력 조회 요청 과제 수행 후 주석 제거하여 확인
    // this.getHistoryByAPI();
  }
};
</script>

<style></style>
