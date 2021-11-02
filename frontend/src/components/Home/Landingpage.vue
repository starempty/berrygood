<template>
  <div :class="{ container: pageScroll == 1 }">
    <div :class="{ scroll2: pageScroll == 2 }" class="main-img-box">
      <img class="main-img" src="@/assets/donate.png" alt="">
    </div>
    <div class="main-blood-box">
      <div class="main-blood-message">
        <h1>블록체인 기반 기부 플랫폼 '베리굿'</h1>
        <p>투명한 기부 문화를 만들어 갑니다.</p>
      </div>
      <!-- <div>
        <button class="main-blood-button" @click="clickBlood">헌혈증 등록하기</button>
      </div> -->
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { reactive, computed } from "vue";

export default {
  props: {
    pageScroll: String,
  },
  setup() {
    const store = useStore();
    const router = useRouter();

    const state = reactive({
      token: computed(() => store.getters["getToken"]),
    });

    const clickBlood = () => {
      if (state.token) {
        router.push({ name: "Bloodinput" });
      } else {
        router.push({ name: "Login" });
      }
    };

    return { state, clickBlood };
  },
};
</script>

<style>
.main-img-box {
  width: 100%;
  /* height: 70vh; */
  transition: all ease-out 2s 0;
}
.scroll2.main-img-box {
  width: 100vw;
  height: 80vh;
  transition: all ease-out 2s 0;
}
.main-img {
  width: 100%;
  transition: all ease-out 2s 0;
}
.scroll2 .main-img {
  width: 100%;
  height: 100%;
  transition: all ease-out 2s 0;
}

.main-blood-box {
  height: 30vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
}
.main-blood-message {
  font-size: 25px;
}
.main-blood-button {
  margin-top: 3vh;
  background-color: #f78065;
  border: none;
  color: white;
  padding: 1vh;
}
</style>