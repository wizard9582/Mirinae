<template>
  <div>
    <h-breadcrumb
      title="Explorer > Transactions"
    ></h-breadcrumb>
    <div class="container">
      <div class="row">
        <div id="transactions" class="col-md-8 mx-auto">
          <div class="card shadow-sm">
            <div class="card-header">Transactions</div>
            <div class="card-body">
              <tx-list countToShow="10"></tx-list>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TxList from './TxList.vue';

export default {
  components: {
    TxList
  },
};
</script>

<style></style>
