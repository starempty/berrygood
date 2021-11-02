<template>
  <div class="my-blood-box">
    <!-- <div class="license" v-for="blood in state.bloodInput" :key="blood">
      <div id="blood-img">
        <div class="license-top">
          <span>헌혈 증서</span>
          <span>증서 번호 {{ blood.num }}</span>
        </div>
      </div>
      <div class="license-bottom">
        <div class="license-item">
          <span>성명 : {{ state.user.name  }}</span>
          <span>헌혈 종류 : {{ blood.kind }}</span>
        </div>
        <div class="license-item">
          <span>생년월일 : {{ state.user.birthday  }}</span>
          <span style="margin:0 117px 0 0">성별 : {{ state.user.gender }}</span>
        </div>
        <div class="license-item"><span>헌혈 일자 : {{ blood.date }}</span></div>
      </div>
    </div> -->
    <div v-if="!state.wallet" style="text-align:center;">
      <h5>헌혈증을 등록하기 위하여 지갑 계정이 필요합니다.</h5>
      </div>
    <div v-else-if="state.bloodInput.length === 0" style="text-align:center;">
      <p style="font-size:18px; font-weight:500;">디지털 헌혈증을 등록해보세요!</p>
      <el-button @click="moveToBloodInput" id="move-blood">헌혈증 등록하기</el-button>
    </div>
    <div class="d-flex justify-content-center" v-else>
        <el-carousel :interval="4000" type="card" class="blood-carousel">
          <el-carousel-item v-for="blood in state.bloodInput" :key="blood">
            <div class="license">
              <div id="blood-img">
                <div class="license-top">
                  <span id="license-title">헌혈증서</span>
                  <span id="license-num">{{ blood.num }}</span>
                </div>
              </div>
              <div class="license-bottom">
                <div class="d-flex" id="license-content">
                  <div class="d-flex flex-column">
                    <span id="license-name">성명 : {{ state.user.name  }}</span>
                    <span id="license-birth">생년월일 : {{ state.user.birthday  }}</span>
                  </div>
                  <div class="d-flex flex-column ms-4">
                    <span id="license-type">헌혈 종류 : {{ blood.kind }}</span>
                    <span id="license-gender" v-if="state.user.gender === 'm'">성별 : 남자</span>
                    <span id="license-gender" v-if="state.user.gender === 'f'">성별 : 남자</span>
                  </div>
                </div>
                <span id="license-date">헌혈 일자 : {{ blood.date }}</span>
              </div>
            </div>
          </el-carousel-item>
        </el-carousel>
      </div>
    <!-- <div class="license-new">
      <div id="blood-img" @click="moveToBloodInput">
        <span class="btn-theme">+</span>
      </div>
    </div> -->
  </div>
</template>

<script>
import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  setup() {
    const store = useStore()
    const router = useRouter()

    const moveToBloodInput = () => {
      router.push({ name : 'Bloodinput' })
    }
    
    const state = reactive ({
      bloodInput: [],
      dialogVisible: false,
      user: computed(() => store.getters['getUser']),
      wallet: computed(() => store.getters['getWallet']),
      startYear: 1900,
      yearCount: 122,
      year: '',
      month: '',
      day: '',
      condition: '',
      options: [ '전혈 320mL', '전혈 400mL', '혈소판성분헌혈', '혈장성분헌혈', '혈소판혈장성분헌혈' ],
    })
    const clickRegister = () => {
      store.dispatch('Blood/blood', { 
        email: state.user.email, 
        num: state.bloodInput.num, 
        kind: state.bloodInput.kind, 
        date: state.bloodInput.date 
      })
      state.dialogVisible=false
      window.location.reload()
    }

    const dateChange = () => {     
      state.bloodInput.date = `${state.year}-${('0' + state.month).slice(-2)}-${('0' + state.day).slice(-2)}`
    }
    
    onMounted(() => {
      store.dispatch('Blood/bloodInfo', state.user.email)
      .then(({ data }) => {
        state.bloodInput = data
      })
    })
    return { state, clickRegister, dateChange, moveToBloodInput }
  },
}
</script>

<style scoped>
hr {
  border: 1px green; 
  width: 100%;
}
.my-blood-box {
  width: 100%;
  height: 100%;
  border: 1px;
}
.blood-carousel {
  width: 90%;

}
.license {
  display: block;
  border-radius: 15px;
  background-color: white;
  position: static;
  width: 300px;
  height: 200px;
  z-index: auto;
  box-shadow: 0px 1px 10px 0px gray;
}
.license:hover {
  display: block;
  transform: scale(1.1);
  /* margin: 0 3% 0 5%; */
  z-index: auto;
  box-shadow: 0px 1px 10px 0px gray;
  cursor: pointer;
}
.license-top {
  color: white;
  border-radius: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  padding: 5%;
}
.license-top #license-title {
  font-size: 24px;
  font-weight: 500;
}
.license-top #license-num {
  font-size: 16px;
}
.license-bottom {
  padding: 9% 5%;
}
#blood-img {
  border-radius: 15px 15px 0 0;
  height:28%;
  border: 1px solid #273cf7de;
  background-color: #273cf7de;
}
.el-carousel__item {
  display: flex;
  align-items: center;
  justify-content: center;
}
#license-name, #license-type {
  margin-bottom: 8px;
}
#license-content {
  margin-bottom: 8px;
}
#move-blood {
  color: white;
  background-color: #f78065;
  border: #f78065;
}
#move-blood:hover {
  color: white;
  background-color: #f78065;
  border: #f78065;
  opacity: 0.8;
}
</style>