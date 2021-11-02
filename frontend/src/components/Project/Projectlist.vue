<template>
  <div class="project-category-box container">
    <div class="project-category flex-fill">
      <div id="category-button" @click="clickDonation">
        <span>기부</span>
      </div>
      <div id="category-button" @click="clickFunding">
        <span>펀딩</span>
      </div>
      <div id="category-button" @click="clickService">
        <span>봉사</span>
      </div>
      <!-- <div id="category-button">
      </div>
      <div id="category-button">
      </div>
      <div id="category-button">
      </div> -->
    </div>
  </div>
  <div class="projectlist-box">
    <div v-if="state.category === 'donation'">
      <Donationlist/>
    </div>
    <div v-else-if="state.category === 'funding'">
      <Fundinglist/>
    </div>
    <div v-else-if="state.category === 'service'">
      <Servicelist/>
    </div>
  </div>
</template>

<script>
import { reactive } from 'vue'
import Donationlist from '@/components/Project/Donationlist.vue'
import Fundinglist from '@/components/Project/Fundinglist.vue'
import Servicelist from '@/components/Project/Servicelist.vue'

export default {
  components: {
    Donationlist,
    Fundinglist,
    Servicelist,
  },
  setup() {
    const state = reactive ({
      category: 'donation',
    })
    const clickDonation = () => {
      state.category = 'donation'
    }
    const clickFunding = () => {
      state.category = 'funding'
    }
    const clickService = () => {
      state.category = 'service'
    }
    return { state, clickDonation, clickFunding, clickService }
  },
}
</script>
<style>
.project-category-box {
  width: 100%;
  height: 15vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.project-category {
  padding: 0 7vw;
  display: flex;
  justify-content: space-between;
}
.project-category #category-button {
  width: 75px;
  height: 75px;
  border: 2px solid white;
  background-color: #f78065;
  border-radius: 50%;
  box-shadow: 0px 1px 2px 0px gray;
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  font-weight: bold;
}
.project-category #category-button:hover {
  /* width: 80px;
  height: 80px; */
  font-size: 18px;
  cursor: pointer;
  opacity: 0.8;
}
.projectlist-box {
  width: 100%;
  /* border: 1px solid black; */
  min-height: 75vh;
  margin-bottom: 5vh;
}
</style>