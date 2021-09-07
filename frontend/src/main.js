import { createApp } from 'vue'
import App from './App.vue'
import router from "./common/lib/vue-router.js";
import store from "./common/lib/store.js";
import VueAxios from "./common/lib/axios.js";
import axios from "./common/lib/axios.js";



const app = createApp(App);
app.use(VueAxios, axios);
app.use(store);
app.use(router);


app.mount("#app");
