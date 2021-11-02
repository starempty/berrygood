<template>
  <div class="donation-current-box">
  <h2>응원의 메세지</h2>
    <div class="row row-cols-3 g-4">
      <div class="message-box col" v-for="message in messages" :key="message">
        <div id="message">
          <span>{{ message }}</span>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    messages: Array,
  },
  setup() {
    
  },
}
</script>
<style>
.donation-current-box {
  width: 100%;
  margin-bottom: 8%;
}
.donation-current-box .message-box {
  height: 120px;
  padding: 2%;
}
.donation-current-box .message-box #message {
  /* border: 1px solid #f78065; */
  /* text-al */
  word-break: break-all;
  height: 120px;
  width: 100%;
  padding: 5%;
  box-shadow: 0px 1px 5px 0px #f78065;
  display: flex;
  align-items: center;
}

.donation-current-box .current .message-box #message {
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