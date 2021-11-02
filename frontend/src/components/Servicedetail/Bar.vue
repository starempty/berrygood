<template>
  <div class="service-detail-bar">
    <p>{{ parseInt(totalCount / num) }} %</p>
    <div>
      <progress class="progress-bar" :value="totalCount" :max="num"></progress>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    num: String,
    totalCount: String,
  },
  setup() {
    
  },
}
</script>
<style>
.service-detail-bar {
  width: 100%;
  border: 1px;
  margin-top: 2%;
  /* padding: 0 5%; */
}
.service-detail-bar p {
  margin-bottom: 0;
  font-size: 25px;
  font-weight: 600;
}
progress {
  -webkit-appearance: none;
  /* border-radius: 30px; */
}
::-webkit-progress-bar {
  background-color: #EDEFEF;
}
::-webkit-progress-value {
  background-color: #FF6647;
}
.progress-bar {
  margin-top: 1%;
  width: 100%;
}

</style>