<template>
  <h2>기부 현황</h2>
  <div class="main-current-box">
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
.main-current-box {
  width: 100%;
  height: 30vh;
  display: flex;
}
.main-current-box .current {
  width: 50%;
}
.main-current-box .current .message-box {
  display: flex;
  justify-content: center;
  align-items: center;
}

.main-current-box .current .message-box #message {
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