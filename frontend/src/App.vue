<template>
  <Navbar/>
  <router-view
    @openChargeDialog="onOpenChargeDialog"
  />
  <!-- <Footer/> -->
  <Chargedialog
    :open="state.chargeDialog"
    @closeChargeDialog="onCloseChargeDialog"
  />
</template>

<script>
import Navbar from "@/views/Navbar.vue";
import Chargedialog from "@/views/Chargedialog.vue"
import { reactive } from 'vue'
// import Footer from "@/views/Footer.vue"

export default {
  components: {
    Navbar,
    Chargedialog,
    // Footer
  },
  setup() {
    const state = reactive ({
      chargeDialog: false,
    })
    const onOpenChargeDialog = () => {
			state.chargeDialog = true
		}
		const onCloseChargeDialog = () => {
			state.chargeDialog = false
		}
    return { state, onOpenChargeDialog, onCloseChargeDialog }
  },
};
</script>

<style>
</style>