<template>
  <div class="my-fundinglist-box container">
    <br>
    <b>총 모금 베리</b>
    <h1><b>{{state.totalAmount}}</b> B </h1>
    <br>
    <b>펀딩 현황</b>
    <!-- 펀딩현황 목록 v-for할 때 div 블록 하나만!!-->
    <div class="activity-box row row-cols-3 g-3">
      <div class="service-box col" v-for="item in state.fundingList" :key="item">
        <div id="service" @click="setTableData(item.no, item.name)">
          <div class="d-flex flex-column">
            <span id="service-title">{{ item.name }}</span>
          </div>
            <span id="service-date">목표 베리: {{ item.amount }}B</span>
            <span id="service-date">마감 일자: {{ item.due }}</span>
        </div>
      </div>
    </div>
    <p style="font-size: 25px; font-weight: 600; color:#f78065" class="text-center">{{state.curDonate}}</p>
    <el-table v-if="state.tableData.length != 0" :data="state.tableData" height="500">
      <el-table-column prop="user" label="참여자"></el-table-column>
      <el-table-column prop="message" label="응원의 메시지"></el-table-column>
      <el-table-column prop="amount" label="펀딩 금액(B)"></el-table-column>
      <el-table-column prop="joinDate" label="펀딩일자"></el-table-column>

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
      fundingList:[],
      totalAmount:0,
      curDonate:'',
    })
    onMounted(()=>{
      store.dispatch('Funding/findByCompemail', state.user.email).then(({data})=>{
        data.forEach(function(item){
          state.fundingList.push({
            no: item.no,
            name: item.name,
            amount: item.amount,
            due: item.due
          })
        })
      })
    })
    const setTableData = (no, name) => {
      state.curDonate = name;
      state.tableData = [];
      state.totalAmount = 0;
      store.dispatch('Funding/findByNo', no).then(({data})=>{
        data.forEach(function(item){
          state.tableData.push({
            amount: item.amount,
            joinDate: item.joinDate,
            message: item.message,
            user: item.user.nickname,
          })
          state.totalAmount += item.amount
        })
      })
    }
    return{
      state,
      setTableData
    }
  }
}
</script>
<style scoped>
hr {
  border: 1px green; 
  width: 100%;
}
.my-fundinglist-box {
  width: 100%;
  height: 100%;
  border: 1px;
  margin-bottom: 7vh;
}
.my-fundinglist-box .type {
  width: 30%;
  height: 3vh;
  float: left;
  border: 1px
}
.funding-img-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-bottom: 5vh;
}
.funding-img-box {
  height: 18vh;
  width: 90%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.funding-img-box #funding-previous {
  display: none;
  font-size: 3vh;
  color: #f78065;
}
.funding-img-box #funding-next {
  font-size: 3vh;
  color: #f78065;
  opacity: 0.7;
}
.funding-img-box #funding-next:hover {
  font-size: 4vh;
  opacity: 1;
  cursor: pointer;
}
.funding-img {
  display: block;
  width: 20%;
  height: 100%;
  margin: 0 2%;
}
.funding-img:hover {
  cursor: pointer;
}
.funding-status-container {
  display: flex;
  justify-content: center;
}
.funding-status {
  width: 100%;
  height: 3vh;
  float: left;
  border: 1px;
  display: flex;
  justify-content: center;
}
.activity-box {
  height: 100%;
  width: 100%;
  /* display: flex; */
  justify-content: space-around;
  align-items: center;
  margin-bottom: 50px;
}
.service-box #service {
  /* border: 1px solid black; */
  height: 120px;
  width: 100%;
  padding: 5%;
  box-shadow: 0px 1px 5px 0px #f78065;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
#service #service-title {
  font-size: 18px;
  font-weight: 600;
  line-height: 120%;
  word-break: keep-all;
}
#service #service-type {
  font-size: 14px;
  font-weight: 500;
  color: #858585;
  line-height: 110%;
}
#service #service-date {
  line-height: 110%;
  font-weight: 500;
}
</style>