<template>
  <div class="container">
    <div class="d-flex align-items-center justify-content-between" id="funding-box">
      <div class="d-flex flex-column">
        <!-- <div id="funding-img">
          <img src="../../assets/funding.png" alt="">
        </div> -->
        <p style="font-size:50px; font-weight:600;">펀딩</p>
        <p style="font-size:20px; font-weight:500;">블록체인 기술을 통해 투명한 펀딩이 가능합니다.</p>
      </div>
      <i class="fas fa-angle-double-right" @click="clickFunding"></i>
    </div>
  </div>
  <!-- <div class="main-funding-box">
    <div class="main-project-type">
      <div class="type-funding" @click="clickFunding">
        <span>펀딩</span>
      </div>
    </div>
    <div class="funding-carousel">
      <div class="project-summary-box">
        <div id="summary-previous">
          <i class="fas fa-angle-left"></i>
        </div>
        <div class="project-summary">
          <div id="summary-img">
          </div>
        </div>
        <div class="project-summary">
          <div id="summary-img">
          </div>
        </div>
        <div class="project-summary">
          <div id="summary-img">
          </div>
        </div>
        <div id="summary-next">
          <i class="fas fa-angle-right"></i>
        </div>
      </div>
    </div>
  </div> -->
</template>

<script>
import { reactive } from "vue";
//import { useRouter } from 'vue-router'

export default {
  props: {},
  setup(props, { emit }) {
    //const router = useRouter()
    const state = reactive({});
    const clickFunding = () => {
      emit("clickFunding", "funding");
      //router.push({ name : 'Fundinglist' })
    };
    return { state, clickFunding };
  },
};
</script>

<style>
#funding-box {
  width: 100%;
  height: 100vh;
}
#funding-box i {
  font-size: 50px;
}
#funding-box i:hover {
  cursor: pointer;
  color: #f78065;
  transform: scale(1.2);
}
.main-funding-box {
  width: 100%;
  height: 20vh;
  display: flex;
  margin-bottom: 5vh;
}
.main-project-type {
  width: 15%;
  background-color: #f78065;
}
.type-funding {
  height: 100%;
  color: white;
  font-size: 2vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.type-funding:hover {
  cursor: pointer;
}
.funding-carousel {
  width: 85%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
</style>
