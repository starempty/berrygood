<template>
  <el-dialog 
    title="베리 충전" 
    v-model="state.chargeDialog" 
    width="500px"
    @close="handleClose"
  >
    <div class="d-flex">
      <el-input v-model="state.money" id="charge-input" style="width:90%"></el-input>
      <div class="d-flex flex-column justify-content-center">
        <span class="text-center" style="font-weight:600; font-size:16px">B</span>
        <span>(천원)</span>
      </div>
    </div>
    <div class="d-flex flex-column">
      <span style="color:#f78065; font-weight:500">* 1B = 1000원</span>
      <span style="margin:1% 0; font-weight:500">* 최소 충전 금액 <span style="font-size:16px; color:#f78065;">1B</span>(1000원), 천 단위 입력만 가능합니다.</span>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button id="key-close" @click="chargeBerry">충전</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed } from 'vue'
import Web3 from 'web3'
import { MINER, BLOCKCHAIN_URL, BERRY_CONTRACT_ADDRESS } from '@/config';
import { BERRY_CONTRACT_ABI } from '@/config/ABIs.js';
import { ElMessage, ElLoading } from 'element-plus'
import { useStore } from 'vuex'

export default {
  props: {
    open: { 
      type: Boolean,
      default: false,
    },
  },
  setup(props, { emit }) {
    const store = useStore()

    const state = reactive ({
      user: computed(() => store.getters['getUser']),
      wallet: computed(() => store.getters['getWallet']),
      chargeDialog: computed(() => props.open),
      money: '',
    })
    const handleClose = () => {
      emit('closeChargeDialog')
    }
    var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL))
    // berry smartcontract 생성
    const createBerryContract = (web3) => {
      return new web3.eth.Contract(BERRY_CONTRACT_ABI, BERRY_CONTRACT_ADDRESS)
    }
    const contract = createBerryContract(web3)
    const chargeBerry = () => {
      if (state.money <= 0) {
        ElMessage.error('최소 충전 금액은 1B(1000원) 입니다.')
        emit('closeChargeDialog')
      } else {
        const loading = ElLoading.service({
          lock: true,
          text: '베리를 충전 중 입니다.',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        // 0.1 이더 충전 (gas 비용)
        const from = MINER
        const to = state.wallet.address
          web3.eth.sendTransaction({ from: from, to: to, value: web3.utils.toWei("1", "ether") })
          .then((receipt) => {
            let ether_tx = {
              block_hash: `${receipt.blockHash}`,
              block_number: `${receipt.blockNumber}`,
              fromhash: receipt.from.toString().toLowerCase(),
              gas_price: `${receipt.gasUsed}`,
              hash: `${receipt.transactionHash}`,
              r: `${receipt.root}`,
              tohash: receipt.to.toString().toLowerCase(),
              transaction_index: `${receipt.transactionIndex}`,
            }
            store.dispatch('Tx/transactionCreate', ether_tx);
            contract.methods
            .transfer(state.wallet.address, state.money)
            .send({from: MINER})
            .then((receipt) => {
              console.log(receipt)
              let berry_tx = {
                block_hash: `${receipt.blockHash}`,
                block_number: `${receipt.blockNumber}`,
                fromhash: receipt.from.toString().toLowerCase(),
                gas_price: `${receipt.gasUsed}`,
                hash: `${receipt.transactionHash}`,
                r: `${receipt.root}`,
                tohash: receipt.to.toString().toLowerCase(),
                transaction_index: `${receipt.transactionIndex}`,
              }
              store.dispatch('Tx/transactionCreate', berry_tx);
              web3.eth.getBalance(state.wallet.address).then(bal => {
                let b = Number(state.wallet.berry)+Number(state.money);
                if(state.user.role === "company"){
                  let wallet = {
                    address: state.wallet.address,
                    balance: Number(bal/1000000000000000000),
                    berry: b,
                    email: state.user.email,
                  }
                  store.dispatch('compwalletUpdate', wallet).then(()=>{
                    emit('closeChargeDialog')
                    ElMessage ({
                      message: '충전이 완료되었습니다.',
                      type: 'success'
                    })
                  })
                }else{
                  let wallet = {
                    address: state.wallet.address,
                    balance: Number(bal/1000000000000000000),
                    berry: b,
                    email: state.user.email,
                    receivecnt: Number(state.wallet.receivecnt+1)
                  }
                  store.dispatch('walletUpdate', wallet).then(()=>{
                    emit('closeChargeDialog')
                    ElMessage ({
                      message: '충전이 완료되었습니다.',
                      type: 'success'
                    })
                  })
                }
                
              })
              loading.close()
            })
            .catch((chargeErr)=>{
              console.log('charge', chargeErr)
              loading.close()
              emit('closeChargeDialog')
              ElMessage.error('충전이 취소되었습니다. 다시 한 번 시도해주세요.')
            })
        })
        .catch(etherErr=>{
          console.log('ether', etherErr)
          loading.close()
          emit('closeChargeDialog')
          ElMessage.error('충전이 취소되었습니다. 다시 한 번 시도해주세요.')
        })
      }
    }
    return { state, handleClose, chargeBerry }
  },
}
</script>
<style>
#charge-input.el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
  width: 95%;
}
#charge-input.el-input__inner:hover {
  border-bottom: 1.5px solid #c0c4cc;
}
#charge-input.el-input__inner:focus {
  border-bottom: 1px solid #f78065 !important;
}
#key-close {
  color: white;
  border: #f78065;
  background-color: #f78065;
}
</style>