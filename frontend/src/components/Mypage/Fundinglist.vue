<template>
  <div class="my-donationlist-box container">
    <br>

    <b>펀딩금액</b>
    <h1><b>{{state.totalAmount}}</b> B </h1>
    <br>
    <b>펀딩 현황</b>
    <!-- 펀딩현황 목록 v-for할 때 div 블록 하나만!!-->
    <el-table
      :data="state.tableData" height="300">
      <el-table-column prop="compName" label="기업명"></el-table-column>
      <el-table-column prop="name" label="펀딩명"></el-table-column>
      <el-table-column prop="amount" label="펀딩 금액(B)"></el-table-column>
      <el-table-column prop="time" label="펀딩 일자"></el-table-column>
    </el-table>
  </div>
</template>
<script>
import {computed, onMounted, reactive} from 'vue'
import {useStore} from 'vuex'
export default {
  setup(){
    const store = useStore();
    const state = reactive({
      user: computed(() => store.getters['getUser']),
      tableData:[],
      totalAmount:0,
    })
    onMounted(()=>{
      store.dispatch('Funding/findByEmail', state.user.email).then(({data})=>{
        data.forEach(function(item){
          state.tableData.push({
            compName: item.funding.comp.name,
            name: item.funding.name,
            amount: item.amount,
            time: item.joinDate.substring(0, 10),
          })
          state.totalAmount += item.amount;
        })
      })
    })
    return{
      state
    }
  }
}
</script>
<style scoped>
hr {
  border: 1px green; 
  width: 100%;
}
.my-donationlist-box {
  width: 100%;
  height: 100%;
  border: 1px;
  margin-bottom: 7vh;
}
.my-donationlist-box .type {
  width: 30%;
  height: 3vh;
  float: left;
  border: 1px
}
.donation-img-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-bottom: 5vh;
}
.donation-img-box {
  height: 18vh;
  width: 90%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.donation-img-box #donate-previous {
  display: none;
  font-size: 3vh;
  color: #f78065;
}
.donation-img-box #donate-next {
  font-size: 3vh;
  color: #f78065;
  opacity: 0.7;
}
.donation-img-box #donate-next:hover {
  font-size: 4vh;
  opacity: 1;
  cursor: pointer;
}
.donation-img {
  display: block;
  width: 20%;
  height: 100%;
  margin: 0 2%;
}
.donation-img:hover {
  cursor: pointer;
}
.donate-status-container {
  display: flex;
  justify-content: center;
}
.donate-status {
  width: 100%;
  height: 3vh;
  float: left;
  border: 1px;
  display: flex;
  justify-content: center;
}
</style>