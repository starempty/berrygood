<template>
  <!-- <div class="section d-flex flex-column justify-content-center align-items-center" id="section1">
    <h1 class="mb-5" id="title">블록체인 기반 기부 플랫폼 '베리굿'</h1>
    <img class="main-img" id="img" src="@/assets/donate.png" alt="">
    <div v-if="state.pageScroll == 2" class="text-center" id="content">
      <h1>투명한 기부 문화를 만들어 갑시다.</h1>
      <div id="down" class="" @click="downClick"></div>
    </div>
  </div>
  <div class="section" id="section2">
    <Donation/>
  </div>
  <div class="section" id="section3">
    <Funding/>
  </div>
  <div class="section" id="section4">
    <Service/>
    class="div-slider"
  </div> -->
  <div id="demo">
    <transition-group tag="div"  :name="state.check? 'slideback' : 'slide'">

      <div v-if="state.check" key="1">
      <div class="section d-flex flex-column justify-content-center align-items-center" id="section1">
        <div id="title" class="text-center">
          <h1 class="mb-3">블록체인 기반 기부 플랫폼</h1>
          <h1 style="font-size:50px; font-weight:700;">'베리굿'</h1>
        </div>
        <img class="main-img" id="main-img" src="@/assets/main.png" alt="">
        <div v-if="state.pageScroll == 2" class="text-center" id="content">
          <h1>블록체인 기술을 통해</h1>
          <h1>투명한 기부 문화를 만들어 갈 수 있습니다.</h1>
          <div id="down" class="" @click="downClick"></div>
        </div>
      </div>
      <div class="section" id="section2">
        <Donation @clickDonation="onClickDetail"/>
      </div>
      <div class="section" id="section3">
        <Funding @clickFunding="onClickDetail"/>
      </div>
      <div class="section" id="section4">
        <Service @clickService="onClickDetail"/>
      </div>
      </div>

      <div v-if="!state.check" key="2">
        <Donationlist v-if="state.clickDetail === 1"/>
        <Fundinglist v-else-if="state.clickDetail === 2"/>
        <Servicelist v-else-if="state.clickDetail === 3"/>
      </div>
    </transition-group>
    <Footer/>
  </div>
  <div id="donate-img-box">
    <img src="../assets/donate.png" alt="">
  </div>
  <div id="funding-img-box">
    <img src="../assets/funding.png" alt="">
  </div>
  <div id="service-img-box">
    <img src="../assets/volunteer.png" alt="">
  </div>
</template>

<script>
// import Landingpage from "@/components/Home/Landingpage.vue"
import Donation from "@/components/Home/Donation.vue";
import Funding from "@/components/Home/Funding.vue";
import Service from "@/components/Home/Service.vue";
import Donationlist from "@/components/Project/Donationlist.vue";
import Fundinglist from "@/components/Project/Fundinglist.vue";
import Servicelist from "@/components/Project/Servicelist.vue";
import Footer from "@/views/Footer.vue"
import { computed, reactive } from "vue";
import { useStore } from "vuex";

export default {
  name: "Home",
  components: {
    // Landingpage,
    Donation,
    Funding,
    Service,
    Donationlist,
    Fundinglist,
    Servicelist,
    Footer,
  },
  setup() {
    const store = useStore();

    const state = reactive({
      pageScroll: 1,
      donationList: false,
      check: computed(() => store.getters["getCheck"]),
      clickDetail: 0,
    });
    const getDelay = () => {
      var down = document.getElementById("down");
      down.innerHTML =
        '<i class="fas fa-angle-double-down animate__animated animate__bounce animate__infinite"></i>';
    };
    const downClick = () => {
      var postop = document.getElementById(`section2`).offsetTop - 70;
      window.scrollTo({ top: postop, behavior: "smooth" });
    };
    const onScroll = (e) => {
      if (e.deltaY > 0) {
        if (state.pageScroll == 5) return;
        state.pageScroll++;
      } else if (e.deltaY < 0) {
        if (state.pageScroll == 1) return;
        state.pageScroll--;
      }
      if (state.pageScroll <= 2) {
        var postop = 0;
        var img = document.getElementById("main-img");
        var title = document.getElementById("title");
        if (state.pageScroll == 1) {
          img.style.width = "45vw";
          img.style.height = "40vh";
          img.style.opacity = "1";
          title.style.display = "block";
        }
        if (state.pageScroll == 2) {
          img.style.width = "100%";
          img.style.height = "100%";
          img.style.opacity = "0";
          img.style.transition = "width 1s, height 1s, opacity 2s";
          title.style.display = "none";
          setTimeout(getDelay, 1000);
        }
      } else if (state.pageScroll <= 3) {
        postop = document.getElementById(`section2`).offsetTop - 70;
      } else if (state.pageScroll <= 4) {
        postop = document.getElementById(`section3`).offsetTop - 70;
      } else if (state.pageScroll <= 5) {
        postop = document.getElementById(`section4`).offsetTop - 70;
      }
      // console.log(postop)
      window.scrollTo({ top: postop, behavior: "smooth" });
    };
    window.addEventListener("wheel", onScroll);

    const onClickDetail = (data) => {
      store.commit("CHECK", false);
      if (data === "donation") {
        state.clickDetail = 1;
      } else if (data === "funding") {
        state.clickDetail = 2;
      } else if (data === "service") {
        state.clickDetail = 3;
      }
    };

    return { state, downClick, onClickDetail };
  },
};
</script>

<style>
/* .landing {
  -ms-overflow-style: none;
  scrollbar-width: none;
} */
/* ::-webkit-scrollbar {
  display: none;
} */
#donate-img-box {
  position: absolute;
  top: 150%;
  left: 50%;
  width: 80%;
  height: 80%;
  /* border: 1px solid #f78065; */
  z-index: -1;
  transform: translate(-50%, -55%);
  display: flex;
  justify-content: center;
}
#donate-img-box img {
  height: 100%;
  width: 70%;
  opacity: 0.5;
}
#funding-img-box {
  position: absolute;
  top: 250%;
  left: 50%;
  width: 80%;
  height: 80%;
  /* border: 1px solid #f78065; */
  z-index: -1;
  transform: translate(-50%, -60%);
  display: flex;
  justify-content: center;
}
#funding-img-box img {
  height: 100%;
  width: 80%;
  opacity: 0.5;
}
#service-img-box {
  position: absolute;
  top: 350%;
  left: 50%;
  width: 80%;
  height: 80%;
  /* border: 1px solid #f78065; */
  z-index: -1;
  transform: translate(-50%, -65%);
  display: flex;
  justify-content: center;
}
#service-img-box img {
  height: 90%;
  width: 65%;
  opacity: 0.5;
}
.main-img {
  width: 45vw;
  height: 40vh;
}
#content {
  position: absolute;
  top: 50%;
}
#content h1 {
  animation: zoomIn;
  animation-duration: 2s;
  margin-bottom: 2vh;
}
#down i {
  /* animation: zoomIn;
  animation-duration: 2s;
  --animate-delay: 3s; */
  font-size: 50px;
}
h1.animate__animated.animate__zoomIn {
  --animate-duration: 2s;
}
#down i:hover {
  cursor: pointer;
  color: #f78065;
  transform: scale(1.2);
}
.section {
  width: 100vw;
  height: calc(100vh - 70px);
}
#section1 {
  display: flex;
  align-items: center;
}
#section2 {
}
#section3 {
}
#section4 {
}
#demo {
  overflow: hidden;
}

.slide-leave-active,
.slide-enter-active {
  transition: 1s;
}
.slide-enter {
  transform: translate(100%, 0);
}
.slide-leave-to {
  transform: translate(-100%, 0);
}

.slideback-leave-active,
.slideback-enter-active {
  transition: 1s;
}
.slideback-enter {
  transform: translate(-100%, 0);
}
.slideback-leave-to {
  transform: translate(100%, 0);
}
</style>
