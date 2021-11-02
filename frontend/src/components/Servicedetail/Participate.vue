<template>
	<div class="service-detail-participate">
    <h4>함께하러 가기</h4>
    <el-button v-if="!state.checkRegist" @click="clickParticipate">참가하기</el-button>
    <el-button v-if="state.checkRegist" @click="clickCancle">참가취소</el-button>
	</div>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'


export default {
  props: {
    serviceId: String,
  },
  setup(props) {

    const store = useStore()
    const state = reactive ({
      amount: 1,
      projectId: '',
      user: computed(() => store.getters['getUser']),
      checkRegist: false,
    })
    const clickParticipate = () => {
      // this.serviceInput.files = this.$refs.picture1.files[0]
      store.dispatch('Service/participantRegister', {
        amount: state.amount,
        projectId: props.serviceId,
        userEmail: state.user.email
      })
      state.checkRegist = !state.checkRegist
    }

    const clickCancle = () => {
      store.dispatch('Service/participantDelete', {
        amount: state.amount,
        projectId: props.serviceId,
        userEmail: state.user.email
      })
      state.checkRegist = !state.checkRegist
    }

    return { state, clickParticipate, clickCancle }
  }
}
</script>

<style>
.service-detail-participate {
  width: 100%;
  height: 30vh;
  /* border: 1px solid black; */
}
</style>