<template>
  <el-dialog
    title="기부하기"
    v-model="state.dialogVisible"
    width="600px"
    :before-close="handleClose"
  >
    <div class="donation-confirm-box container">
      <el-form
        :label-position="'top'"
        v-model="state.donationInput"
      >
        <el-form-item label="기부대상">
          <el-input class="donation-input-disable" v-model="state.donationInput.comp" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="기부자">
          <div v-if="state.donationInput.hideName">
            <el-input class="donation-input-disable" v-model="state.donationInput.usernickname" :disabled="true"></el-input>
          </div>
          <div v-else>
            <el-input class="donation-input-disable" v-model="state.donationInput.username" :disabled="true"></el-input>
          </div>
        </el-form-item>
        <el-form-item label="기부 베리 (B)">
          <el-input class="donation-input-disable" v-model="state.donationValue" :disabled="true">
          </el-input>
        </el-form-item>
        <el-form-item label="응원의 메세지">
          <el-input class="donation-input-disable" v-model="state.donationInput.message" :disabled="true"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">취소</el-button>
        <el-button class="donation-button" @click="clickDonation">기부하기</el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog
    v-model="state.pwDialogVisible"
    title="비밀번호를 입력하세요."
    width="500px"
  >
    <el-input id="donation-input" v-model="state.pw" autocomplete="off" show-password></el-input>
    <template #footer>
      <span class="dialog-footer">
        <el-button id="confirm" @click="clickConfirm">확인</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
  import { reactive, computed } from 'vue';
  import { ElLoading, ElMessageBox } from 'element-plus'
  import Web3 from 'web3'
  import { MINER, BLOCKCHAIN_URL, BERRY_CONTRACT_ADDRESS } from '@/config';
  import { BERRY_CONTRACT_ABI } from '@/config/ABIs.js';
  import { useStore } from 'vuex';
  import { ElMessage } from 'element-plus'

  export default {
    props: {
      open: { 
        type: Boolean,
        default: false,
      },
      donationInput: {
        type: Object,
      },
      donationNo: Number,
    },
    setup(props, { emit }) {
      const store = useStore();
      const state = reactive ({
        dialogVisible: computed(() => props.open),
        donationInput: computed(() => props.donationInput),
        donationNo: computed(() => props.donationNo),
        donationValue: computed(() => props.donationInput.donationValue.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") + ' B'),
        wallet: computed(() => store.getters['getWallet']),
        user: computed(() => store.getters['getUser']),
        compWallet: computed(() => store.getters['getPjtwallet']),
        pwDialogVisible: false,
        pw:'',
      }) 
      console.log(state.compWallet)
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
            emit('closeDonationDialog')
          })
          .catch(() => {})
      }
    var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL))
    // Berry smartcontract 생성
    const createBerryContract = (web3) => {
      return new web3.eth.Contract(BERRY_CONTRACT_ABI, BERRY_CONTRACT_ADDRESS)
    }
    const contract = createBerryContract(web3)
    const clickDonation = () => {
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
        loading.close()
        ElMessage.error('이더 충전 중 문제가 발생했습니다. 다시 한 번 시도해주세요.')
      })
    }
    const clickConfirm = () =>{
      state.pwDialogVisible = false;
      const loading = ElLoading.service({
          lock: true,
          text: '계정 잠금 해제 후 요청하신 기부를 진행 중 입니다. 잠시만 기다려주세요.',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        console.log(state.wallet.address, state.pw)
        console.log(state.compWallet);
      web3.eth.personal.unlockAccount(`${state.wallet.address}`, `${state.pw}`).then(()=>{
        
          contract.methods
          .transfer(`${state.compWallet.address}`, parseInt(state.donationInput.donationValue))
          .send({from: `${state.wallet.address}`})
          .then((receipt) => {
            console.log(receipt)
            let donate_tx = {
              block_hash: `${receipt.blockHash}`,
              block_number: `${receipt.blockNumber}`,
              fromhash: receipt.from.toString().toLowerCase(),
              gas_price: `${receipt.gasUsed}`,
              hash: `${receipt.transactionHash}`,
              r: `${receipt.root}`,
              tohash: receipt.to.toString().toLowerCase(),
              transaction_index: `${receipt.transactionIndex}`,
            }
            store.dispatch('Tx/transactionCreate', donate_tx);
            let participant = {
              amount: state.donationInput.donationValue,
              projectId: state.donationNo,
              userEmail: state.user.email,
              message: state.donationInput.message,
            }
            console.log(participant);
            store.dispatch('Donation/participantRegister', participant);

            web3.eth.getBalance(state.wallet.address)
            .then(bal => {
              let b = parseInt(state.wallet.berry)-parseInt(state.donationInput.donationValue);
              let wallet = {
                address: state.wallet.address,
                balance: parseInt(bal/1000000000000000000),
                berry: b,
                email: state.user.email,
                receivecnt: Number(state.wallet.receivecnt)
              }
              console.log(wallet); 
              store.dispatch('walletUpdate', wallet);

              web3.eth.getBalance(state.compWallet.address)
              .then(cbal => {
                let b = parseInt(state.compWallet.berry)+parseInt(state.donationInput.donationValue);
                let wallet = {
                  address: state.compWallet.address,
                  balance: parseInt(cbal/1000000000000000000),
                  berry: b,
                  email: state.compWallet.comp.email,
                }
                console.log(wallet); 
                store.dispatch('compwalletUpdate', wallet)
                emit('closeDonationDialog')
              })
            })
            loading.close()
          }).catch((txErr)=>{
            console.log('tx', txErr)
            emit('closeDonationDialog')
            loading.close()
            ElMessage.error('기부가 취소되었습니다. 다시 한 번 시도해주세요')
          })
        state.pw = '';
      }).catch((authenticErr) => {
        console.log('authentic', authenticErr);
        emit('closeDonationDialog')
        loading.close()
        ElMessage.error('지갑 비밀번호를 확인하세요.')
      })
      state.pw = '';
    }
      return { 
        state, 
        handleClose, 
        clickDonation,
        clickConfirm 
      }
    },
  }
</script>

<style>
.donation-confirm-box .donation-input-disable .el-input__inner {
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
.dialog-footer .el-button.el-button--default.donation-button {
  background-color: #f78065 !important;
  border-color: #f78065;
  color: white;
}
.dialog-footer .el-button.el-button--default.donation-button:hover {
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
#donation-input.el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
  width: 95%;
}
#donation-input.el-input__inner:hover {
  border-bottom: 1.5px solid #c0c4cc;
}
#donation-input.el-input__inner:focus {
  border-bottom: 1px solid #f78065 !important;
}
</style>

