<template>
  <div class="my-badge-container">
    <p style="font-size: 18px;">뱃지</p>
    <div class="my-badge-box">
      <div class="badge">
        <!-- <div class="d-flex"> -->
          <el-card>
          <el-tooltip v-for="badge in state.badges" :key="badge"
            effect="dark"
            placement="bottom"
          >
            <template #content> {{badge.content}}</template>
            <img class="img" :src="`http://j5d101.p.ssafy.io/${badge.imgPath}`" alt="">
          </el-tooltip>
          </el-card>
          <!-- <div v-for="badge in state.badges" :key="badge">
            <img :src="`http://j5d101.p.ssafy.io/${badge.imgPath}`" alt="">
          </div> -->
        <!-- </div> -->
      </div>
    </div>
  </div>
</template>
<script>
// import Web3 from 'web3';
import { reactive, computed, onMounted } from "vue";
import { useStore } from "vuex";

export default {
  setup() {
    const store = useStore();

    const state = reactive({
      user: computed(() => store.getters["getUser"]),
      badges: [],
      // address: '',
      // privateKey: '',
      // wallet: computed(() => store.getters['getWallet']),
    });
    onMounted(() => {
      // if (state.user.role == 'user') {
      store.dispatch("badge", state.user.no).then(({ data }) => {
        state.badges = data;
      });
      // }
    });

    // const createWallet = () => {
    //   var web3 = new Web3(new Web3.providers.HttpProvider('http://13.124.163.193:8545/'));
    //   var newAccount = web3.eth.accounts.create();
    //   console.log(newAccount)
    //   state.address = newAccount.address;
    //   state.privateKey = newAccount.privateKey;

    //   let wallet = {
    //     email: state.useremail,
    //     address: state.address,
    //     balance: 0,
    //     berry: 0,
    //     receivecnt: 0
    //   }
    //   store.dispatch('setWallet', wallet)
    //     .then(() => {
    //       store.dispatch('walletDetail', state.useremail)
    //     })
    // }
    return { state };
  },
};
</script>

<style>
.my-badge-container {
  width: 100%;
  height: 20vh;
  border: 1px;
  margin: 4vh 0 4vh 0;
}
.my-badge-box {
  display: flex;
  width: 100%;
  margin: 0;
}
.badge {
  display: flex;
  width: 100%;
  margin: 0;
  /* justify-content: right; */
}
.gold-medal {
  display: inline-block;
  background-color: gold;
  width: 50px;
  height: 50px;
  border-radius: 50px;
  margin-right: 2vw;
}
.silver-medal {
  display: inline-block;
  background-color: silver;
  width: 50px;
  height: 50px;
  border-radius: 50px;
  margin-right: 2vw;
}
.bronze-medal {
  display: inline-block;
  background-color: #cd7f32;
  width: 50px;
  height: 50px;
  border-radius: 50px;
  margin-right: 2vw;
}
.name {
  display: flex;
  align-items: center;
  width: 50%;
}
.img {
  width: 50px;
}
</style>