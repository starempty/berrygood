<template>
  <div class="container">
    <Createeditor/>
  </div>
  <Footer/>
</template>

<script>
import Createeditor from '@/components/Fundingcreate/Createeditor.vue'
import Footer from "@/views/Footer.vue"

export default {
  components : {
    Createeditor,
    Footer
  },
}
</script>

<style>

</style>