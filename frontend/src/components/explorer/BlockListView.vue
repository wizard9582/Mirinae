<template>
  <div>
    <h-breadcrumb
      title="Explorer > Blocks"
    ></h-breadcrumb>
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <div id="blocks" class="col-md-8 mx-auto">
            <div class="card shadow-sm">
              <div class="card-header">Blocks</div>
              <div class="card-body">
                <block-list 
                  countToShow="10"
                >
                </block-list>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BlockList from "./BlockList.vue";

export default {
  components: {
    BlockList
  },
};
</script>

<style></style>
