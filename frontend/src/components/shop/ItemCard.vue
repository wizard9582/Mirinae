<template>
  <a @click.prevent="$emit('clicked')" href="" class="card item-card">
    <div class="card-body">
      <img :src="imgPath" />
      <p>{{ item.name }}</p>
      <h5>{{ item.price }} CASH</h5>
    </div>
  </a>
</template>

<script>
export default {
  props: ['item'],
  computed: {
    imgPath() {
      return process.env.BASE_URL + 'images/' + this.item.image;
    }
  }
};
</script>

<style>
.item-card img {
  height: 180px;
  width: 180px;
}

.item-card:hover {
  text-decoration: underline !important;
  border: 1.2px solid #0070bc;
}
</style>
