import { createApp } from 'vue'
import App from './App.vue'
import router from "./common/lib/vue-router.js";
import store from "./common/lib/store.js";
import VueAxios from "./common/lib/axios.js";
import axios from "./common/lib/axios.js";
import './common/css/main.css'

window.Kakao.init('1696080c62fac01538f3f85a146d95fa');

const app = createApp(App);
app.use(VueAxios, axios);
app.use(store);
app.use(router);

app.mount("#app");
