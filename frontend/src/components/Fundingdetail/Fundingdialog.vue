<template>
  <el-dialog
    title="펀딩하기"
    v-model="state.dialogVisible"
    width="600px"
    :before-close="handleClose"
  >
    <div class="funding-confirm-box container">
      <el-form
        :label-position="'top'"
        v-model="state.fundingInput"
      >
        <el-form-item label="펀딩 대상">
          <el-input class="funding-input-disable" v-model="state.fundingInput.comp" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="이름">
          <div v-if="state.fundingInput.hideName">
            <el-input class="funding-input-disable" v-model="state.fundingInput.usernickname" :disabled="true"></el-input>
          </div>
          <div v-else>
            <el-input class="funding-input-disable" v-model="state.fundingInput.username" :disabled="true"></el-input>
          </div>
        </el-form-item>
        <!-- <el-form-item class="col-2 d-flex align-items-center" v-if="state.fundingInput.fundingValue">
            {{ $filters.money(state.fundingInput.fundingValue) }}
          </el-form-item> -->
        <el-form-item label="펀딩 베리 (B)">
          <el-input class="funding-input-disable" v-model="state.fundingValue" :disabled="true">
          </el-input>
        </el-form-item>
      </el-form>
      <el-dialog
    v-model="state.pwDialogVisible"
    title="비밀번호를 입력하세요."
    width="500px"
  >
    <el-input id="funding-input" v-model="state.pw" autocomplete="off" show-password></el-input>
    <template #footer>
      <span class="dialog-footer">
        <el-button id="confirm" @click="clickConfirm">확인</el-button>
      </span>
    </template>
  </el-dialog>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">취소</el-button>
        <el-button class="funding-button" @click="clickFunding">펀딩하기</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
  import { reactive, computed } from 'vue';
  import { ElLoading, ElMessageBox } from 'element-plus'
  import Web3 from 'web3'
  import { MINER, BLOCKCHAIN_URL, FUNDING_CONTRACT_ADDRESS, BERRY_CONTRACT_ADDRESS } from '@/config';
  import { FUNDING_CONTRACT_ABI, BERRY_CONTRACT_ABI } from '@/config/ABIs.js';
  import { useStore } from 'vuex';
  import { ElMessage } from 'element-plus'


  export default {
    props: {
      open: { 
        type: Boolean,
        default: false,
      },
      fundingInput: {
        type: Object,
      },
      fundingNo:Number
    },
    setup(props, { emit }) {
      const store = useStore();
      const state = reactive ({
        user: computed(() => store.getters['getUser']),
        wallet: computed(() => store.getters['getWallet']),
        dialogVisible: computed(() => props.open),
        fundingInput: computed(() => props.fundingInput),
        compWallet: computed(() => store.getters['getPjtwallet']),
        fundingNo: computed(() => props.fundingNo),
        fundingValue: computed(() => props.fundingInput.fundingValue.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + ' B'),
        pw:'',
        pwDialogVisible:false,
      })      
      const handleClose = () => {
        ElMessageBox
          .confirm(
            '정말 취소하시겠습니까?',
            '취소',
            {
              confirmButtonText: '네',
              cancelButtonText: '아니요',
            }
          )
          .then(() => {
            emit('closeFundingDialog')
          })
          .catch(() => {})
      }

      var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL))
      // Funding smartcontract 생성
      const createFundingContract = (web3) => {
        return new web3.eth.Contract(FUNDING_CONTRACT_ABI, FUNDING_CONTRACT_ADDRESS)
      }
      const createBerryContract = (web3) => {
        return new web3.eth.Contract(BERRY_CONTRACT_ABI, BERRY_CONTRACT_ADDRESS)
      }
      
      const contract = createFundingContract(web3);
      const bcontract = createBerryContract(web3);

      const clickFunding = () => {
        const loading = ElLoading.service({
          lock: true,
          text: '이더를 충전 중 입니다. 잠시만 기다려주세요.',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        web3.eth.sendTransaction({ from: MINER, to: state.wallet.address, value: web3.utils.toWei("1", "ether") })
        .then(() => {
          loading.close();
          state.pwDialogVisible = true;
        }).catch(etherErr=>{
          console.log('ether', etherErr)
          loading.close();
          ElMessage.error('이더 충전 중 문제가 발생했습니다. 다시 한 번 시도해주세요.')
        })
      }

      const clickConfirm = () => {
          state.pwDialogVisible = false;
          const loading = ElLoading.service({
            lock: true,
            text: '계정 잠금 해제 후 요청하신 펀딩을 진행 중 입니다. 잠시만 기다려주세요.',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          })
          let participant = {
            amount: parseInt(state.fundingInput.fundingValue),
            projectId: parseInt(state.fundingNo),
            userEmail: state.user.email,
          }
          console.log(participant);
          store.dispatch('Funding/participantRegister', participant).then(() => {
            web3.eth.personal.unlockAccount(state.wallet.address, state.pw).then(()=>{
            console.log(state.fundingInput.fundingValue);
              contract.methods
              .offerFunding(parseInt(state.fundingInput.fundingValue))
              .send({from: `${state.wallet.address}`})
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
                bcontract.methods
                .transfer(state.compWallet.address, state.fundingInput.fundingValue)
                .send({from: `${state.wallet.address}`})
                .then((res) => {
                  console.log(res)
                  web3.eth.getBalance(state.wallet.address)
                  .then(bal => {
                    let b = parseInt(state.wallet.berry)-parseInt(state.fundingInput.fundingValue);
                    let wallet = {
                      address: state.wallet.address,
                      balance: parseInt(bal/1000000000000000000),
                      berry: b,
                      email: state.user.email,
                      receivecnt: Number(state.wallet.receivecnt)
                    }
                    console.log(wallet); 
                    store.dispatch('walletUpdate', wallet);
                  })

                  web3.eth.getBalance(state.compWallet.address)
                  .then(cbal => {
                    let b = parseInt(state.compWallet.berry)+parseInt(state.fundingInput.fundingValue);
                    let wallet = {
                      address: state.compWallet.address,
                      balance: parseInt(cbal/1000000000000000000),
                      berry: b,
                      email: state.compWallet.comp.email,
                    }
                    console.log(wallet); 
                    store.dispatch('compwalletUpdate', wallet)
                    loading.close()
                    emit('closeFundingDialog')
                  })
                }).catch(()=>{
                  loading.close();
                  ElMessage.error('펀딩 참가가 취소되었습니다. 다시 한 번 시도해주세요.')
                })
              }).catch((offerFundingErr)=>{
                console.log('offerFunding', offerFundingErr)
                loading.close();
                ElMessage.error('펀딩 참가가 취소되었습니다. 다시 한 번 시도해주세요.')
              })
            }).catch((authenticErr) => {
              console.log('authentic', authenticErr);
              loading.close();
              ElMessage.error('지갑 비밀번호를 확인하세요.')
            })
          })
      }
      return { state, handleClose, clickFunding, clickConfirm }
    },
  }
</script>

<style>
.funding-confirm-box .funding-input-disable .el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #f78065;
  margin-bottom: 1vh;
  padding: 0;
  color: black;
}
.dialog-footer .el-button.el-button--default {
  background-color: #ffc57d;
  color: gray;
  border-color: #ffc57d;
}
.dialog-footer .el-button.el-button--default:hover {
  opacity: 0.8;
}
.dialog-footer .el-button.el-button--default.funding-button {
  background-color: #f78065 !important;
  border-color: #f78065;
  color: white;
}
.dialog-footer .el-button.el-button--default.funding-button:hover {
  opacity: 0.8;
}
.el-button.el-button--default.el-button--small {
  background-color: #f56c6c;
  border-color: #f56c6c;
  color: white;
}
.el-button.el-button--default.el-button--small:hover {
  background-color: #f56c6c;
  border-color: #f56c6c;
  color: white;
  opacity: 0.6;
}
.el-button.el-button--default.el-button--small.el-button--primary {
  background-color: #f78065 !important;
  border-color: #f78065;
}
.el-button.el-button--default.el-button--small.el-button--primary:hover {
  opacity: 0.8;
}
#funding-input.el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
  width: 95%;
}
#funding-input.el-input__inner:hover {
  border-bottom: 1.5px solid #c0c4cc;
}
#funding-input.el-input__inner:focus {
  border-bottom: 1px solid #f78065 !important;
}
</style>

