<template>
  <div class="breadcrumb">
    <div class="container">
      <h4>Shop > {{ category }}</h4>
      <ul>
        <li>
          <router-link class="nav-link" to="/shop"
            >전체보기</router-link
          >
        </li>
        <li>
          <router-link class="nav-link" to="/shop/digital"
            >디지털/가전</router-link
          >
        </li>
        <li>
          <router-link class="nav-link" to="/shop/child">유아물품</router-link>
        </li>
        <li>
          <router-link class="nav-link" to="/shop/hobby">게임/취미</router-link>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  props: ["category"]
};
</script>

<style scoped>
ul {
  display: flex;
  list-style: none;
}
a.nav-link {
  color: rgba(255, 255, 255, 0.5);
}

a.nav-link.router-link-exact-active {
  color: white;
}
a:focus {
  font-weight: bold;
  text-decoration: underline;
}
</style>
