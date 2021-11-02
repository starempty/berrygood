<template>
  <div class="service-list row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
        <div class="d-flex justify-content-center" v-for="service in state.list" :key="service">
          <div class="project-summary" @click="clickService(service.volunteer.no)">
            <div class="figure">
              <img id="service-img" :src="`http://j5d101.p.ssafy.io/${service.imgPath[0]}`">
              <div class="figcaption">
                <div id="summary-text-box" class="d-flex flex-column justify-content-between">
                  <span id="service-comp">{{ service.volunteer.user.name }}</span>
                  <span id="service-name">{{ service.volunteer.name }}</span>
                </div>
                <div id="service-content">
                  <div class="d-flex justify-content-between align-items-end">
                    <p id="total-percent">{{ parseInt((service.totalCnt / service.volunteer.num) * 100) }}%</p>
                    <p id="total-amount">{{ service.totalCnt }} / {{ service.volunteer.num }} 명</p>
                  </div>
                  <div>
                    <progress class="progress-bar" :value="service.totalCnt" :max="service.volunteer.num"></progress>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
</template>
<script>
import { computed, reactive } from 'vue'
import { useRouter } from "vue-router"
export default {
  props: {
    list: Array,
  },
  setup(props){
    const router = useRouter();
    const state = reactive({
      list : computed(() => props.list),
    });
    const clickService = (serviceNo) => {
      router.push({ name: "Servicedetail", params: { serviceNo: serviceNo } });
    };
    return{
      state,
      clickService
    }
  }
  
  
}
</script>
<style>
  .el-tabs__item{
  font-size: 18px !important;
}
.service-list{
  padding-top: 10px;
  margin-bottom: 15px;
  padding-left: 15px;
}
</style>