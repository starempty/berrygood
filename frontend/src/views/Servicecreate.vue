<template>
  <div class="container">
    <Createeditor/>
  </div>
  <Footer/>
</template>

<script>
import Createeditor from '../components/Servicecreate/Createeditor.vue'
import Footer from '@/views/Footer.vue'
export default {
  components : {
    Createeditor,
    Footer,
  },
}
</script>

<style>
</style>