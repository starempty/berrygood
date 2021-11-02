<template>
  <div class="blood-background">
  </div>
  <div class="blood-input-view">
    <h2 class="mb-5 animate__animated animate__fadeIn animate__delay-1s"><b>헌혈증 등록</b></h2>
    <div class="blood-input-box container animate__animated animate__fadeIn animate__delay-1s">
      <el-form
        :label-position="'top'"
        v-model="state.bloodInput"
      >
        <el-form-item class="animate__animated animate__fadeInUp name" label="이름">
          <span id="name">{{ state.bloodInput.username }}</span>
          <!-- <el-input class="blood-input-disable" v-model="state.bloodInput.username" :disabled="true"></el-input> -->
        </el-form-item>
        <div class="row">
          <el-form-item class="col animate__animated animate__fadeInUp" label="헌혈 종류">
            <el-radio-group v-model="state.condition">
              <el-radio label="a">전혈헌혈</el-radio>
              <el-radio label="b">성분헌혈</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item v-if="state.condition === 'a'" class="col animate__animated animate__fadeInUp blood-select" label="전혈헌혈">
            <el-select v-model="state.bloodInput.kind" class="w-100" placeholder="전혈헌혈">
              <el-option
                v-for="i in state.options.slice(0, 2)"
                :key="i"
                :value="i"
              >
              </el-option>
            </el-select>
            <!-- <el-radio-group v-model="state.bloodInput.kind">
              <el-radio label="320mL">320mL</el-radio>
              <el-radio label="400mL">400mL</el-radio>
            </el-radio-group> -->
          </el-form-item>
          <el-form-item v-if="state.condition === 'b'" class="col animate__animated animate__fadeInUp blood-select" label="성분헌혈">
            <el-select v-model="state.bloodInput.kind" class="w-100" placeholder="성분헌혈">
              <el-option
                v-for="i in state.options.slice(-3)"
                :key="i"
                :value="i"
              >
              </el-option>
            </el-select>
            <!-- <el-radio-group v-model="state.bloodInput.kind">
              <el-radio label="혈소판">혈소판</el-radio>
              <el-radio label="혈장">혈장</el-radio>
              <el-radio label="혈소판혈장">혈소판혈장</el-radio>
            </el-radio-group> -->
          </el-form-item>
        </div>
        <div class="row">
          <el-form-item class="col animate__animated animate__fadeInUp" label="증서 번호">
            <el-input class="blood-input" v-model="state.bloodInput.num"></el-input>
          </el-form-item>
          <el-form-item class="col animate__animated animate__fadeInUp blood-select" label="헌혈 일자">
            <div>
              <el-select v-model="state.year" placeholder="년" @change="dateChange()">
                <el-option
                  v-for="i in state.yearCount"
                  :key="i"
                  :value="state.startYear - (i - 1)"
                >
                </el-option>
              </el-select>
            </div>
            <div class="mx-4">
              <el-select v-model="state.month" placeholder="월" @change="dateChange()">
                <el-option 
                  v-for="month in 12"
                  :key="month"
                  :value="month"
                >
                {{ month }}
                </el-option>
              </el-select>
            </div>
            <div>
              <el-select v-model="state.day" placeholder="일" @change="dateChange()">
                <el-option 
                  v-for="day in 31"
                  :key="day"
                  :value="day"
                >
                {{ day }}
                </el-option>
              </el-select>
            </div>
          </el-form-item>
        </div>
        <el-form-item 
          class="register-btn animate__animated animate__fadeInUp" 
          v-show="state.bloodInput.username && state.bloodInput.num && state.bloodInput.kind && state.bloodInput.date"
        >
          <el-button @click="registerBlood">등록하기</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
  <Footer/>
</template>

<script>
import { reactive, computed, onMounted } from 'vue'
import { useStore } from 'vuex'
import Web3 from 'web3';
import { MINER, BLOCKCHAIN_URL, BLOOD_CONTRACT_ADDRESS } from '@/config';
import { BLOOD_CONTRACT_ABI } from '@/config/ABIs.js';
import { ElMessage, ElLoading } from 'element-plus';
import { useRouter } from 'vue-router'
import Footer from "@/views/Footer.vue"

export default {
  components : {
    Footer,
  },
  setup() {
    const store = useStore()
    const router = useRouter()
    const state = reactive ({
      bloodInput: {
        username: '',
        num: '',
        kind: '',
        date: '',
      },
      user: computed(() => store.getters['getUser']),
      startYear: 2021,
      yearCount: 122,
      year: '',
      month: '',
      day: '',
      condition: '',
      options: [ '전혈 320mL', '전혈 400mL', '혈소판성분헌혈', '혈장성분헌혈', '혈소판혈장성분헌혈' ],
      wallet: computed(() => store.getters['getWallet']),
    })

    const createBloodContract = (web3) => {
      return new web3.eth.Contract(BLOOD_CONTRACT_ABI, BLOOD_CONTRACT_ADDRESS)
    }
    
    const web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL))
    const bcontract = createBloodContract(web3);
    const registerBlood = () => {
      const loading = ElLoading.service({
        lock: true,
        text: '헌혈증 정보를 블록체인에 저장 중 입니다. 잠시만 기다려주세요.',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
            bcontract.methods
            .setBloodCard(state.wallet.address, state.bloodInput.num)
            .send({from: MINER})
            .then((receipt) => {
                store.dispatch('Blood/blood', { 
                  email: `${state.user.email}`, 
                  num: `${state.bloodInput.num}`, 
                  kind: `${state.bloodInput.kind}`, 
                  date: `${state.bloodInput.date}` 
                })
                let blood_tx = {
                  block_hash: `${receipt.blockHash}`,
                  block_number: `${receipt.blockNumber}`,
                  fromhash: state.wallet.address.toString().toLowerCase(),
                  gas_price: `${receipt.gasUsed}`,
                  hash: `${receipt.transactionHash}`,
                  r: `${receipt.root}`,
                  tohash: receipt.to.toString().toLowerCase(),
                  transaction_index: `${receipt.transactionIndex}`,
                }
                store.dispatch('Tx/transactionCreate', blood_tx);
                loading.close();
                ElMessage ({
                    message: '헌혈증이 등록되었습니다.',
                    type: 'success'
                })
                router.push({ name: 'Mypage' })
            })
            .catch((err) => {
              console.log(err)
              loading.close();
              ElMessage.error('헌혈증 등록에 실패하였습니다.')
            })
    }

    const dateChange = () => {     
      state.bloodInput.date = `${state.year}-${('0' + state.month).slice(-2)}-${('0' + state.day).slice(-2)}`
    }

    onMounted(() => {
        state.bloodInput.username = state.user.name
    })
    return { state, registerBlood, dateChange, web3, bcontract }
  },
}
</script>

<style>
.blood-background {
  width: 100%;
  height: 100%;
  /* background-color: #dcdfe6; */
  background-size: cover;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
  opacity: 0.3;
}
.blood-input-view {
  width: 100%;
  height: calc(100vh - 140px);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.blood-input-box {
  max-width: 700px !important;
  border: 2px solid #f78065;
  border-radius: 15px;
  padding: 4vh 4vw 2vh 4vw !important;
  background-color: white;
}
.el-form--label-top .name .el-form-item__label {
  margin-bottom: 0 !important;
}
.el-form-item__content #name {
  font-weight: 600;
  font-size: 16px;
}
.el-form--label-top .el-form-item__label {
  padding: 0;
  line-height: 1vh;
  font-size: 12px;
  margin-bottom: 2px;
}
.blood-input-disable .el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
}
.blood-input-disable .el-input__inner {
  background-color: white !important;
}
.blood-input .el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
}
.blood-input .el-input__inner:hover {
  border-bottom: 1.5px solid #c0c4cc;
}
.blood-input .el-input__inner:focus {
  border-bottom: 1px solid #f78065;
}
.blood-select .el-form-item__content {
  display: flex;
  justify-content: flex-start;
}
.blood-select .el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
}
.blood-select .el-input__inner:hover {
  border-bottom: 1.5px solid #c0c4cc;
}
.blood-select .el-input__inner:focus {
  border-bottom: 1px solid #f78065;
}
.blood-select .el-select .el-input.is-focus .el-input__inner {
  border-color: #f78065;
}
.el-popper__arrow {
  display: none;
}
.register-btn .el-form-item__content {
  display: flex;
  justify-content: center;
}
.register-btn .el-button {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
}
.register-btn .el-button:hover {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  opacity: 0.8;
}
.custom-select select {
  word-wrap: normal;
  border: none;
  height: 39px;
  border-bottom: 1px solid #dcdfe6;
  padding: 0 10px;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  text-align: center;
}
.el-radio__input.is-checked .el-radio__inner {
  border-color: #f78065;
  background: #f78065;
}
.el-radio__inner:hover {
  border-color: #f78065;
}
.el-radio__input.is-checked+.el-radio__label {
  color: #f78065;
}
.el-radio__label:hover {
  color: #f78065;
}
</style>