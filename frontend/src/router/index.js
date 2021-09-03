import Vue from "vue";
import VueRouter from "vue-router";
import Signup from "@/views/Signup.vue";
import Home from "@/views/Home.vue";
import Login from "@/views/Login.vue";
import store from "@/store";
import Shop from "@/views/Shop.vue";
import MyPage from "@/views/MyPage.vue";
import Item from "@/views/Item.vue";
import Explorer from "@/views/Explorer.vue";
import Escrow from "@/views/Escrow.vue";

Vue.use(VueRouter);

/**
 * 아래의 router를 변경하여 구현할 수 있습니다.
 */
const routes = [
  {
    path: "/",
    name: "home",
    component: Home,
  },
  {
    name: "login",
    path: "/login",
    component: Login,
  },
  {
    path: "/register",
    name: "signup",
    component: Signup,
  },
  {
    path: "/logout",
    name: "logout",
    beforeEnter(to, from, next) {
      store.commit("logout");
      alert("로그아웃 되었습니다.");
      next("/");
    },
  },
  {
    name: "shop",
    path: "/shop",
    component: Shop,
    children: [
      {
        path: "",
        component: () => import("@/components/shop/All.vue"),
      },
      {
        path: "digital",
        component: () => import("@/components/shop/Digital.vue"),
      },
      {
        path: "child",
        component: () => import("@/components/shop/Child.vue"),
      },
      {
        path: "hobby",
        component: () => import("@/components/shop/Hobby.vue"),
      },
    ],
    redirect: () => {
      return "/shop";
    },
  },
  {
    name: "mypage",
    path: "/mypage",
    component: MyPage,
    children: [
      {
        name: "mypage.wallet.create",
        path: "wallet_create",
        component: () => import("../components/mypage/WalletCreate.vue"),
      },
      {
        name: "mypage.wallet.info",
        path: "wallet_info",
        component: () => import("../components/mypage/WalletInfo.vue"),
      },
      {
        name: "mypage.items",
        path: "/mypage/items",
        component: () => import("../components/mypage/MyItems.vue"),
      },
      {
        name: "mypage.password",
        path: "/mypage/password",
        component: () => import("../components/mypage/Password.vue"),
      },
    ],
    redirect: () => {
      return "/mypage/items";
    },
  },
  {
    name: "item",
    path: "/item",
    component: Item,
    children: [
      {
        name: "item.create",
        path: "create",
        component: () => import("../components/item/ItemCreate.vue"),
      },
      {
        name: "item.detail",
        path: "detail/:id",
        component: () => import("../components/item/ItemDetail.vue"),
      },
      {
        name: "item.purchase",
        path: "purchase/:id",
        component: () => import("../components/item/ItemPurchase.vue"),
      },
    ],
  },
  {
    name: "escrow",
    path: "/escrow",
    component: Escrow,
    children: [
      {
        name: "escrow.purchase.detail",
        path: "purchase/detail",
        component: () => import("@/components/escrow/PurchaseTxDetail.vue"),
      },
      {
        name: "escrow.sale.detail",
        path: "sale/detail",
        component: () => import("@/components/escrow/SaleTxDetail.vue"),
      },
      {
        name: "escrow.history",
        path: "history/:id",
        component: () => import("@/components/escrow/EscrowHistory.vue")
      }
    ],
  },
  {
    name: "explorer",
    path: "/explorer",
    component: Explorer,
    children: [
      {
        name: "explorer.dashboard",
        path: "dashboard",
        component: () => import("../components/explorer/Dashboard.vue"),
      },
      {
        name: "explorer.block",
        path: "blocks",
        component: () => import("../components/explorer/BlockListView.vue"),
      },
      {
        name: "explorer.block.detail",
        path: "block/:blockNumber",
        component: () => import("../components/explorer/BlockDetail.vue"),
      },
      {
        name: "explorer.tx",
        path: "txes",
        component: () => import("../components/explorer/TxListView.vue"),
      },
      {
        name: "explorer.tx.detail",
        path: "tx/:hash",
        component: () => import("../components/explorer/TxDetail.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  let isSigned = store.state.isSigned;
  let isAvailableToGuest =
    ["/", "/login", "/register"].includes(to.path) ||
    to.path.startsWith("/explorer");

  // 로그인도 하지 않았고 게스트에게 허용된 주소가 아니라면 로그인 화면으로 이동한다.
  if (!isSigned && !isAvailableToGuest) {
    alert("로그인을 하신 뒤에 사용이 가능합니다.");
    next("/login");
  } else {
    next();
  }
});

export default router;
