<template>
  <div class="donation-list row row-cols-1 row-cols-md-2 row-cols-lg-3 g-5">
      <div class="d-flex justify-content-center" v-for="donation in state.list.slice().reverse()" :key="donation">
        <div class="project-summary" @click="clickDonation(donation.donate.no)">
          <div class="figure">
            <img id="donation-img" :src="`http://j5d101.p.ssafy.io/${donation.imgPath[0]}`">
            <div class="figcaption">
              <div id="summary-text-box" class="d-flex flex-column justify-content-between">
                <span id="donation-comp">{{ donation.donate.comp.name }}</span>
                <span id="donation-name">{{ donation.donate.name }}</span>
              </div>
              <div id="donation-content">
                <!-- <p id="total-percent">현재 기부 금액 : {{ donation.totalAmount }} B</p> -->
                <div v-if="donation.totalAmount">
                  <p id="total-participant">현재 기부 금액 : {{ donation.totalAmount }} B</p>
                </div>
                <div v-else>
                  <p id="total-participant">현재 기부 금액 : 0 B</p>
                </div>
                <div><p id="total-participant">참여 인원 : {{ donation.totalCnt }} 명</p></div>
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
    const clickDonation = (donationNo) => {
      router.push({ name: "Donationdetail", params: { donationNo: donationNo }})
    };
    return{
      state,
      clickDonation
    }
  }
}
</script>
<style>
.el-tabs__item{
  font-size: 18px !important;
}
.donation-list{
  padding-top: 10px;
  margin-bottom: 15px;
  padding-left: 15px;
  padding-right: 15px;
}
</style>