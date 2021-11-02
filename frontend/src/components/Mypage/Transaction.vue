<template>
  <div class="my-donation-box container" v-if="state.fromData.length != 0">
    <b>{{state.user.name}}님이 보낸 거래 내역</b>
    <el-table
      :data="state.fromData" height="350">
      <el-table-column prop="block_number" label="Block Number"></el-table-column>
      <el-table-column prop="block_hash" label="Block Hash" min-width="300" ></el-table-column>
      <el-table-column prop="gas_price" label="Gas Price"></el-table-column>
      <el-table-column prop="stored_at" label="Date"></el-table-column>
    </el-table>
    </div>
  <div v-else style="text-align:center"><br><b>{{state.user.name}}님이 보낸 거래내역이 존재하지 않습니다.</b></div>
  <br>
  <div class="my-donation-box container" v-if="state.toData.length != 0">
    <b>{{state.user.name}}님이 받은 거래 내역</b>
    <el-table
      :data="state.toData" height="350">
      <el-table-column prop="block_number" label="Block Number"></el-table-column>
      <el-table-column prop="block_hash" label="Block Hash" min-width="300" ></el-table-column>
      <el-table-column prop="gas_price" label="Gas Price"></el-table-column>
      <el-table-column prop="stored_at" label="Date"></el-table-column>
    </el-table>
  </div>
  <div v-else style="text-align:center"><br><b>{{state.user.name}}님이 받은 거래내역이 존재하지 않습니다.</b></div>
</template>
<script>
import { computed, onMounted, reactive } from 'vue'
import { useStore } from 'vuex'


export default ({
  setup() {
    const store = useStore()
    const wallet = computed(() => store.getters['getWallet'])
    const state = reactive({
      fromData: [],
      toData:[],
      user:computed(()=>store.getters['getUser']),
    })
    // console.log(wallet.value);
    onMounted(()=>{
      store.dispatch('Tx/transactionFrom', wallet.value.address).then(({data})=>{
        state.fromData = data
      }),
      store.dispatch('Tx/transactionTo', wallet.value.address).then(({data})=>{
        state.toData = data
      })
    })
    
    return {
      state
    }
  },
})
</script>

<style scoped>
.my-donation-box {
  width: 100%;
  height: 100%;
}
</style>