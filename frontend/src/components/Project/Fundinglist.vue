<template>
  <div class="container">
    <div v-if="state.main">
      <div class="funding-main">
        <h2>이 달의 펀딩</h2>
        <hr id="funding-line">
        <div class="d-flex">
          <div class="funding-main-img">
            <el-carousel 
              class="flex-fill"
              indicator-position="outside"
              height="380px"
            >
              <el-carousel-item v-for="i in state.fundingMain.imgPath" :key="i">
                <img class="funding-img" :src="`http://j5d101.p.ssafy.io/${i}`" alt="">
              </el-carousel-item>
            </el-carousel>
            <!-- <img :src="`http://j5d101.p.ssafy.io/${state.fundingMain.imgPath}`" alt=""> -->
          </div>
          <div class="funding-main-desc">
            <p id="comp">{{ state.fundingMain.funding.comp.name }}</p>
            <h1>{{ state.fundingMain.funding.name }}</h1>
            <div class="d-flex" id="due">
              <p class="me-3"><i class="far fa-calendar-alt me-2"></i>{{ state.fundingMain.funding.due }}</p>
              <p>D - {{ state.dday }}일</p>
            </div>
            <div class="funding-detail-bar">
              <div class="d-flex justify-content-between align-items-end">
                <p>{{ parseInt((state.fundingMain.totalAmount / state.fundingMain.funding.amount) * 100) }}%</p>
                <p id="total-amount">{{ state.fundingMain.totalAmount }} / {{ state.fundingMain.funding.amount }} B</p>
              </div>
              <div>
                <progress class="progress-bar" :value="state.fundingMain.totalAmount" :max="state.fundingMain.funding.amount"></progress>
              </div>
            </div>
            <p id="total-participant">참여 인원 : {{ state.fundingMain.totalCnt }} 명</p>
            <div class="d-flex justify-content-end" id="detail">
              <p class="animate__animated animate__pulse animate__infinite" id="clickDetail" @click="clickDetail(state.fundingMain.funding.no)">자세히 보기</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <div class="funding-none">
        <h2>현재 진행중인 펀딩이 없습니다.</h2>
        <div v-if="state.user.role == 'company' && state.wallet">
          <el-button id="createBtn" @click="clickCreate">펀딩 생성하기</el-button>
        </div>
      </div>
    </div>
    <div class="fundinglist-box">
      <h2 class="mb-5">지난 펀딩 보기</h2>
      <div class="funding-list row row-cols-1 row-cols-md-2 row-cols-lg-3 g-5">
        <div class="d-flex justify-content-center" v-for="funding in state.fundingList" :key="funding">
          <div class="project-summary" @click="clickFunding(funding.funding.no)">
            <div class="figure">
              <img id="funding-img" :src="`http://j5d101.p.ssafy.io/${funding.imgPath[0]}`">
              <div class="figcaption">
                <div id="summary-text-box" class="d-flex flex-column justify-content-between">
                  <span id="funding-comp">{{ funding.funding.comp.name }}</span>
                  <span id="funding-name">{{ funding.funding.name }}</span>
                </div>
                <div id="funding-content">
                  <div class="d-flex justify-content-between align-items-end">
                    <p id="total-percent">{{ parseInt((funding.totalAmount / funding.funding.amount) * 100) }}%</p>
                    <p id="total-amount">{{ funding.totalAmount }} / {{ funding.funding.amount }} B</p>
                  </div>
                  <div>
                    <progress class="progress-bar" :value="funding.totalAmount" :max="funding.funding.amount"></progress>
                  </div>
                  <p id="total-participant">참여 인원 : {{ funding.totalCnt }} 명</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, computed, reactive } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

export default {
  setup() {
    const router = useRouter()
    const store = useStore()
    const date = new Date()

    const state = reactive({
      user: computed(() => store.getters['getUser']),
      wallet: computed(() => store.getters['getWallet']),
      fundingMain: {},
      fundingList: [],
      today: date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2),
      main: false,
      dday: '',
    });
    
    const clickCreate = () => {
      router.push({ name: "Fundingcreate" })
    }

    const clickFunding = (no) => {
      router.push({
        name: "Fundingdetail",
        params: { fundingNo: no },
      });
    };
    const clickDetail = (no) => {
      router.push({
        name: "Fundingdetail",
        params: { fundingNo: no },
      })
    }

    onMounted(() => {
      store.dispatch("Funding/fundingList")
        .then(({ data }) => {
          if (data.slice(-1)[0].funding.due >= state.today) {
            state.fundingMain = data.pop();
          }
          state.fundingList = data.reverse();
          if (state.fundingMain.funding) {
            if (state.fundingMain.funding.due < state.today) {
              state.main = false
            } else {
              state.main = true
              const diff = new Date(state.fundingMain.funding.due).getTime() - new Date(state.today).getTime()
              state.dday = Math.ceil(diff / (1000 * 3600 * 24))
            }
          }
      });
      // if (new Date(state.fundingMain.funding.due) < new Date(state.today)) {
      //   state.main = false
      // } else {
      //   state.main = true
      // }
    })
    return { state, clickCreate, clickFunding, clickDetail };
  },
};
</script>

<style>
.fundinglist-box {
  /* margin-top: 2vh; */
  margin-bottom: 10%;
}
.funding-main {
  margin-top: 5%;
  margin-bottom: 10%;
}
#funding-line {
  background: #f78065;
  height: 2px;
  opacity: 1;
  margin: 3% 0 5% 0;
}
.funding-main .funding-main-img {
  width: 55%;
  height: 100%;
  min-width: 400px;
  margin-right: 8%;
}
.funding-main-img .funding-img {
  width: 100%;
  height: 100%;
}
.funding-main-desc {
  width: 40%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.funding-main-desc #comp {
  font-weight: 500;
  color: #858585;
}
.funding-main-desc #due {
  font-weight: 500;
  padding-left: 2%;
  margin-top: 5%;
  /* color: #858585; */
}
.funding-main-desc #detail {
  font-size: 20px;
  margin-top: 5%;
}
.funding-main-desc #detail #clickDetail:hover {
  cursor: pointer;
  color: #f78065;
  font-weight: 600;
}
#total-participant {
  font-size: 18px;
  font-weight: 500;
  margin-top: 5%;
}
.funding-main-desc .funding-detail-bar {
  margin-top: 10%;
}
.funding-detail-bar #total-amount {
  font-size: 22px;
}

.fundinglist-box .project-summary {
  display: block;
  width: 35vw;
  min-width: 280px;
  height: 22vh;
  min-height: 300px;
  margin: 5% 0;
  box-shadow: 0px 1px 10px 0px gray;
  padding: 5px;
}
.fundinglist-box h1 {
  margin-bottom: 5%;
}
.fundinglist-box .project-summary:hover {
  cursor: pointer;
}
/* .fundinglist-box .project-summary #summary-img-box {
  height: 70%;
} */
.fundinglist-box .project-summary #summary-text-box {
  margin: 3% 0;
  /* height: 25%; */
  padding: 0 2%;
  /* border: 1px solid #f78065; */
}
#funding-info {
  height: 50%;
}
#funding-comp {
  font-size: 16px;
  /* margin-top: 2%; */
}
#funding-name {
  font-size: 20px;
  font-weight: 500;
  margin-bottom: 2%;
}
#funding-content {
  padding: 0 2%;
  margin-top: 10%;
}
#funding-content p {
  font-weight: 500;
  font-size: 18px;
  margin-bottom: 0 !important;
}
.funding-none {
  height: 60vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.funding-none h2 {
  margin-bottom: 2%;
}
.funding-none #createBtn {
  color: white;
  background-color: #f78065;
  border: 1px solid #f78065;
  border-radius: 0;
}
.funding-none #createBtn:hover {
  color: white;
  background-color: #f78065;
  border: 1px solid #f78065;
  border-radius: 0;
  opacity: 0.8;
}
</style>