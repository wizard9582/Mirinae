<template>
  <div>
    <h-breadcrumb
      title="상품 등록"
      description="새로운 상품을 등록합니다."
    ></h-breadcrumb>
    <div class="container">
      <div class="row">
        <div class="col-md-8 mx-auto">
          <div class="card">
            <div class="card-body">
              <div class="form-group">
                <label id="name">상품 이름</label>
                <input
                  type="text"
                  class="form-control"
                  id="name"
                  v-model="item.name"
                />
              </div>
              <div class="form-group">
                <label id="name">카테고리</label>
                <select
                  class="form-control"
                  id="category"
                  v-model="item.category"
                >
                  <option value="D">디지털/가전</option>
                  <option value="C">유아물품</option>
                  <option value="H">게임/취미</option>
                </select>
              </div>
              <div class="form-group">
                <label id="price">가격 <b>(CASH)</b></label>
                <input
                  type="number"
                  class="form-control"
                  id="price"
                  v-model="item.price"
                />
              </div>
              <div class="form-group">
                <label id="description">상품 설명</label>
                <textarea
                  class="form-control"
                  id="description"
                  v-model="item.description"
                  placeholder=""
                ></textarea>
              </div>
              <div class="form-group">
                <label id="image-upload">이미지 첨부</label>
                <!-- Upload image input-->
                <input
                  id="upload"
                  type="file"
                  class="form-control"
                  style="height: auto;"
                  @change="onFileChange"
                />

                <!-- Uploaded image area-->
                <div class="image-area mt-4">
                  <img
                    id="imageResult"
                    :src="imgLocalPath"
                    alt=""
                    class="img-fluid rounded shadow-sm mx-auto d-block"
                    style="max-height: 500px;"
                  />
                </div>
              </div>
              <div class="form-group">
                <label id="privateKey">지갑 개인키</label>
                <input
                  id="privateKey"
                  v-model="privateKey"
                  type="text"
                  class="form-control"
                  placeholder="지갑 개인키를 입력해주세요."
                />
              </div>
              <button
                type="button"
                class="btn btn-primary"
                v-on:click="save"
                v-bind:disabled="isCreating"
              >
                {{
                  isCreating
                    ? "상품을 등록하는 중입니다."
                    : "상품을 등록합니다."
                }}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { create as createItem } from "@/api/item.js";
import { registerItem } from "@/utils/itemInventory.js";

export default {
  name: "ItemCreate",
  data() {
    return {
      item: {
        name: "",
        category: "",
        price: null,
        description: "",
        imgName: null,
      },
      privateKey: "",
      userId: this.$store.state.user.id,
      isCreating: false,
    };
  },
  computed: {
    /**
     * public/images에 있는 로컬 이미지를 첨부해서 사용합니다.
     * DB에는 이미지 파일 이름만 저장되고
     * 화면에 보여줄 땐 'public/images/{파일이름}' 경로를 사용합니다.
     */
    imgLocalPath() {
      if (this.item.imgName) {
        return process.env.BASE_URL + "images/" + this.item.imgName;
      }

      return null;
    },
  },
  methods: {
    // 상품을 등록한다.
    save() {
      const vm = this;
      this.isCreating = true; // 아이템 등록 중임을 화면에 표시, 등록이 끝나면 false로 변경
      if (
        this.item.name.length <= 0 ||
        this.item.category.length <= 0 ||
        this.item.price === null ||
        this.item.price <= 0 ||
        this.item.imgName === null ||
        this.item.imgName.length <= 0
      ) {
        alert("입력폼을 모두 입력해주세요.");
        this.isCreating = false;
        return;
      }

      const item = {
        name: this.item.name,
        category: this.item.category,
        explanation: this.item.description,
        seller: this.userId,
        image: this.item.imgName,
      };

      /**
       * TODO: PJTⅢ 과제3 Req.1-1 [상품 등록]
       * DB에 상품 등록 후 반환 받은 id를 이용해서 이더리움에 상품을 등록
       */
    },
    onFileChange(input) {
      var files = input.target.files || input.dataTransfer.files;
      if (!files.length) {
        return;
      }

      this.item.imgName = files[0].name;
    },
  },
};
</script>

<style>
.image-area {
  border: 2px dashed #ced4da;
  padding: 1rem;
  position: relative;
}

.image-area::before {
  content: "업로드 된 이미지";
  font-weight: bold;
  text-transform: uppercase;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 0.8rem;
  z-index: 1;
}

.image-area img {
  z-index: 2;
  position: relative;
}
</style>
