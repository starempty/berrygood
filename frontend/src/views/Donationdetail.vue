<template>
  <div class="container pb-5">
    <Donationinfo 
      :name="state.name" 
      :content="state.content" 
			:due="state.due"
      :img="state.img" 
      :comp="state.comp"
			:compEmail="state.compEmail"
			:user="state.user"
			:isFinish="state.isFinish"
			:donationNo="state.donationNo"
    />
    <Donationcurrent
			v-if="state.messages.length !== 0"
      :messages="state.messages"
    />
    <Donationinput
      v-if="state.user.role =='user' && !state.isFinish"
      :user="state.user"
      :comp="state.comp"
			@openChargeDialog="onOpenChargeDialog"
      @openDonationDialog="onOpenDonationDialog"
    />
    <Donationdialog
      :open="state.donationDialog"
      :donationInput="state.donationInput"
			:donationNo="state.donationNo"
      @closeDonationDialog="onCloseDonationDialog"
    />
		<Chargedialog
			:open="state.chargeDialog"
			@closeChargeDialog="onCloseChargeDialog"
		/>
  </div>
</template>

<script>
import Donationinfo from "@/components/Donationdetail/Donationinfo.vue"
import Donationcurrent from "@/components/Donationdetail/Donationcurrent.vue"
import Donationinput from "@/components/Donationdetail/Donationinput.vue"
import Donationdialog from "@/components/Donationdetail/Donationdialog.vue"
import Chargedialog from "@/views/Chargedialog.vue"
import { reactive, onMounted, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
	components: {
		Donationinfo,
		Donationcurrent,
		Donationinput,
		Donationdialog,
		Chargedialog,
	},
	props: {
		donationNo: String,
	},
	setup(props) {
		const store = useStore()
		const router = useRouter()
		const state = reactive ({
      user: computed(() => store.getters['getUser']),
			donationNo: computed(() => props.donationNo),
			donationDialog: false,
			donationInput: {},
			name: '',
			content: '',
			due: '',
			comp: '',
      messages: [],
      img: [],
			isFinish: false,
			chargeDialog: false,
		})
		const onOpenDonationDialog = (donationInput) => {
			state.donationDialog = true
			state.donationInput = donationInput
		}
		const onCloseDonationDialog = () => {
			state.donationDialog = false
			router.go()
		}
		console.log(router)
		const onOpenChargeDialog = () => {
			state.chargeDialog = true
		}
		const onCloseChargeDialog = () => {
			state.chargeDialog = false
		}
    onMounted(() => {
      store.dispatch('Donation/donateDetail', props.donationNo)
      .then(({ data }) => {
				console.log(data)
				state.name = data.donate.name
				state.content = data.donate.content
				state.due = data.donate.due
				state.comp = data.donate.comp.name
				state.compEmail = data.donate.comp.email
				state.messages = data.messages.slice(-6)
        state.img = data.imgPath
				state.isFinish = new Date(data.donate.due) > new Date() ? false : true
        store.dispatch('compwalletDetail', data.donate.comp.email)
        .then(({ data }) => {
					store.commit('PJTWALLET',data);
        })
      })
    })
		return { state, onOpenDonationDialog, onCloseDonationDialog, onOpenChargeDialog, onCloseChargeDialog }
	},
}
</script>