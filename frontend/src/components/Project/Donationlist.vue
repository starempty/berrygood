<template>
  <div class="donationlist-box container">
    <el-tabs tab-position="left">
    <el-tab-pane label="진행">
      <div class="donation-intro">
        <span>기업 및 단체 회원만 기부 생성이 가능합니다.</span>
        <span class="donation-create animate__animated animate__pulse animate__infinite" v-if="state.user.role == 'company' && state.wallet" @click="clickCreate">생성하러 가기</span>
      </div>
      <div v-if="state.curList.length === 0"><b>진행 중인 봉사활동이 없습니다.</b></div>
      <DonationItem :list = "state.curList"/>
    </el-tab-pane>
    <el-tab-pane label="종료" v-if="state.finList.length != 0">
      <DonationItem :list = "state.finList"/>
    </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { onMounted, computed, reactive } from 'vue'
import { useRouter } from 'vue-router' 
import { useStore } from 'vuex'
import DonationItem from "@/components/Project/DonationItem.vue"

export default {
  components:{
    DonationItem
  },
  setup() {
    const router = useRouter()
    const store = useStore()

    const state = reactive ({
      user: computed(() => store.getters['getUser']),
      wallet: computed(() => store.getters['getWallet']),
      curList: [],
      finList: [],
    })
    const clickCreate = () => {
      router.push({ name: "Donationcreate" })
    }
    
    onMounted(() => {
      store.dispatch('Donation/donateList')
      .then(({ data }) => {
        data.forEach(function(item){
          if(new Date(item.donate.due) <= new Date()){
            state.finList.push(item);
          }else{
            state.curList.push(item);
          }
        })
      })
    })
    return { state, clickCreate }
  },
}
</script>

<style>
.donationlist-box {
  min-height: 85vh;
  margin-top: 3vh;
}
.donation-intro {
  margin-bottom: 2%;
  display: flex;
  justify-content: space-between;
  padding-top: 10px;
  padding-left: 20px;
  padding-right: 25px;
}
.donation-create {
  font-size: 18px;
}
.donation-create:hover {
  cursor: pointer;
  color: #f78065;
}
.donationlist-box .project-summary {
  display: block;
  width: 35vw;
  min-width: 280px;
  height: 22vh;
  min-height: 300px;
  margin: 5% 0;
  box-shadow: 0px 1px 10px 0px gray;
  padding: 5px;
}
.donationlist-box .project-summary:hover {
  cursor: pointer;
}
/* .donationlist-box .project-summary #summary-img-box {
  height: 70%;
} */
/* .donationlist-box .project-summary #summary-img-box #summary-img {
  width: 100%;
  height: 100%;
} */
#donation-info {
  display: flex;
  flex-direction: column;
  height: 30%;
}
#info-box {
  display: flex;
  flex-direction: column;
  height: 100%;
}
#donation-comp {
  font-size: 16px;
  /* margin-top: 2%; */
}
#donation-name {
  font-size: 20px;
  font-weight: 500;
  margin-bottom: 2%;
}
#donation-content {
  padding: 0 2%;
  margin-top: 15%;
}
#donation-content #total-amount {
  font-weight: 500;
  font-size: 16px;
  margin-bottom: 0 !important;
}
.project-summary .figure {
  position: relative;
  overflow: hidden;
  margin: 2%;
  /* min-width: 230px; */
  /* max-width: 315px; */
  /* width: 90%; */
  height: 95%;
  width: 96%;
  text-align: left;
  line-height: 1.4em;
}
.figure * {
  -webkit-transition: all 0.35s ease;
  transition: all 0.35s ease;
}
.figure img {
  width: 100%;
  height: 100%;
  vertical-align: top;
}
.figure .figcaption {
  position: absolute;
  top: 70%;
  width: 100%;
  height: 80%;
  background-color: #ffffff;
  padding: 3% 0;
}
.figure .figcaption:before {
  position: absolute;
  content: '';
  z-index: 2;
  bottom: 100%;
  left: 0;
  width: 100%;
  height: 30%;
  background-image: -webkit-linear-gradient(top, transparent 0%, #ffffff 100%);
  background-image: linear-gradient(to bottom, transparent 0%, #ffffff 100%);
}
.figure .figcaption #summary-text-box {
  margin: 3% 0;
  height: 30%;
  padding: 0 2%;
  /* border: 1px solid #f78065; */
}
.figure:hover .figcaption, .figure.hover .figcaption {
  top: 80px;
}
</style>
