<template>
  <div class="servicelist-box container">
    <el-tabs tab-position="left">
    <el-tab-pane label="진행">
      <div class="service-intro">
        <span>개인 회원만 활동 생성이 가능합니다.</span>
        <span class="service-create animate__animated animate__pulse animate__infinite" v-if="state.user.role == 'user' && state.wallet" @click="clickCreate">생성하러 가기</span>
      </div>
      <div v-if="state.curList.length === 0"><b>진행 중인 봉사활동이 없습니다.</b></div>
      <ServiceItem :list = "state.curList"/>
    </el-tab-pane>
    <el-tab-pane label="마감" v-if="state.fullList.length != 0">
      <ServiceItem :list = "state.fullList"/>
    </el-tab-pane>
    <el-tab-pane label="종료" v-if="state.finList.length != 0">
      <ServiceItem :list = "state.finList"/>
    </el-tab-pane>
  </el-tabs>

    
  </div>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { reactive, computed, onMounted } from "vue";
import ServiceItem from "@/components/Project/ServiceItem.vue"

export default {
  components:{
    ServiceItem
  },
  setup() {
    const store = useStore();
    const router = useRouter();

    const state = reactive({
      user: computed(() => store.getters['getUser']),
      wallet: computed(() => store.getters['getWallet']),
      token: computed(() => store.getters["getToken"]),
      curList: [],
      fullList: [],
      finList:[],
    });

    const clickCreate = () => {
      router.push({ name: "Servicecreate" });
    };

    onMounted(() => {
      store.dispatch("Service/serviceList")
      .then(({ data }) => {
        let tmp = data.slice().reverse();
        tmp.forEach(function(item){
          if(new Date(item.volunteer.date) <= new Date()){
            state.finList.push(item);
          }else if(item.volunteer.num <= item.totalCnt){
            state.fullList.push(item);
          }else{
            state.curList.push(item);
          }
        })
      });
    });

    return { state, clickCreate };
  },
};
</script>

<style>
.el-tabs__item.is-active {
  color:#FF6647 !important; 
}
.el-tabs__item:hover {
  color:#FF6647 !important; 
}
.el-tabs__active-bar{
  background-color: #FF6647 !important
}
.service-list{
  padding-top: 10px;
  margin-bottom: 15px;
  padding-left: 15px;
  padding-right: 15px;
}
.servicelist-box {
  min-height: 85vh;
  margin-top: 3vh;
}
.service-intro {
  margin-bottom: 2%;
  display: flex;
  justify-content: space-between;
  padding-top: 10px;
  padding-left: 20px;
  padding-right: 25px;
}
.service-create {
  font-size: 18px;
}
.service-create:hover {
  cursor: pointer;
  color: #f78065;
}
.servicelist-box .project-summary {
  display: block;
  width: 35vw;
  min-width: 280px;
  height: 22vh;
  min-height: 300px;
  margin: 5% 0;
  box-shadow: 0px 1px 10px 0px gray;
  padding: 5px;
}
.servicelist-box .project-summary:hover {
  cursor: pointer;
}
/* .servicelist-box .project-summary #summary-img-box {
  height: 70%;
} */
.servicelist-box .project-summary #summary-text-box {
  margin: 3% 0;
  /* height: 25%; */
  padding: 0 2%;
  /* border: 1px solid #f78065; */
}
/* .servicelist-box .project-summary #summary-img-box #summary-img {
  width: 100%;
  height: 100%;
} */
#service-info {
  height: 50%;
}
#service-user {
  font-size: 16px;
  margin-top: 2%;
}
#service-name {
  font-size: 20px;
  font-weight: 500;
  margin-bottom: 2%;
}
#service-content {
  padding: 0 2%;
  margin-top: 18%;
}
#service-content p {
  font-weight: 500;
  font-size: 18px;
  margin-bottom: 0 !important;
}
</style>