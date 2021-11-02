<template>
  <div class="container">
    <div class="d-flex align-items-center justify-content-between" id="funding-box">
      <div class="d-flex flex-column">
        <!-- <div id="service-img">
          <img src="../../assets/volunteer.png" alt="">
        </div> -->
        <p style="font-size:50px; font-weight:600;">봉사</p>
        <p style="font-size:20px; font-weight:500;">다양한 봉사활동을 공유하고 참여할 수 있습니다.</p>
      </div>
      <i class="fas fa-angle-double-right" @click="clickService"></i>
    </div>
  </div>
  <!-- <div class="main-service-box">
    <div class="main-project-type">
      <div class="type-service" @click="clickService">
        <span>활동</span>
      </div>
    </div>
    <div class="service-carousel">
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
    const clickService = () => {
      emit("clickService", "service");
      //router.push({ name : 'Servicelist' })
    };
    return { state, clickService };
  },
};
</script>


<style>
.main-service-box {
  width: 100%;
  height: 20vh;
  display: flex;
  margin-bottom: 5vh;
}
.main-project-type {
  width: 15%;
  background-color: #f78065;
}
.type-service {
  height: 100%;
  color: white;
  font-size: 2vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.type-service:hover {
  cursor: pointer;
}
.service-carousel {
  width: 85%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
</style>
