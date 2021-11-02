<template>
  <div class="container">
    <!-- <Bar
      :num="state.num"
      :totalCount="state.totalCount"
    /> -->
    <Content
      :name="state.name"
      :content="state.content"
      :date="state.date"
      :num="state.num"
      :totalCount="state.totalCount"
      :imgPath="state.imgPath"
      :user="state.user"
      :isFinish="state.isFinish"
      :serviceNo="state.serviceNo"
    />
  </div>
</template>

<script>
// import Bar from '../components/Servicedetail/Bar.vue'
import Content from '../components/Servicedetail/Content.vue'
import { onMounted, computed, reactive } from 'vue'
import { useStore } from 'vuex'


export default {
  components : {
    // Bar,
    Content,
  },
  props: {
    serviceNo: String,
  },
  setup(props) {
    const store = useStore()

    const state = reactive ({
      name: '',
      user: {},
      content: '',
      date: '',
      num: '',
      totalCount: '',
      serviceNo: computed(() => props.serviceNo),
      isFinish: false,
      imgPath: [],
    })
    onMounted(() => {
      store.dispatch('Service/serviceDetail', props.serviceNo)
      .then(({ data }) => {
        state.name = data.volunteer.name
        state.user = data.volunteer.user
        state.content = data.volunteer.content
        state.date = data.volunteer.date
        state.num = data.volunteer.num
        state.totalCount = data.totalCnt
        state.imgPath = data.imgPath
        if (new Date(data.volunteer.date) <= new Date() || data.totalCnt === data.volunteer.num) {
          state.isFinish = true
        } else {
          state.isFinish = false
        }
        console.log(state.isFinish)
      })
    })
    return { state }
  }

}
</script>