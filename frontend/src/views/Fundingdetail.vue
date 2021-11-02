<template>
	<div class="container">
		<Fundinginfo
      :name="state.name"
      :content="state.content"
      :due="state.due"
      :amount="state.amount"
      :totalCount="state.totalCount"
      :totalAmount="state.totalAmount"
      :comp="state.comp"
      :compEmail="state.compEmail"
      :img="state.imgPath"
      :fundingNo="state.fundingNo"
      :isFinish="state.isFinish"
      :calc="state.calc"
    />
		<!-- <Fundingcurrent/> -->
		<Fundinginput
      v-if="state.user.role =='user' && !state.isFinish"
      :comp="state.comp"
      :user="state.user"
      @openChargeDialog="onOpenChargeDialog"
      @openFundingDialog="onOpenFundingDialog"
    />
    <Fundingdialog
      :open="state.fundingDialog"
      :fundingInput="state.fundingInput"
      :fundingNo="state.fundingNo"
      @closeFundingDialog="onCloseFundingDialog"
    />
    <Chargedialog
			:open="state.chargeDialog"
			@closeChargeDialog="onCloseChargeDialog"
		/>

	</div>
</template>

<script>
import Fundinginfo from "@/components/Fundingdetail/Fundinginfo.vue";
// import Fundingcurrent from "@/components/Fundingdetail/Fundingcurrent.vue";
import Fundinginput from "@/components/Fundingdetail/Fundinginput.vue";
import Fundingdialog from "@/components/Fundingdetail/Fundingdialog.vue";
import Chargedialog from "@/views/Chargedialog.vue"
import { reactive, onMounted, computed } from "vue";
import { useStore } from "vuex";

export default {
  components: {
    Fundinginfo,
    Fundinginput,
    Fundingdialog,
    Chargedialog,
  },
  props: {
    fundingNo: String,
  },
  setup(props) {
    const store = useStore();

    const state = reactive({
      user: computed(() => store.getters['getUser']),
      fundingInput: {},
      name: '',
      content: '',
      due: '',
      amount: '',
      totalCount: '',
      totalAmount: '',
      comp: '',
      compEmail: '',
      imgPath: [],
      fundingDialog: false,
      fundingNo: computed(() => props.fundingNo),
      isFinish: false,
      chargeDialog: false,
      calc: 0,
    });
    const onOpenFundingDialog = (fundingInput) => {
			state.fundingDialog = true
			state.fundingInput = fundingInput
		}
		const onCloseFundingDialog = () => {
			state.fundingDialog = false
		}
    const onOpenChargeDialog = () => {
			state.chargeDialog = true
		}
		const onCloseChargeDialog = () => {
			state.chargeDialog = false
		}
    onMounted(() => {
      store.dispatch("Funding/fundingDetail", props.fundingNo)
        .then(({ data }) => {
          console.log(data)
          state.name = data.funding.name
          state.content = data.funding.content
          state.due = data.funding.due
          state.amount = data.funding.amount
          state.totalCount = data.totalCnt
          state.totalAmount = data.totalAmount
          state.comp = data.funding.comp.name
          state.compEmail = data.funding.comp.email
          state.imgPath = data.imgPath
          state.isFinish = new Date(data.funding.due) > new Date() ? false : true
          state.calc = data.funding.calc;
          store.dispatch("compwalletDetail", data.funding.comp.email)
          .then(({ data }) => {
            store.commit('PJTWALLET', data);
          })
        });
    });

    return { state, onOpenFundingDialog, onCloseFundingDialog, onOpenChargeDialog, onCloseChargeDialog };
  },
};
</script>