<template>
  <div class="app-navbar sticky-top">
    <div class="container">
      <div class="row">
        <div class="col-3" id="navbar-logo">
          <img id="icon" src="../assets/icon1.png" alt="" @click="clickLogo">
        </div>
        <div class="col navbar-category">
          <div class="row row-cols-4 flex-fill">
            <span id="category-title" @click="clickDonation">기부</span>
            <span id="category-title" @click="clickFunding">펀딩</span>
            <span id="category-title" @click="clickService">활동</span>
            <span id="category-title" @click="clickBlood">헌혈</span>
          </div>
        </div>
        <div v-if="!state.token" class="navbar-user col-3">
          <div>
            <span id="navbar-login" @click="clickLogin">로그인</span>
            <span id="navbar-signup" @click="clickSignup">회원가입</span>
          </div>
        </div>
        <div v-else class="navbar-user col-3">
          <div class="navbar-dropdown">
            <el-dropdown>
              <span class="el-dropdown-link">
                내 정보 <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="clickMypage">마이페이지</el-dropdown-item>
                  <el-dropdown-item @click="clickMydata">내 정보</el-dropdown-item>
                  <el-dropdown-item @click="clickLogout">로그아웃</el-dropdown-item>
                </el-dropdown-menu>
              </template>  
            </el-dropdown>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";

export default {
  setup() {
    const store = useStore();
    const router = useRouter();

    const state = reactive({
      token: computed(() => store.getters["getToken"]),
      wallet: computed(() => store.getters['getWallet']),
    });
    const clickLogo = () => {
      store.commit("CHECK", true);
      router.push({ name: "Home" });
    };
    const clickProject = () => {
      router.push({ name: "Project" });
    };
    const clickDonation = () => {
      router.push({ name: "Donationlist" });
    };
    const clickFunding = () => {
      router.push({ name: "Fundinglist" });
    };
    const clickService = () => {
      router.push({ name: "Servicelist" });
    };
    const clickLogin = () => {
      router.push({ name: "Login" });
    };
    const clickSignup = () => {
      router.push({ name: "Signup" });
    };
    const clickMypage = () => {
      store.dispatch("myInfo", { token: state.token });
      router.push({ name: "Mypage" });
    };
    const clickMydata = () => {
      router.push({ name: "Mydata" });
    };
    const clickBlood = () => {
      if(state.wallet) router.push({ name: "Bloodinput" });
      else{
        ElMessage.error("헌혈증 생성을 위해 지갑이 필요합니다.");
        router.push({name:"Mypage"})
      } 
    };
    const clickLogout = () => {
      store.dispatch("logout").then(() => {
        ElMessage.error("로그아웃 되었습니다.");
        router.push("/");
      });
    };
    return {
      state,
      clickBlood,
      clickLogo,
      clickProject,
      clickDonation,
      clickFunding,
      clickService,
      clickLogin,
      clickSignup,
      clickMypage,
      clickMydata,
      clickLogout,
    };
  },
};
</script>

<style>
.app-navbar {
  height: 70px;
  background-color: #f78065;
  /* margin-bottom: 2vh; */
  display: flex;
  align-items: center;
  box-shadow: 0px 1px 10px 0px gray;
}
.app-navbar #icon {
  height: 50px;
}
.navbar-box {
  border: 1px solid blue;
  height: 70px;
}
#navbar-logo {
  color: white;
  display: flex;
  align-items: center;
}
#navbar-logo:hover {
  cursor: pointer;
}
.navbar-category {
  display: flex;
  justify-content: center;
  align-items: center;
}
#category-title {
  text-align: center;
  color: white;
  font-size: 20px;
  font-weight: bold;
}
#category-title:hover {
  font-size: 23px;
  font-weight: bolder;
  cursor: pointer;
}
.navbar-user {
  color: white;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.navbar-dropdown .el-dropdown-link {
  cursor: pointer;
  color: white;
}
.el-popper.is-light,
.el-popper.is-light .el-popper__arrow::before {
  border: 1px solid #f78065 !important;
}
.el-dropdown-menu__item:focus,
.el-dropdown-menu__item:not(.is-disabled):hover {
  background-color: #ffeadc !important;
  color: #f78065 !important;
}
#navbar-login {
  margin-right: 10px;
}
#navbar-login:hover {
  cursor: pointer;
}
#navbar-signup {
  margin-left: 10px;
}
#navbar-signup:hover {
  cursor: pointer;
}
</style>