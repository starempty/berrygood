<template>
  <div class="my-service-box container">
    <div v-if="state.totalCnt != 0">
    <b>봉사 횟수</b>
    <h2><b>{{state.totalCnt}}</b> 회</h2>
    </div>
    <div v-else style="text-align:center;">
      <p style="font-size:18px; font-weight:500;">봉사 활동은 사용자끼리 자유롭게 이용할 수 있습니다.</p>
      <el-button @click="moveToService" id="move-service">봉사활동 하러 가기</el-button>
    </div>
    <div class="activity-container">
      <div class="activity-box row row-cols-3 g-3">
        <div class="service-box col" v-for="service in state.services" :key="service">
          <div id="service" @click="clickService(service.volunteer.no)">
            <div class="d-flex flex-column">
              <span id="service-title">{{ service.volunteer.name }}</span>
              <span id="service-type">{{ service.volunteer.type }}</span>
            </div>
              <span id="service-date">{{ service.volunteer.date.slice(0, 10) }}</span>
          </div>
        </div>
      </div>
      <div v-if="state.totalCnt > 6" id="service-more" class="mt-4">
        <div v-if="state.more === false" @click="clickMore">
          <i class="fas fa-caret-down"></i>
        </div>
        <div v-if="state.more === true" @click="clickShort">
          <i class="fas fa-caret-up"></i>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { computed, reactive, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from "vue-router";

export default {
  setup() {
    const store = useStore()
    const router = useRouter();
    const state = reactive({
      services:{},
      user: computed(()=>store.getters['getUser']),
      totalCnt: 0,
      more: false,
    })
    const moveToService = () => {
      router.push({ name: "Servicelist" });
    }
    const clickMore = () => {
      store.dispatch('Service/findByEmail', state.user.email)
        .then(({ data }) => {
          state.services = data
          state.more = true
        })
    }
    const clickShort = () => {
      store.dispatch('Service/findByEmail', state.user.email)
        .then(({ data }) => {
          state.services = data.slice(0,6)
          state.more = false
        })
    }
    const clickService = (serviceNo) => {
      router.push({ name: 'Servicedetail', params: { serviceNo: serviceNo }})
    }
    onMounted(()=>{
      store.dispatch('Service/findByEmail', state.user.email)
      .then(({ data })=>{
        state.services = data.slice(0, 6)
        state.totalCnt = data.length
        // console.log(state.services)
      })
    })

    return{ state, moveToService, clickMore, clickShort, clickService } 
  },
}
</script>

<style>
.my-service-box {
  width: 100%;
  height: 100%;
  border: 1px;
}
.activity-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.activity-box {
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.activity {
  display: block;
  width: 50%;
  height: 100%;
  margin: 0 2%;
}
.activity:hover {
  cursor: pointer;
}
.activity-status-container {
  display: flex;
  justify-content: center;
}
.activity-box {
  width: 100%;
}

.activity-box .service-box #service {
  /* border: 1px solid black; */
  height: 120px;
  width: 100%;
  padding: 5%;
  box-shadow: 0px 1px 5px 0px #f78065;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.activity-box .service-box #service:hover {
  transform: scale(1.1);
  cursor: pointer;
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
#service-more {
  font-size: 30px;
}
#service-more:hover {
  transform: scale(1.3);
  color: #f78065;
  cursor: pointer;
}
#move-service {
  color: white;
  background-color: #f78065;
  border: #f78065;
}
#move-service:hover {
  color: white;
  background-color: #f78065;
  border: #f78065;
  opacity: 0.8;
}
</style>