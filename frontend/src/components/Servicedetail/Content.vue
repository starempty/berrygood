<template>
  <div class="service-title-box">
    <!-- <p>{{ volunteer }}</p> -->
    <div class="d-flex justify-content-between align-items-center">
      <p>{{ user.name }}</p>
      <div class="d-flex" v-if="state.user.email === user.email && !isFinish">
        <p class="me-3" id="edit" @click="clickUpdate(serviceNo)"><i class="far fa-edit me-1"></i>수정하기</p>
        <p id="delete" @click="clickDelete(serviceNo)"><i class="far fa-trash-alt me-1"></i>삭제하기</p>
      </div>
    </div>
    <h1>{{ name }}</h1>
  </div>
  <div class="service-content-box">
    <p id="content-detail">활동 정보</p>
    <hr id="content-line">
    <p id="date"><i class="far fa-calendar-alt me-2"></i>{{ state.date }}<i class="far fa-clock ms-3 me-2"></i>{{ state.time }}</p>
    <div class="service-detail-bar">
      <div class="d-flex justify-content-between align-items-end">
        <p>{{ parseInt((state.totalCount / num) * 100) }} %</p>
        <p id="status">{{ state.totalCount }} / {{ num }} 명</p>
      </div>
      <div>
        <progress class="progress-bar" :value="state.totalCount" :max="num"></progress>
      </div>
    </div>
    <div class="service-participate d-flex justify-content-center mt-5" v-if="state.user.role == 'user'">
      <p class="join me-4" @click="clickJoin"><i class="fas fa-check me-2"></i>참가 하기</p>
      <p class="delete ms-4" @click="clickCancle"><i class="fas fa-times me-2"></i>참가 취소</p>
    </div>
    <p id="content-detail">활동 소개</p>
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
        <el-carousel-item v-for="i in imgPath" :key="i">
          <img class="service-img" :src="`http://j5d101.p.ssafy.io/${i}`" alt="">
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
  <el-dialog
    v-model="state.dialogVisible"
    title="비밀번호를 입력하세요."
    width="500px"
  >
    <el-input v-model="state.pw" autocomplete="off" show-password></el-input>
    <template #footer>
      <span class="dialog-footer">
        <el-button id="confirm" @click="clickConfirm">확인</el-button>
      </span>
    </template>
  </el-dialog>
  <el-dialog
    v-model="state.cancelDialogVisible"
    title="비밀번호를 입력하세요."
    width="500px"
  >
    <el-input v-model="state.pw" autocomplete="off" show-password></el-input>
    <template #footer>
      <span class="dialog-footer">
        <el-button id="confirm" @click="clickConfirmCancel">확인</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElLoading, ElMessage, ElMessageBox } from 'element-plus'
import Web3 from 'web3'
import { MINER, BLOCKCHAIN_URL, SERVICE_CONTRACT_ADDRESS } from '@/config';
import { SERVICE_CONTRACT_ABI } from '@/config/ABIs.js';
import { BASE_URL } from '@/config/constants';
import axios from 'axios'

export default {
  props: {
    name: String,
    content: String,
    date: String,
    num: String,
    totalCount: String,
    imgPath: Array,
    user: Object,
    serviceNo: String,
    isFinish: Boolean,
  },
  setup(props) {
    const store = useStore()
    const router = useRouter()

    const state = reactive ({
      wallet: computed(() => store.getters['getWallet']),
      user: computed(() => store.getters['getUser']),
      date: computed(() => {
        return `${props.date.slice(0,4)}-${props.date.slice(5,7)}-${props.date.slice(8,10)}` 
      }),
      time: computed(() => {
        return `${props.date.slice(11,13)}:${props.date.slice(14,16)}`
      }),
      totalCount: props.totalCount,
      dialogVisible: false,
      pw: '',
      cancelDialogVisible: false,
    })

    var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL))
    // service smartcontract 생성
    const createServiceContract = (web3) => {
      return new web3.eth.Contract(SERVICE_CONTRACT_ABI, SERVICE_CONTRACT_ADDRESS)
    }
    const contract = createServiceContract(web3)


    const clickConfirm = () => {
      state.dialogVisible = false
      clickParticipate();
    }
    const clickConfirmCancel = () => {
      state.cancelDialogVisible = false
      clickUnparticipate();
    }

    const clickJoin = () => {
      const loading = ElLoading.service({
        lock: true,
        text: '이더를 충전 중 입니다. 잠시만 기다려주세요.',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      web3.eth.sendTransaction({ from: MINER, to: state.wallet.address, value: web3.utils.toWei("1", "ether") })
        .then(() => {
          state.dialogVisible = true
          loading.close()
        })
        .catch(etherErr=>{
          console.log('ether', etherErr)
          loading.close()
          ElMessage.error('이더 충전 중 문제가 발생했습니다. 다시 한 번 시도해주세요.')
        })
    }
    const clickCancle = () => {
      const loading = ElLoading.service({
        lock: true,
        text: '이더를 충전 중 입니다. 잠시만 기다려주세요.',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      web3.eth.sendTransaction({ from: MINER, to: state.wallet.address, value: web3.utils.toWei("1", "ether") })
        .then(() => {
          state.cancelDialogVisible = true
          loading.close()
        }).catch(etherErr => {
          console.log('etherErr', etherErr)
          loading.close()
          ElMessage.error('이더 충전 중 문제가 발생했습니다. 다시 한 번 시도해주세요.')
        })
    }


    const clickParticipate = () => {
      const loading = ElLoading.service({
        lock: true,
        text: '계정 잠금 해제 후 요청하신 신청을 진행 중 입니다. 잠시만 기다려주세요.',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      web3.eth.personal.unlockAccount(state.wallet.address, state.pw)
        .then(()=>{
          state.pw = ''
          contract.methods
            .registerService(props.serviceNo)
            .send({from: state.wallet.address})
            .then((receipt) => {
              let service_tx = {
                block_hash: `${receipt.blockHash}`,
                block_number: `${receipt.blockNumber}`,
                fromhash: receipt.from.toString().toLowerCase(),
                gas_price: `${receipt.gasUsed}`,
                hash: `${receipt.transactionHash}`,
                r: `${receipt.root}`,
                tohash: receipt.to.toString().toLowerCase(),
                transaction_index: `${receipt.transactionIndex}`,
              }
              store.dispatch('Tx/transactionCreate', service_tx);

              store.dispatch('Service/participantRegister', {
                amount: 1,
                projectId: props.serviceNo,
                userEmail: state.user.email
              })
              .then(() => {
                axios.get(`${BASE_URL}/volunteer/${props.serviceNo}`)
                .then(({ data }) => {
                  state.totalCount = data.totalCnt
                  loading.close()
                  state.dialogVisible2 = false
                  state.pw = ''
                  state.pwConf = ''
                })
              })
              loading.close()  
              router.go();
            }).catch((registerErr)=>{
              console.log('register', registerErr)
              loading.close()
              ElMessage.error('신청이 취소되었습니다. 다시 한 번 시도해주세요')
            })
        }).catch((authenErr) => {
          console.log('authentic', authenErr)
          loading.close()
          ElMessage.error('지갑 비밀번호를 확인하세요.')
        })
    }
    
    const clickUnparticipate = () => {
      const loading = ElLoading.service({
        lock: true,
        text: '계정 잠금 해제 후 요청하신 신청을 진행 중 입니다. 잠시만 기다려주세요.',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
        web3.eth.personal.unlockAccount(state.wallet.address, state.pw)
        .then(()=>{
          state.pw = ''
          contract.methods
            .cancelService(props.serviceNo)
            .send({from: state.wallet.address})
            .then((receipt) => {
              let service_tx = {
                block_hash: `${receipt.blockHash}`,
                block_number: `${receipt.blockNumber}`,
                fromhash: receipt.from.toString().toLowerCase(),
                gas_price: `${receipt.gasUsed}`,
                hash: `${receipt.transactionHash}`,
                r: `${receipt.root}`,
                tohash: receipt.to.toString().toLowerCase(),
                transaction_index: `${receipt.transactionIndex}`,
              }
              store.dispatch('Tx/transactionCreate', service_tx);
              store.dispatch('Service/participantDelete', {
                amount: 1,
                projectId: props.serviceNo,
                userEmail: state.user.email
              })
              .then(() => {
                axios.get(`${BASE_URL}/volunteer/${props.serviceNo}`)
                .then(({ data }) => {
                  state.totalCount = data.totalCnt
                  state.dialogVisible3 = false
                  loading.close()
                  state.pw = ''
                  state.pwConf = ''
                })
              })
              loading.close()
              router.go();
            }).catch((registerErr)=>{
              console.log('register', registerErr)
              loading.close()
              ElMessage.error('취소 신청이 취소되었습니다. 다시 한 번 시도해주세요')
            })
        }).catch(authenticErr=>{
          console.log('authentic', authenticErr)
          loading.close()
          ElMessage.error('지갑 비밀번호를 확인하세요.')
        })
        
    }
    const clickUpdate = (serviceNo) => {
      router.push({ name: 'Serviceupdate', params: { serviceNo: serviceNo }})
    }
    const clickDelete = (serviceNo) => {
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
          store.dispatch('Service/serviceDelete', serviceNo)
            .then(() => {
              ElMessage.error('삭제되었습니다.')
              router.push({ name: 'Servicelist' })
            })
        })
        .catch(() => {})
    }
    onMounted(() => {
      store.dispatch('Service/serviceDetail', props.serviceNo)
      .then(({ data }) => {
        state.totalCount = data.totalCnt
      })
    })

    return { 
      state, 
      clickJoin, 
      clickCancle, 
      clickConfirm, 
      clickConfirmCancel,
      clickParticipate, 
      clickUnparticipate, 
      clickUpdate, 
      clickDelete, 
    }
  },
}
</script>

<style>
.service-title-box {
  margin: 5% 0 3% 0;
  width: 100%;
  min-height: 5vh;
  /* padding: 0 5%; */
  /* border: 1px solid black; */
}
.service-title-box p {
  margin: 2% 0;
  font-weight: 500;
  color: #858585;
}
.service-participate .fa-check {
  color: #67c23a;
}
.service-participate .join {
  font-size: 18px;
}
.service-participate .join:hover {
  cursor: pointer;
  color: #67c23a;
  transform: scale(1.1);
  font-weight: 500;
}
.service-participate .delete {
  font-size: 18px;
}
.service-participate .delete:hover {
  cursor: pointer;
  color: rgb(255, 64, 64);
  transform: scale(1.1);
  font-weight: 500;
}
.service-content-box {
  width: 100%;
  /* border: 1px solid black; */
  margin-bottom: 2vh;
  min-height: 43vh;
  /* padding: 0 5%; */
}
.service-content-box #content-detail {
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
.carousel-box {
  width: 60%;
  min-width: 600px;
  margin-bottom: 8%;
}
.service-img {
  width: 100%;
  height: 100%;
}
.service-title-box #edit {
  color: #409eff;
}
.service-title-box #edit:hover {
  color: #409eff;
  cursor: pointer;
  transform: scale(1.1);
}
.service-title-box #delete {
  color: #f56c6c;
}
.service-title-box #delete:hover {
  color: #f56c6c;
  cursor: pointer;
  transform: scale(1.1);
}
.service-detail-bar {
  width: 100%;
  border: 1px;
  margin-top: 5%;
  /* padding: 0 5%; */
}
.service-detail-bar p {
  margin-bottom: 0;
  font-size: 25px;
  font-weight: 600;
}
.service-detail-bar #status {
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
.service-content-box #date {
  font-size: 18px;
  font-weight: 600;
}
.el-dialog__footer .dialog-footer #confirm {
  color: white;
  background-color: #f78065;
  border-color: #f78065;
}
</style>