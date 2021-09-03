<template>
  <div>
    <h-breadcrumb
      title="비밀번호 수정"
      description="비밀번호를 수정합니다."
    ></h-breadcrumb>
    <div class="container">
      <my-page-nav></my-page-nav>
      <div class="row">
        <div class="col-md-6 mx-auto mt-5">
          <div class="card">
            <div class="card-header">비밀번호 수정</div>
            <div class="card-body">
              <div class="form-group">
                <label id="name">기존 비밀번호</label>
                <input
                  id="prev-password"
                  type="text"
                  class="form-control"
                  placeholder=""
                  v-model="prevPwd"
                />
              </div>
              <div class="form-group">
                <label id="password">새 비밀번호</label>
                <input
                  id="password"
                  type="password"
                  class="form-control"
                  placeholder=""
                  v-model="newPwd"
                />
              </div>
              <div class="form-group">
                <label id="password">새 비밀번호 확인</label>
                <input
                  id="password"
                  type="password"
                  class="form-control"
                  placeholder=""
                  v-model="confirmNew"
                />
              </div>
              <div class="row">
                <div class="col text-right">
                  <button class="btn btn-sm btn-primary" v-on:click="update">
                    저장
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { findById, update } from "../../api/user.js";
import MyPageNav from "./MyPageNav.vue";

export default {
  components: {
    MyPageNav
  },
  data() {
    return {
      prevPwd: "",
      newPwd: "",
      confirmNew: "",
      user: {
        id: this.$store.state.user.id,
        email: ""
      }
    };
  },
  methods: {
    update: function() {
      const scope = this;

      if (
        this.prevPwd.length === 0 ||
        this.newPwd.length === 0 ||
        this.confirmNew.length === 0
      ) {
        alert("입력란을 모두 채워주세요.");
      } else if (this.newPwd !== this.confirmNew) {
        alert("새 비밀번호가 일치하지 않습니다.");
      } else {
        findById(this.user.id, function(response) {
          scope.user.email = response.data["email"];
          if (scope.prevPwd !== response.data["password"]) {
            alert("기존 비밀번호와 일치하지 않습니다.");
          } else {
            update(
              {
                id: scope.user.id,
                email: scope.user.email,
                password: scope.newPwd
              },
              function() {
                alert("비밀번호가 변경되었습니다.");
                scope.user.prevPwd = "";
                scope.user.newPwd = "";
                scope.user.confirmNew = "";
              },
              function() {
                alert("비밀번호 변경에 실패했습니다.");
              }
            );
          }
        });
      }
    },
  },
  mounted: function() {}
};
</script>

<style></style>
