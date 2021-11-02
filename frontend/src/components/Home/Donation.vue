<template>
  <div class="container" v-if="!state.transition">
    <div class="d-flex align-items-center justify-content-between" id="donation-box">
      <div class="d-flex flex-column">
        <!-- <div id="donate-img">
          <img src="../../assets/donate.png" alt="">
        </div> -->
        <p style="font-size:50px; font-weight:600;">기부</p>
        <p style="font-size:20px; font-weight:500;">블록체인 기술을 통해 투명한 기부가 가능합니다.</p>
      </div>
      <i class="fas fa-angle-double-right animate__animated animate__pulse animate__infinite" @click="clickDonation"></i>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";
//import { useRouter } from "vue-router";

export default {
  props: {},
  setup(props, { emit }) {
    //const router = useRouter();

    const state = reactive({});
    const clickDonation = () => {
      emit("clickDonation", "donation");
      //router.push({ name: "Donationlist" });
    };

    // onBeforeRouteLeave((to, from) => {
    //   if (from.path == '/' && to.path == '/donation') {
    //     console.log(to)
    //   }
    // })

    return { state, clickDonation };
  },
};
</script>

<style>
#donation-box {
  width: 100%;
  height: 100vh;
}
#donation-box i {
  font-size: 50px;
}
#donation-box i:hover {
  cursor: pointer;
  color: #f78065;
  transform: scale(1.2);
}
.main-donation-box {
  width: 100%;
  height: 20vh;
  display: flex;
  margin-bottom: 5vh;
}
.main-project-type {
  width: 15%;
  background-color: #f78065;
}
.type-donation {
  height: 100%;
  color: white;
  font-size: 2vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.type-donation:hover {
  cursor: pointer;
}
.donation-carousel {
  width: 85%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.project-summary-box {
  height: 95%;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.project-summary-box #summary-previous {
  display: none;
  font-size: 3vh;
  color: #f78065;
}
.project-summary-box #summary-next {
  font-size: 3vh;
  color: #f78065;
  opacity: 0.7;
}
.project-summary-box #summary-next:hover {
  font-size: 4vh;
  opacity: 1;
  cursor: pointer;
}
.project-summary {
  display: block;
  width: 28%;
  height: 100%;
  margin: 0 2% !important;
  box-shadow: 0px 1px 10px 0px gray;
  padding: 5px;
}
.project-summary:hover {
  display: block;
  width: 30%;
  height: 20vh;
  margin: 0 2%;
  box-shadow: 0px 1px 10px 0px gray;
  cursor: pointer;
}
.project-summary-box #summary-img-box {
  height: 70%;
  /* border: 1px solid #f78065; */
}
.project-summary-box #summary-img-box #summary-img {
  width: 100%;
  height: 100%;
}
</style>
