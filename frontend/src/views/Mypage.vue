<template>
  <div class="container">
    <br>
    <h1 class="mt-5" style="text-align: right"><b>{{state.user.name}}</b>님 안녕하세요:)</h1><br>
    <div class="wallet-container">
      <div v-if="!state.wallet">
        <p>지갑을 생성한 후 베리를 충전할 수 있습니다.</p>
        <el-button id="walletBtn" @click="state.walletCreate = true">지갑 생성하기</el-button>
      </div>
      <div v-else>
        <p id="my-berry">나의 베리</p>
        <div class="wallet">
          <div>
            <h1 class="berry">{{state.wallet.berry}}B</h1>
          </div>
          <div>
            <el-button id="walletBtn" @click="openCharge">충전하기</el-button>
          </div>
        </div>
      </div>
    </div>
    <!-- @change="handleChange" -->
    <el-collapse v-model="activeNames" >
      <el-collapse-item v-if="state.wallet" title="거래 내용" name="1">
        <Transaction/> 
      </el-collapse-item>
      <el-collapse-item title="기부" name="2">
        <Donationlist  v-if="state.user.role==='user'"/>
        <compDonationlist v-else/>
      </el-collapse-item>
      <el-collapse-item title="펀딩" name="3">
        <Fundinglist  v-if="state.user.role==='user'"/>
        <compFundinglist v-else/>
      </el-collapse-item>
      <el-collapse-item title="헌혈" name="4" v-if="state.user.role==='user'">
        <Blood/>
      </el-collapse-item>
      <el-collapse-item title="봉사활동" name="5" v-if="state.user.role==='user'">
        <Service/> 
      </el-collapse-item>
      
    </el-collapse>
    <Badge/>
  </div>
  <Footer/>
  <el-dialog
    title="비밀번호 설정"
    v-model="state.walletCreate"
    width="500px"
  >
    <el-input v-model="state.pw" id="mypage-input" autocomplete="off" show-password></el-input>
    <template #footer>
      <span class="dialog-footer">
        <el-button id="confirm" @click="clickConfirm">확인</el-button>
      </span>
    </template>
  </el-dialog>
  <el-dialog
    title="비밀번호 확인"
    v-model="state.walletCreate1"
    width="500px"
  >
    <el-input id="mypage-input" v-model="state.pwConf" autocomplete="off" show-password></el-input>
    <template #footer>
      <span class="dialog-footer">
        <el-button id="confirm" @click="createWallet">확인</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed } from 'vue'
import { useStore } from 'vuex'
import Web3 from 'web3'
import { BLOCKCHAIN_URL } from '@/config';
import Badge from "@/components/Mypage/Badge.vue"
import Transaction from "@/components/Mypage/Transaction.vue"
import Donationlist from "@/components/Mypage/Donationlist.vue"
import Fundinglist from "@/components/Mypage/Fundinglist.vue"
import compDonationlist from "@/components/Mypage/compDonationlist.vue"
import compFundinglist from "@/components/Mypage/compFundinglist.vue"
import Blood from "@/components/Mypage/Blood.vue"
import Service from "@/components/Mypage/Service.vue"
import Footer from "@/views/Footer.vue"
import { ElLoading, ElMessage } from 'element-plus'

export default {
  components : {
    Badge,
    Transaction,
    Donationlist,
    compDonationlist,
    Blood,
    Service,
    Footer,
    Fundinglist,
    compFundinglist
  },
  setup(props, { emit }) {
    const store = useStore()
    const state = reactive ({
      user: computed(() => store.getters['getUser']),
      wallet: computed(() => store.getters['getWallet']),
      address:'',
      chargeDialog: false,
      money: '',
      walletCreate: false,
      walletCreate1: false,
      pw: '',
      pwConf: '',
    })
    const activeNames = reactive([])
    
    var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL))
    //지갑 생성
    const clickConfirm = () => {
      state.walletCreate = false
      state.walletCreate1 = true
    }
    const createWallet = () => {
        state.walletCreate1 = false
        if (state.pw === state.pwConf) {
          const loading = ElLoading.service({
            lock: true,
            text: '지갑을 생성 중 입니다.',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          })
          web3.eth.personal.newAccount(state.pwConf)
          .then((na) => {
            console.log(na)
            state.address = na
  
            if(state.user.role === "company"){
              store.dispatch('setCompwallet', {
                email: state.user.email,
                address: state.address,
                balance: 0,
                berry: 0,
              })
            } 
            else { 
              store.dispatch('setWallet', {
                email: state.user.email,
                address: state.address,
                balance: 0,
                berry: 0,
                receivecnt: 0
              })
            }
            loading.close()
            state.pw = ''
            state.pwConf = ''
          }).catch((err)=>{
            console.log('create', err)
            loading.close()
            ElMessage.error('지갑 생성에 문제가 발생했습니다. 다시 한 번 시도해주세요.')  
          })
        } else {
          state.pw = ''
          state.pwConf = ''
          state.walletCreate1 = false
          ElMessage.error('비밀번호가 올바르지 않습니다.')
        }
    }
    const openCharge = () => {
      emit('openChargeDialog')
    }

      return {
          state,
          activeNames,
          clickConfirm,
          createWallet,
          openCharge,
      }
  }
}

</script>
<style>
.wallet-container {
  height: 10vh;
  border: 1px;
  margin: 4vh 4vh 7vh 1vh;
}
.wallet{
  display: flex;
  justify-content: space-between;
}
.berry{
  color:salmon;
}
.wallet-container #walletBtn {
  color: white;
  background-color: #f78065;
  border: #f78065;
}
.wallet-container #walletBtn:hover {
  color: white;
  background-color: #f78065;
  border: #f78065;
  opacity: 0.8;
}
#wallet-address {
  font-weight: 500;
  font-size: 18px;
}
.el-dialog__footer .dialog-footer #confirm {
  color: white;
  background-color: #f78065;
  border-color: #f78065;
}
#my-berry {
  font-size: 20px;
  font-weight: 600;
}
.el-collapse-item__header{
  font-size: 18px !important;
  font-weight: 400 !important;
}
.el-collapse-item__header::selection{
  font-size: 18px !important;
  font-weight: 400 !important;
  color: #f78065;
}
#mypage-input.el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
  width: 95%;
}
#mypage-input.el-input__inner:hover {
  border-bottom: 1.5px solid #c0c4cc;
}
#mypage-input.el-input__inner:focus {
  border-bottom: 1px solid #f78065 !important;
}
#key-close {
  color: white;
  border: #f78065;
  background-color: #f78065;
}
</style>