<template>
  <div class="container">
    <div class="row">
      <div id="login-form" class="col-md-6 mx-auto bg-white">
        <router-link to="/">Escrow | STARMIX</router-link>
        <div class="mt-4">
          <div class="form-group">
            <label for="email">email</label>
            <input
              type="text"
              class="form-control"
              id="email"
              v-model="user.email"
              placeholder="이메일"
            />
          </div>
          <div class="form-group">
            <label for="password">비밀번호</label>
            <input
              type="password"
              class="form-control"
              id="password"
              v-model="user.password"
              placeholder="비밀번호"
            />
          </div>
          <button type="submit" class="btn btn-primary" v-on:click="login">
            로그인
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { login } from "../api/user.js";
import { findByUserId as findWallet } from "../api/wallet.js";

export default {
  data() {
    return {
      user: {
        email: "",
        password: ""
      }
    };
  },
  methods: {
    login() {
      const scope = this;

      login(
        this.user.email,
        this.user.password,
        function(response) {
          scope.$store.commit("setIsSigned", true);
          scope.$store.commit("setUserId", response.data.id);

          findWallet(
            response.data.id,
            function(response) {
              if (response.status == 200) {
                scope.$store.commit("setWalletAddress", response.data.address);
              } else {
                alert("Unexpected status code: " + response.status);
              }
            },
            function(err) {
              if (err.response != 404) {
                console.error(err);
                //alert("지갑 정보를 찾지 못했습니다.");
              }
            }
          );

          scope.$router.push("/");
        },
        function(error) {
          console.error(error);
          alert("유저 이메일 혹은 비밀번호가 일치하지 않습니다.");
        }
      );
    }
  }
};
</script>

<style></style>
