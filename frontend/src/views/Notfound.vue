<template>
  <section class="error-section">
    <div class="auto-container">
      <div class="content">
        <div class="big-text">
          <img src="@/assets/404-image.png" id="error-404" class="img-fluid" alt="">
        </div>
        <h2>죄송합니다. 페이지를 찾을 수 없습니다.</h2>
        <div class="link-box">
          <router-link to="/" class="theme-btn btn-style-one">
            <i class="btn-curve"></i>
            <span class="btn-title">홈으로 돌아가기</span>
          </router-link>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
  export default {
    name: "ErrorPage"
  }
</script>

<style scoped>
.error-section {
  position: relative;
  background: #ffffff;
  padding: 120px 0px;
}

.error-section .content {
  position: relative;
  max-width: 700px;
  text-align: center;
  margin: 0 auto;
}

.error-section .big-text {
  position: relative;
  line-height: 0.7em;
  font-size: 340px;
  color: var(--thm-base);
  font-weight: 400;
  letter-spacing: 15px;
  line-height: 1em;
}

.error-section .big-text .front {
  position: relative;
  display: block;
  width: 100%;
  text-align: center;
}

.error-section .big-text .back {
  position: absolute;
  padding-left: 20px;
  left: 0;
  top: 0;
  display: block;
  width: 100%;
  text-align: center;
  color: rgba(var(--thm-base-rgb), 0.1);
}

.error-section h2 {
  font-weight: 400;
  color: var(--thm-black);
  line-height: 1em;
  margin-bottom: 0px;
  text-transform: uppercase;
  letter-spacing: 0.005em;
}

.error-section .text {
  position: relative;
  display: block;
  font-weight: 300;
  line-height: 1.5em;
  letter-spacing: 0.1em;
}

.error-section .link-box {
  position: relative;
  padding-top: 20px;
}

.error-section .link-box .theme-btn {
  position: relative;
  min-width: 200px;
  text-decoration: none;
}
</style>
