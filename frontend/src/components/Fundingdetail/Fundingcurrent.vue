<template>
  <h2>펀딩 현황</h2>
  <div class="funding-current-box d-flex">
    <div class="current row row-cols-3">
      <div class="message-box">
        <div id="message">
        </div>
      </div>
      <div class="message-box">
        <div id="message">
          <span>응원의 메세지</span>
        </div>
      </div>
      <div class="message-box">
        <div id="message">
          <span>응원의 메세지</span>
        </div>
      </div>
      <div class="message-box">
        <div id="message">
          <span>응원의 메세지</span>
        </div>
      </div>
      <div class="message-box">
        <div id="message">
          <span>응원의 메세지</span>
        </div>
      </div>
      <div class="message-box">
        <div id="message">
          <span>여기는 응원의 메세지가 들어갈 자리입니다.</span>
        </div>
      </div>
    </div>
    <div class="current">
    </div>
  </div>
</template>

<style>
.funding-current-box {
  width: 100%;
  height: 30vh;
  margin-bottom: 8%;
}
.funding-current-box .current {
  width: 50%;
}
.funding-current-box .current .message-box {
  display: flex;
  justify-content: center;
  align-items: center;
}

.funding-current-box .current .message-box #message {
  width: 80%; 
  height: 12vh;
  border: 1px solid #f78065;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 5px;
}
</style>