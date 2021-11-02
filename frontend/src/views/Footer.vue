<template>
  <div class="app-footer">
    <div class="container">
      <!-- <div class="col-3" id="footer-logo">
        <img id="icon" src="../assets/icon1.png" alt="" @click="clickLogo">
      </div> -->
      <div class="widgets-section">
        <div class="column col-xl-3 col-lg-6 col-md-6 col-sm-12">
          <div class="footer-widget logo-widget">
            <div class="widget-content">
              <ul class="social-links clearfix">
                <li>
                  <a href="https://ko-kr.facebook.com/login/web/"><span class="fab fa-facebook-square"></span></a>
                </li>
                <li>
                  <a href="https://www.instagram.com/accounts/login/"><span class="fab fa-instagram"></span></a>
                </li>
                <li>
                  <a href="service"><span class="fab fa-pinterest-p"></span></a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
      <ul class="footer-element">
        <li>
          <a href="">이용약관</a>
        </li>
        <li>
          <a href="">개인정보 처리방침</a>
        </li>
        <li>
          <a href="">오시는길</a>
        </li>
        <li>
          <a href="">FAQ</a>
        </li>
        <li>
          <a href="">사이트맵</a>
        </li>
      </ul>
      <div class="footer-text">
        <div>
          <span>희망나눔 플랫폼 베리굿</span>
          <span>주소 : 경상북도 구미시 임수동 94 삼성전자 구미2사업장</span>
        </div>
        <div>
          <span>이메일 : goodberry@berrygood.com</span>
          <span>대표번호 : 054 951 3602</span>
        </div>
        <div id="copyright">
          <span>© copyright 2021 by BerryGood.com</span>
        </div>
      </div>

      

    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  setup() {

    const store = useStore();
    const router = useRouter();

    const clickLogo = () => {
      store.commit("CHECK", true);
      router.push({ name: "Home" });
    };
    return { clickLogo }
  },
}
</script>

<style>
.app-footer {
  position: block;
  width: 100%;
  min-height: 20vh;
  background-color: #f7f7f7;
  margin-top: 5vh;
  padding: 1vh;
}
.app-footer .container{
  position: relative;
  margin: 0px auto;
}

.app-footer #icon {
  height: 34px;
}
#footer-logo {
  color: white;
  display: flex;
  align-items: center;
}
#footer-logo:hover {
  cursor: pointer;
}

.app-footer .social-links {
  position: absolute;
  right: 50px;
}

.app-footer .social-links li {
  position: relative;
  margin-right: 8px;
  display: inline-block;
}

.app-footer .social-links li a {
  position: relative;
  display: block;
  color: #fff;
  font-size: 16px;
  width: 44px;
  height: 44px;
  line-height: 44px;
  background: #f78065;
  text-align: center;
  border-radius: 50%;
  -webkit-transition: all 300ms ease;
  -moz-transition: all 300ms ease;
  -ms-transition: all 300ms ease;
  -o-transition: all 300ms ease;
  transition: all 300ms ease;
}

.app-footer .social-links li a span {
  vertical-align: middle;
}

.app-footer .social-links li a:hover {
  color: #ffffff;
  background: var(--thm-base);
}

.app-footer .footer-element {
  overflow: hidden;
  margin-top: 35px;
  margin-bottom: 20px;
  list-style: none;
}
.app-footer .footer-element li + li{
  padding-left: 8px;
}
.app-footer .footer-element li + li::before{
  content: "";
  display: block;
  width: 1px;
  height: 10px;
  background: rgb(183, 184, 186);
  position: absolute;
  left: 0px;
  top: 50%;
  margin-top: -5px;
}
.app-footer .footer-element li{
  position: relative;
  float: left;
  margin-right: 8px;
}
.app-footer .footer-element a{
  text-decoration: none;
  color: black;
}

.app-footer .footer-text {
  margin-left: 2em;
}
.app-footer .footer-text span{
  display: inline-block;
  margin: 0.5% 0;
}
.app-footer .footer-text span + span{
  margin-left: 12px;
}
.app-footer .footer-text span + span:before{
  content: "";
  width: 1px;
  height: 10px;
  background: rgb(183, 184, 186);
  position: absolute;
  left: 0px;
  top: 50%;
}
.app-footer .footer-text #copyright{
  color: #acacac;
}
</style>