<template>
  <!-- <img src="../assets/berry.jpg" alt="" class="background-img"> -->
  <div class="background-img"></div>
  <div class="signup-view">
    <div class="signup-box container text-center">
      <div class="container">
        <h3 class="animate__animated animate__fadeIn animate__delay-1s my-5">베리굿에 오신 것을 환영합니다.</h3>
        <div class="animate__animated animate__fadeIn animate__delay-1s">
          <el-tabs v-model="state.activeName" @tab-click="handleClick">
            <el-tab-pane label="일반" name="first">
              <Usersignup/>
            </el-tab-pane>
            <el-tab-pane label="기업" name="second">
              <Compsignup/>
            </el-tab-pane>
          </el-tabs>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from 'vue'
import Usersignup from '@/components/User/Signup/Usersignup.vue'
import Compsignup from '@/components/User/Signup/Compsignup.vue'

export default {
  components: {
    Usersignup,
    Compsignup,
  },
  setup() {
    const state = reactive ({
      activeName: 'first',
    })

    return { state }
  },
}
</script>

<style>
/* .signup-background {
  opacity: 0.2;
  position: absolute;
  top: 0px;
  left: 0px;
  right: 0px;
  bottom: 0px;
} */
.signup-view {
  width: 100%;
  height: calc(100vh - 70px);
  display: flex;
  justify-content: center;
  align-items: center;
}
.background-img {
  width: 100%;
  height: 100%;
  background: #dcdfe6;
  background-image: url('../assets/berry.jpg');
  background-size: cover;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
  opacity: 0.7;
}
.signup-box {
  background-color: white;
  /* border: 2px solid #f78065; */
  /* background-image: url('../assets/berry.jpg'); */
  width: 600px !important;
  padding: 1vh 1vw;
  animation: fadeInLeft;
  animation-duration: 1s;
  max-height: 70vh;
  overflow: auto;
}
.signup-box::-webkit-scrollbar {
  opacity: 1;
}
.signup-box .el-tabs__nav-scroll {
  display: flex;
  justify-content: space-evenly;
}
.signup-box .el-tabs__nav-scroll .el-tabs__nav.is-top {
  width: 100%;
  display: flex;
  justify-content: space-around;
}
.signup-box .el-tabs__nav-scroll .el-tabs__active-bar {
  background-color: #f78065;
  width: 50% !important;
}
.signup-box .el-tabs__nav-scroll .el-tabs__item {
  width: 100%;
}
.signup-box .el-tabs__nav-scroll .el-tabs__item:hover {
  color: #f78065;
}
.signup-box .el-tabs__nav-scroll .el-tabs__item.is-active {
  color: #f78065;
}

</style>