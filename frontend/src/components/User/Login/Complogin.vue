<template>
<!-- 로그인 폼 임시 보관용 -->
  <div class="login-section">
    <div class="login-box">
      <div class="berry_login">
        <div class="berry_login_user_company">
          <div class="col_md_login_user">
            <div class="berry_ba_opcitiy">
              <h2>일반 회원</h2><br>
              <!-- <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p> -->
              <button class="btn_user_login" @click="user_login">LOGIN</button>
            </div>
          </div>
          <div class="col_md_login_company">
            <div class="berry_ba_opcitiy">
              <h2>기업 회원</h2><br>
              <!-- <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p> -->
              <button class="btn_login_company" @click="company_login">LOGIN</button>
            </div>
          </div>
        </div>

        <div class="cont_back_info">
          <div class="cont_img_back_grey">
            <img src="@/assets/heart.jpg" alt="" />
          </div>

        </div>
        <div class="berry_login_forms">
          <div class="cont_img_back_">
            <img src="@/assets/heart.jpg" alt="" />
          </div>
          <div class="berry_form_user">
            <a href="#" @click="ocultar_login_user_company"><i class="material-icons">&#xE5C4;</i></a>
            <h2>LOGIN</h2>
            <input v-model="state.user.id" type="text" placeholder="Email" />
            <input v-model="state.user.password" type="password" placeholder="Password" />
            <button class="btn_user_login" @click="userLogin">LOGIN</button>
          </div>

          <div class="berry_form_company">
            <a href="#" @click="ocultar_login_user_company"><i class="material-icons">&#xE5C4;</i></a>
            <h2>LOGIN</h2>
            <input v-model="state.comp.id" type="text" placeholder="Email" />
            <input v-model="state.comp.password" type="password" placeholder="Password" />
            <button class="btn_login_company" @click="compLogin">LOGIN</button>

          </div>

        </div>

      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { walletInfo } from '@/utils/BerryContract.js';

export default {
  setup() {
    const store = useStore();
    // const router = useRouter();
    const state = reactive ({
      user: {
        id: '',
        password: '',
      },
      comp: {
        id: '',
        password: '',
      }
    })

    function user_login() {
      document.querySelector(".berry_login_forms").className =
        "berry_login_forms berry_login_forms_active_login";
      document.querySelector(".berry_form_user").style.display = "block";
      document.querySelector(".berry_form_company").style.opacity = "0";

      setTimeout(function () {
        document.querySelector(".berry_form_user").style.opacity = "1";
      }, 400);

      setTimeout(function () {
        document.querySelector(".berry_form_company").style.display = "none";
      }, 200);
    }

    function company_login() {
      document.querySelector(".berry_login_forms").className =
        "berry_login_forms berry_login_forms_active_sign_up";
      document.querySelector(".berry_form_company").style.display = "block";
      document.querySelector(".berry_form_user").style.opacity = "0";

      setTimeout(function () {
        document.querySelector(".berry_form_company").style.opacity = "1";
      }, 400);

      setTimeout(function () {
        document.querySelector(".berry_form_user").style.display = "none";
      }, 200);
    }

    function ocultar_login_user_company() {
      document.querySelector(".berry_login_forms").className = "berry_login_forms";
      document.querySelector(".berry_form_company").style.opacity = "0";
      document.querySelector(".berry_form_user").style.opacity = "0";

      setTimeout(function () {
        document.querySelector(".berry_form_company").style.display = "none";
        document.querySelector(".berry_form_user").style.display = "none";
      }, 500);
    }
    
    const userLogin = () => {
      store.dispatch('userLogin', { email: state.user.id, pw: state.user.password })
      .then(() => {
        store.dispatch('walletDetail', state.user.id)
        .then(({data})=>{
          walletInfo(data).then((w) => {
            console.log(w)
            store.dispatch('setWallet', w);
          })
        })
      })
    }
    const compLogin = () => {
      store.dispatch('compLogin', { email: state.comp.id, pw: state.comp.password })
      .then(() => {
        store.dispatch('compwalletDetail', state.comp.id)
        .then(({data})=>{
          walletInfo(data).then((w) => {
            console.log(w)
            store.dispatch('setCompwallet', w);
          })
        })
      })
    }

    return { state, user_login, company_login, ocultar_login_user_company, userLogin, compLogin }
  },
}
</script>


<style scoped>

* {
  margin: 0px auto;
  padding: 0px;
  text-align: center;
  font-family: 'Open Sans', sans-serif;
}

.login-section {
  width: 100%;
  height: 40vh;
}

.login-box {
  position: relative;
  float: left;
  width: 100%;
  margin-top: 6%;
  margin-bottom: 6%;
  padding-top: 1%;
  padding-bottom: 1%;
}

.berry_login {
  position: relative;
  width: 640px;
  left: 50%;
  margin-left: -320px;
}

.cont_back_info {  
  position: relative;
  float: left;
  width: 640px;
  height: 280px;
  overflow: hidden;
  background-color: #fff;
  margin-top: 100px;
  box-shadow: 1px 10px 30px -10px rgba(0,0,0,0.5);
}

.berry_login_forms {
  position: absolute;
  overflow: hidden;
  top:100px;
  left: 0px;
  width: 320px;
  height: 280px;
  background-color: #eee;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
}

.berry_login_forms_active_login {
  box-shadow: 1px 10px 30px -10px rgba(0,0,0,0.5);
  height: 420px;  
  top:20px;
  left: 0px;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
}

.berry_login_forms_active_sign_up {
  box-shadow: 1px 10px 30px -10px rgba(0,0,0,0.5);
  height: 420px;  
  top:20px;
  left:320px;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
}

.cont_img_back_grey {
  position: absolute;
  width: 950px;
  top:-80px;
  left: -116px;
}

.cont_img_back_grey > img {
  width: 100%;
  -webkit-filter: grayscale(100%);     
  filter: grayscale(100%);
  opacity: 0.2;
  animation-name: animar_fondo;
  animation-duration: 20s;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
  animation-direction: alternate;
}

.cont_img_back_ {
  position: absolute;
  width: 950px;
  top:-80px;
  left: -116px;
}

.cont_img_back_ > img {
  width: 100%;
  opacity: 0.3;
  animation-name: animar_fondo;
  animation-duration: 20s;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
  animation-direction: alternate;
}

.berry_login_forms_active_login > .cont_img_back_ {
top:0px;  
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
}

.berry_login_forms_active_sign_up > .cont_img_back_ {
  top:0px;  
  left: -435px;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
}
 

.berry_login_user_company {
  position: absolute;
  width: 640px;
  height: 280px;
  top: 100px;
  z-index: 1;
} 

.col_md_login_user {
  position: relative;
  float: left;
  width: 50%;
}

.col_md_login_user > h2 {
  font-weight: 400;
  margin-top: 70px;
    color: #757575;
}

.col_md_login_user > p {
 font-weight: 400;
  margin-top: 15px;
  width: 80%;
    color: #37474F;
}

.btn_user_login { 
  background-color: #26C6DA;
  border: none;
  padding: 10px;
  width: 200px;
  border-radius:3px;
  box-shadow: 1px 5px 20px -5px rgba(0,0,0,0.4);
  color: #fff;
  margin-top: 10px;
  cursor: pointer;
}

.col_md_login_company {
  position: relative;
  float: left;
  width: 50%;  
}

.berry_ba_opcitiy > h2 {
  font-weight: 400;
  color: #fff;
}

.berry_ba_opcitiy > p {
 font-weight: 400;
  margin-top: 15px;
 color: #fff;
}
/* ----------------------------------
background text    
------------------------------------
 */
.berry_ba_opcitiy {
  position: relative;
  background-color: rgba(120, 144, 156, 0.55);
  width: 80%;
  border-radius:3px ;
  margin-top: 60px;
  padding: 15px 0px;
}

.btn_login_company { 
  background-color: #ef5350;
  border: none;
  padding: 10px;
  width: 200px;
  border-radius:3px;
  box-shadow: 1px 5px 20px -5px rgba(0,0,0,0.4);
  color: #fff;
  margin-top: 10px;
  cursor: pointer;
}

.berry_login_forms_active_sign_up {
  z-index: 2;  
}


@-webkit-keyframes animar_fondo {
  from { -webkit-transform: scale(1) translate(0px);
-moz-transform: scale(1) translate(0px);
  -ms-transform: scale(1) translate(0px);
  -o-transform: scale(1) translate(0px);
  transform: scale(1) translate(0px); }
  to { -webkit-transform: scale(1.5) translate(50px);
  -moz-transform: scale(1.5) translate(50px);
  -ms-transform: scale(1.5) translate(50px);
  -o-transform: scale(1.5) translate(50px);
  transform: scale(1.5) translate(50px); }
}

@-o-keyframes identifier {
  from { -webkit-transform: scale(1);
  -moz-transform: scale(1);
  -ms-transform: scale(1);
  -o-transform: scale(1);
  transform: scale(1); }
  to { -webkit-transform: scale(1.5);
  -moz-transform: scale(1.5);
  -ms-transform: scale(1.5);
  -o-transform: scale(1.5);
  transform: scale(1.5); }

}
@-moz-keyframes identifier {
  from { -webkit-transform: scale(1);
  -moz-transform: scale(1);
  -ms-transform: scale(1);
  -o-transform: scale(1);
  transform: scale(1); }
  to { -webkit-transform: scale(1.5);
  -moz-transform: scale(1.5);
  -ms-transform: scale(1.5);
  -o-transform: scale(1.5);
  transform: scale(1.5); }

}
@keyframes identifier {
  from { -webkit-transform: scale(1);
  -moz-transform: scale(1);
  -ms-transform: scale(1);
  -o-transform: scale(1);
  transform: scale(1); }
  to { -webkit-transform: scale(1.5);
  -moz-transform: scale(1.5);
  -ms-transform: scale(1.5);
  -o-transform: scale(1.5);
  transform: scale(1.5); }
}
.berry_form_user {
  position: absolute;
  opacity: 0;
  display: none;
  width: 320px;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
}

.berry_login_forms_active_login {
  z-index: 2;  
}
.berry_login_forms_active_login  >.berry_form_user {
}

.berry_form_company {
  position: absolute;
  width: 320px;
  loat: left;
  opacity: 0;
  display: none;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
}

  
.berry_form_company > input {
  text-align: left;
  padding: 15px 5px;
  margin-left: 10px;
  margin-top: 20px;
  width: 260px;
  border: none;
    color: #757575;
}

.berry_form_company > h2 {
  margin-top: 50px; 
  font-weight: 400;
  color: #757575;
}


.berry_form_user > input {
  padding: 15px 5px;
  margin-left: 10px;
  margin-top: 20px;
  width: 260px;
  border: none;
  text-align: left;
  color: #757575;
}

.berry_form_user > h2 {
  margin-top: 110px; 
  font-weight: 400;
  color: #757575;
}
.berry_form_user > a,.berry_form_company > a  {
  color: #757575;
  position: relative;
  float: left;
  margin: 10px;
  margin-left: 30px;
}
</style>