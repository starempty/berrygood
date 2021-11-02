<template>
  <div class="funding-create-box">
    <el-form
      :label-position="'top'"
      :rules="state.rules"
      :model="state.fundingCreate"
      v-model="state.fundingCreate"
    >
      <el-form-item prop="name" label="펀딩 이름">
        <el-input class="funding-input" v-model="state.fundingCreate.name"></el-input>
      </el-form-item>
      <div class="row">
        <el-form-item label="펀딩 마감 기한 (1달)" class="col">
          <el-input class="funding-input" v-model="state.fundingCreate.due" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item prop="amount" label="목표 베리 (B)" class="col">
          <el-input class="funding-input" v-model="state.fundingCreate.amount"></el-input>
        </el-form-item>
      </div>
      <el-form-item label="펀딩 소개">
        <editor
          v-model="state.fundingCreate.content"
          api-key="tk5btofsrwela5rs0fweq2p0bwoiry8iyiidu1gqly2eyz4n"
          :init="{
            language: 'ko_KR',
            height: 500,
            menubar: 'favs file edit view insert format tools table help',
            plugins: [
            'advlist autolink link lists charmap print preview hr anchor pagebreak',
            'searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime nonbreaking',
            'table emoticons template paste help'
            ],
            toolbar:
            'undo redo | styleselect | bold italic forecolor backcolor emoticons | alignleft aligncenter alignright alignjustify | ' +
            'bullist numlist outdent indent | link image | print preview media fullpage | ' +
            'help'
          }"
        />
      </el-form-item>
      <!-- <input @change="fileSelect()" id="upload" type="file" accept="image/*" multiple/> -->
      <input type="file" name="file" accept="image/*" id="upload" @change="fileSelect()">
      <label for="upload" class="btn uploadBtn">사진 업로드<i class="fas fa-plus mx-2"></i></label>
      <div class="upload">
        <div v-for="(file, i) in state.fundingCreate.files" :key="i" class="img-list animate__animated animate__fadeIn">
          <div id="img">
            <i class="far fa-image me-2"></i>
            <span id="fileName">{{ file[0].name }}</span>
          </div>
          <i class="fas fa-times" @click="fileDelete(i)"></i>
        </div>
      </div>
      <el-form-item class="funding-submit">
        <el-button @click="clickCreate">작성 완료</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { reactive, computed } from 'vue'
import { ElLoading, ElMessage } from 'element-plus'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import Editor from '@tinymce/tinymce-vue'
 import Web3 from 'web3'
  import { MINER, BLOCKCHAIN_URL, FUNDING_CONTRACT_ADDRESS } from '@/config';
  import { FUNDING_CONTRACT_ABI } from '@/config/ABIs.js';

export default {
  components: {
    editor: Editor,
  },
  setup() {
    const store = useStore()
    const router = useRouter()
    const today = new Date()
    const end = new Date(today.setMonth(today.getMonth() + 2))
    // const amountValid = /^[0-9]/g

    const state = reactive ({
      fundingCreate: {
        name: '',
        due: end.getFullYear() + '-' + ('0' + end.getMonth()).slice(-2) + '-' + ('0' + end.getDate()).slice(-2),
        amount: '',
        files: [],
        content: '',
      },
      user: computed(() => store.getters['getUser']),
      wallet: computed(() => store.getters['getWallet']),
      rules: {
        name: [
          { required: true, message: "필수 항목입니다.", trigger: "blur" }
        ],
        amount: [
          { required: true, message: "필수 항목입니다.", trigger: "blur" },
          {
            pattern: /^[0-9]+$/,
            message:
              "숫자만 입력 가능합니다.",
            trigger: ["blur", "change"],
          },
        ],
      }
    })

    const fileSelect = () => {
      const uploadimg = document.getElementById("upload")
      state.fundingCreate.files.push(uploadimg.files)
    }
    const fileDelete = (idx) => {
      state.fundingCreate.files.splice(idx, 1)
    }

    var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL))
      // Funding smartcontract 생성
    const createFundingContract = (web3) => {
      return new web3.eth.Contract(FUNDING_CONTRACT_ABI, FUNDING_CONTRACT_ADDRESS)
    }
    const contract = createFundingContract(web3);
    console.log(contract)
    const clickCreate = () => {
      if (!state.fundingCreate.name) {
        ElMessage.error('펀딩명을 작성해주세요.')
      } else if (!state.fundingCreate.amount) {
        ElMessage.error('목표 베리를 입력해주세요.')
      } else if (!state.fundingCreate.content) {
        ElMessage.error('펀딩 소개글을 작성해주세요.')
      } else if (!state.fundingCreate.files[0]) {
        ElMessage.error('사진을 선택해주세요.')
      } else {
        const loading = ElLoading.service({
          lock: true,
          text: '펀딩을 게시 중 입니다. 잠시만 기다려주세요.',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        console.log(`${state.wallet.address}`, parseInt(state.fundingCreate.amount), parseInt(30))
        contract.methods
          .registerFunding(`${state.wallet.address}`, parseInt(state.fundingCreate.amount))
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

            const formData = new FormData()
                  formData.append('name', state.fundingCreate.name)
                  formData.append('content', state.fundingCreate.content)
                  formData.append('due', state.fundingCreate.due)
                  formData.append('amount', state.fundingCreate.amount)
                  for (var i=0; i < state.fundingCreate.files.length; i++) {
                    formData.append('files', state.fundingCreate.files[i][0])
                  }
                  formData.append('email', state.user.email)

            store.dispatch('Funding/fundingCreate', formData)
            .then(() => {
              loading.close()
              router.push({ name: 'Fundinglist' })
            })
          }).catch((err)=>{
            console.log('txErr', err)
          })
      }
    }
    return { state, fileSelect, fileDelete, clickCreate }
  },
}
</script>

<style>
.funding-create-box {
  width: 100%;
  min-height: 80vh;
  /* border: 1px solid black; */
  padding: 20px;
  margin-top: 12vh;
}
.funding-create-box .el-form-item__label {
  padding: 0;
  line-height: 1vh;
  font-size: 12px;
  /* margin-bottom: 1vh; */
  margin: 0 !important;
}
.funding-input .el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
}
.funding-input .el-input__inner:hover {
  border-bottom: 1.5px solid #c0c4cc;
}
.funding-input .el-input__inner:focus {
  border-bottom: 1px solid #f78065;
}
.funding-submit .el-form-item__content {
  display: flex;
  justify-content: center;
  margin-top: 5%;
}
.funding-submit .el-button {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  margin-bottom: 10vh;
}
.funding-submit .el-button:hover {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  opacity: 0.8;
}
.funding-submit .el-button:focus {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  opacity: 0.8;
}
.upload {
  /* border: 1px solid #f78065;
  min-height: 12vh;
  border-radius: 10px; */
  padding: 1% 0;
  width: 100%;
}
#upload {
  display: none;
}
.img-list {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: 0 2%;
  margin-bottom: 5px;
  
}
.img-list:hover {
  cursor: pointer;
  background-color: #ffe5df;
  transition: all 1s;
  border-radius: 5px;
}
.img-list #img {
  width: 95%;
  /* border: 1px solid black; */
}
label.btn.uploadBtn {
  /* border: 1px solid #f78065; */
  padding: 0;
  margin-bottom: 2%;
}
label.btn.uploadBtn:hover {
  /* border: 1px solid #f78065; */
  padding: 0;
  margin-bottom: 2%;
  transform: scale(1.1);
}
.fa-times {
  color: rgb(255, 64, 64);
}
.fa-times:hover {
  cursor: pointer;
  transform: scale(1.1);
}
.fa-plus {
  color: #67c23a;
}
</style>