<template>
  <div class="donation-title-box">
    <div class="d-flex justify-content-between align-items-center">
      <p>{{ comp }}</p>
      <div class="d-flex" v-if="user.email === compEmail && !isFinish">
        <p class="me-3" id="edit" @click="clickUpdate(donationNo)"><i class="far fa-edit me-1"></i>수정하기</p>
        <p id="delete" @click="clickDelete(donationNo)"><i class="far fa-trash-alt me-1"></i>삭제하기</p>
      </div>
    </div>
    <h1>{{ name }}</h1>
  </div>
  <div class="donation-content-box">
    <p id="content-detail">기부 소개</p>
    <hr id="content-line">
    <div v-html="content" class="mt-3"></div>
  </div>
  <div class="d-flex justify-content-center">
    <div class="carousel-box">
      <el-carousel 
        class="flex-fill"
        indicator-position="outside"
        height="30vh"
      >
        <el-carousel-item v-for="i in img" :key="i">
          <img class="donation-img" :src="`http://j5d101.p.ssafy.io/${i}`" alt="">
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  props: {
    donationNo: String,
    comp: String,
    compEmail: String,
    name: String,
    content: String,
    img: Array,
    user: Object,
    isFinish: Boolean,
  },
  setup() {
    const router = useRouter()
    const store = useStore()

    const state = reactive ({
      // user: computed(() => store.getters['getUser']),
    })
    const clickUpdate = (donationNo) => {
      router.push({ name: 'Donationupdate', params: { donationNo: donationNo }})
    }
    const clickDelete = (donationNo) => {
      ElMessageBox
        .confirm(
          '정말 삭제하시겠습니까?',
          '취소',
          {
            confirmButtonText: '네',
            cancelButtonText: '아니요',
          }
        )
        .then(() => {
          store.dispatch('Donation/donateDelete', donationNo)
            .then(() => {
              ElMessage.error('삭제되었습니다.')
              router.push({ name: 'Donationlist' })
            })
        })
        .catch(() => {})
    }
    return { state, clickUpdate, clickDelete }
  },
}
</script>

<style>
.donation-title-box {
  margin: 5% 0 3% 0;
  width: 100%;
  min-height: 5vh;
  /* padding: 0 5%; */
  /* border: 1px solid black; */
}
.donation-title-box p {
  margin: 2% 0;
  font-weight: 500;
  color: #858585;
}
.donation-content-box {
  width: 100%;
  /* border: 1px solid black; */
  margin-bottom: 2vh;
  min-height: 47vh;
  /* padding: 0 5%; */
}
.carousel-box {
  width: 60%;
  min-width: 600px;
  margin-bottom: 8%;
}
.donation-img {
  width: 100%;
  height: 100%;
}
.donation-content-box #content-detail {
  font-size: 16px;
  font-weight: 500;
  margin-top: 5%;
  margin-bottom: 1%;
}
#content-line {
  color: #f78065;
  height: 3px !important;
  opacity: 1;
  margin-top: 0;
  margin-bottom: 4%;
}
.donation-title-box #edit {
  color: #409eff;
}
.donation-title-box #edit:hover {
  color: #409eff;
  cursor: pointer;
  transform: scale(1.1);
}
.donation-title-box #delete {
  color: #f56c6c;
}
.donation-title-box #delete:hover {
  color: #f56c6c;
  cursor: pointer;
  transform: scale(1.1);
}
</style>