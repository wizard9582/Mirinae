<template>
  <div class="container">
    <div class="row">
      <div id="register-form" class="col-md-6 mx-auto bg-white">
        <router-link to="/">Escrow | STARMIX</router-link>
        <div class="mt-4">
          <div class="form-group">
            <label for="email">이메일</label>
            <input
              type="text"
              class="form-control"
              id="email"
              v-model="user.email"
              placeholder="이메일"
            />
          </div>
          <div class="form-group">
            <label for="name">이름</label>
            <input
              type="text"
              class="form-control"
              id="name"
              v-model="user.name"
              placeholder="이름"
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
          <div class="form-group">
            <label for="password-confirm">비밀번호 확인</label>
            <input
              type="password"
              class="form-control"
              id="password-confirm"
              v-model="user.passwordConfirm"
              placeholder="비밀번호 확인"
            />
          </div>
          <button type="submit" class="btn btn-primary" v-on:click="register">
            회원가입
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { signup } from "../api/user.js";

export default {
  data() {
    return {
      user: {
        email: "",
        name: "",
        password: "",
        passwordConfirm: ""
      }
    };
  },
  methods: {
    register() {
      var vm = this;

      if (this.user.password === this.user.passwordConfirm) {
        signup(
          this.user.email,
          this.user.name,
          this.user.password,
          function() {
            alert("회원가입이 완료되었습니다.");
            vm.$router.push("/");
          },
          function(error) {
            console.error(error);
          }
        );
      } else {
        alert("비밀번호가 일치하지 않습니다.");
      }
    }
  }
};
</script>

<style></style>
