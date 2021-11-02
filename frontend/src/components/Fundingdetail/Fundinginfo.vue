<template>
  <div class="funding-title-box">
    <div class="d-flex justify-content-between align-items-center">
      <p>{{ comp }}</p>
      <div class="d-flex" v-if="state.user.email === compEmail && !isFinish">
        <p class="me-3" id="edit" @click="clickUpdate(fundingNo)"><i class="far fa-edit me-2"></i>수정하기</p>
        <p id="delete" @click="clickDelete(fundingNo)"><i class="far fa-trash-alt me-2"></i>삭제하기</p>
      </div>
      <div v-if="isFinish && state.user.email === compEmail && calc===0">
        <p id="delete" @click="clickCalc(fundingNo)"><i class="fas fa-calculator me-2"></i>정산하기</p>
      </div>
    </div>
    <h1>{{ name }}</h1>
  </div>

  <div class="funding-content-box">
    <p id="content-detail">펀딩 정보</p>
    <hr id="content-line">
    <p id="date"><i class="far fa-calendar-alt me-2"></i>{{ due }}</p>
    <div class="funding-detail-bar">
      <div class="d-flex justify-content-between align-items-end">
        <p>{{ parseInt((totalAmount / amount) * 100) }} %</p>
        <p id="status">{{ totalAmount }} / {{ amount }} B</p>
      </div>
      <div>
        <progress class="progress-bar" :value="totalAmount" :max="amount"></progress>
      </div>
      <p id="total-count">참여 인원 : {{ totalCount }} 명</p>
    </div>
    <p id="content-detail">펀딩 소개</p>
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
          <img class="funding-img" :src="`http://j5d101.p.ssafy.io/${i}`" alt="">
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>

  <el-dialog title="펀딩 정산 내역" v-model="state.dialogVisible" width="600px" :before-close="handleClose">
    <div class="funding-confirm-box container">
          <p style="text-align:center; font-size: 23px; font-weight: 500" >{{name}}</p>
          <b>목표 베리</b>
          <p style="font-size: 15px; font-weight 1000" >{{amount}} B</p>
          <b>모금 베리</b>
          <p style="font-size: 15px; font-weight 1000" >{{totalAmount}} B</p>
          <b>입금 예정 계좌</b>
          <p style="font-size: 15px; font-weight 1000" >{{state.wallet.address}} ({{comp}})</p>
    </div>
    <p style="text-align:center; font-size: 18px; font-weight:500" v-if="totalAmount < amount">모금액 부족으로 정산이 불가능합니다.</p>
    <p style="text-align:center; font-size: 18px; font-weight:500" v-else>{{totalAmount}}B 정산 예정입니다.</p>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">취소</el-button>
        <el-button class="funding-button" @click="handleClose" v-if="totalAmount < amount">확인</el-button>
        <el-button class="funding-button" @click="clickFunding" v-else>정산하기</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import {  reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ElLoading, ElMessageBox, ElMessage } from 'element-plus'
import Web3 from 'web3'
import { MINER, BLOCKCHAIN_URL, BERRY_CONTRACT_ADDRESS } from '@/config';
import {BERRY_CONTRACT_ABI } from '@/config/ABIs.js';

export default {
  props: {
    fundingNo: String,
    comp: String,
    compEmail: String,
    name: String,
    content: String,
    amount: String,
    totalCount: String,
    totalAmount: String,
    due: String,
    img: Array,
    isFinish: Boolean,
    calc: Number,
  },
  setup(props) {
    const router = useRouter()
    const store = useStore()
    const state = reactive ({
      user: computed(() => store.getters['getUser']),
      wallet: computed(() => store.getters['getWallet']),
      dialogVisible: false,
      // date: computed(() => {
      //   return `${props.date.slice(0,4)}-${props.date.slice(5,7)}-${props.date.slice(8,10)}` 
      // }),
      // today: date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2),
    })
    const clickUpdate = (fundingNo) => {
      router.push({ name: 'Fundingupdate', params: { fundingNo: fundingNo }})
    }
    const clickDelete = (fundingNo) => {
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
          store.dispatch('Funding/fundingDelete', fundingNo)
            .then(() => {
              ElMessage.error('삭제되었습니다.')
              router.push({ name: 'Fundinglist' })
            })
        })
        .catch(() => {})
    };
    const clickCalc = (fundingNo) => {
      console.log(fundingNo)
      state.dialogVisible = true;
    }
    const handleClose = () => {
      state.dialogVisible = false;
    }

    var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL))
    // Berry smartcontract 생성
    const createBerryContract = (web3) => {
      return new web3.eth.Contract(BERRY_CONTRACT_ABI, BERRY_CONTRACT_ADDRESS)
    }
    
    const contract = createBerryContract(web3);

    const clickFunding = () => {
      const loading = ElLoading.service({
        lock: true,
        text: '요청하신 정산을 진행 중 입니다. 잠시만 기다려주세요.',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      contract.methods
        .transfer(state.wallet.address, parseInt(props.totalAmount))
        .send({from: MINER})
        .then((receipt) => {
          console.log(receipt)
          let funding_tx = {
            block_hash: `${receipt.blockHash}`,
            block_number: `${receipt.blockNumber}`,
            fromhash: receipt.from.toString().toLowerCase(),
            gas_price: `${receipt.gasUsed}`,
            hash: `${receipt.transactionHash}`,
            r: `${receipt.root}`,
            tohash: receipt.to.toString().toLowerCase(),
            transaction_index: `${receipt.transactionIndex}`,
          }
          store.dispatch('Tx/transactionCreate', funding_tx);
          web3.eth.getBalance(state.wallet.address)
          .then(bal => {
            let b = parseInt(state.wallet.berry)+parseInt(props.totalAmount);
            let wallet = {
              address: state.wallet.address,
              balance: parseInt(bal/1000000000000000000),
              berry: b,
              email: state.user.email,
            }
            console.log(wallet); 
            store.dispatch('compwalletUpdate', wallet);
            loading.close();
          })
        }).catch(()=>{
          loading.close();
          ElMessage.error('정산이 취소되었습니다. 다시 한 번 시도해주세요.')
        })
    }
    return { state, clickUpdate, clickDelete, clickCalc, handleClose, clickFunding }
  },
}
</script>

<style>
.funding-title-box {
  margin: 5% 0 3% 0;
  width: 100%;
  min-height: 5vh;
  /* padding: 0 5%; */
  /* border: 1px solid black; */
}
.funding-title-box p {
  margin: 2% 0;
  font-weight: 500;
  color: #858585;
}
.funding-content-box {
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
.funding-img {
  width: 100%;
  height: 100%;
}
#content-detail {
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
.funding-title-box #edit {
  color: #409eff;
}
.funding-title-box #edit:hover {
  color: #409eff;
  cursor: pointer;
  transform: scale(1.1);
}
.funding-title-box #delete {
  color: #f56c6c;
}
.funding-title-box #delete:hover {
  color: #f56c6c;
  cursor: pointer;
  transform: scale(1.1);
}
.funding-detail-bar {
  width: 100%;
  border: 1px;
  margin-top: 5%;
  /* padding: 0 5%; */
}
.funding-detail-bar p {
  margin-bottom: 0;
  font-size: 25px;
  font-weight: 600;
}
.funding-detail-bar #status {
  font-size: 18px;
}
progress {
  -webkit-appearance: none;
  /* border-radius: 30px; */
}
::-webkit-progress-bar {
  background-color: #EDEFEF;
}
::-webkit-progress-value {
  background-color: #FF6647;
}
.progress-bar {
  margin-top: 1%;
  width: 100%;
}
.funding-content-box #date {
  font-size: 18px;
  font-weight: 600;
}
#total-count {
  font-size: 18px;
  font-weight: 500;
  margin-top: 2%;
}
</style>