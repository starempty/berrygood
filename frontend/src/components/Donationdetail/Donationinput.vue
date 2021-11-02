<template>
  <h2>기부 참여하기</h2>
  <div class="donation-input-box container">
    <el-form
      :label-position="'top'"
      v-model="state.donationInput"
    >
      <el-form-item label="기부대상">
        <el-input class="donation-input-disable" v-model="state.donationInput.comp" :disabled="true"></el-input>
      </el-form-item>
      <div class="row">
        <el-form-item class="col-10" label="이름">
          <div v-if="state.donationInput.hideName">
            <el-input class="donation-input-disable" v-model="state.donationInput.usernickname" :disabled="true"></el-input>
          </div>
          <div v-else>
            <el-input class="donation-input-disable" v-model="state.donationInput.username" :disabled="true"></el-input>
          </div>
        </el-form-item>
        <el-form-item class="col-2 d-flex align-items-center">
          <el-checkbox class="hide-check" label="익명" v-model="state.donationInput.hideName"></el-checkbox>
        </el-form-item>
        <!-- <el-form-item class="col-2 d-flex align-items-center" v-if="state.donationInput.donationValue">
            {{ $filters.money(state.donationInput.donationValue) }}
          </el-form-item> -->
      </div>
      <div class="row">
        <el-form-item class="col-4" label="잔여 베리 (B)">
          <el-input class="donation-input-disable" v-model="state.myBerry" :disabled="true"></el-input>
        </el-form-item>
        <div class="col-2 d-flex justify-content-start align-items-center">
          <el-form-item class="berry-check">
            <el-button @click="berryCheck" v-if="wallet">조회</el-button>
          </el-form-item>
        </div>
        <el-form-item class="col-6" label="기부 베리 (B)">
          <el-input class="donation-input" v-model="state.donationInput.donationValue">
          </el-input>
        </el-form-item>
      </div>
      <el-form-item label="응원의 메세지">
        <el-input class="donation-input" v-model="state.donationInput.message"></el-input>
      </el-form-item>
      <div class="donation-btn" v-if="wallet">
        <el-button class="berry-charge" @click="clickCharge">충전하기</el-button>
        <el-button @click="clickDonation(state.donationInput)">기부하기</el-button>
      </div>
      <div v-else>
        <p class="text-center" style="font-weight:600">개인 지갑의 베리로 기부할 수 있습니다.</p>
        <div class="d-flex justify-content-center">
          <el-button id="create-wallet" @click="moveToMypage">지갑 만들기</el-button>
        </div>
      </div>
      <br><br>
    </el-form>
  </div>
</template>

<script>
import { ref, reactive, computed } from 'vue'
import { useStore } from 'vuex';
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router';

export default {
  props: {
    comp: String,
    user: Object,
  },

  setup(props, { emit }) {
    const store = useStore();
    const router = useRouter();
    // const email = computed(() => props.user.email)
    const wallet = computed(() => store.getters['getWallet'])
    const state = reactive ({
      donationInput: {
        comp: computed(() => props.comp),
        username: computed(() => props.user.name),
        usernickname: computed(() => props.user.nickname),
        donationValue: null,
        message: '',
        hideName: ref(false),
      },
      money: '',
      myBerry: null,
      openDonationDialog: ref(false),
      chargeDialog: false, 
    })
    const moveToMypage = () => {
      router.push({ name: "Mypage" });
    }
    const berryCheck = () => {
      state.myBerry = wallet.value.berry;
    }
    const clickCharge = () => {
      // state.chargeDialog = true;
      emit('openChargeDialog')
    }
    const clickDonation = (donationInput) => {
      if (!state.myBerry) {
        ElMessage.error('잔여 베리를 조회해주세요.')
      }
      else if (!state.donationInput.donationValue) {
        ElMessage.error('기부할 베리를 작성해주세요.')
      }
      else if (state.myBerry >= state.donationInput.donationValue) {
        emit('openDonationDialog', donationInput)
      }
      else {
        ElMessage.error('베리가 부족합니다.')
      }
    }
    return { 
      state, wallet, berryCheck, clickCharge, clickDonation, moveToMypage, 
    }
  },
}
</script>

<style>
.donation-input-box {
  width: 100%;
  height: 30vh;
  padding: 20px;
}
.el-form--label-top .el-form-item__label {
  padding: 0;
  line-height: 1vh;
  font-size: 12px;
  margin-bottom: 1vh;
}
.donation-input-disable .el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
}
.donation-input-disable .el-input__inner {
  background-color: white !important;
}
.donation-input .el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
}
.donation-input .el-input__inner:hover {
  border-bottom: 1.5px solid #c0c4cc;
}
.donation-input .el-input__inner:focus {
  border-bottom: 1px solid #f78065;
}
.hide-check .el-checkbox__input.is-checked .el-checkbox__inner {
  background-color: #f78065;
  border-color: #f78065;
}
.hide-check .el-checkbox__input.is-checked+.el-checkbox__label {
  color: #f78065;
}
.hide-check .el-checkbox__input.is-focus .el-checkbox__inner {
  border-color: #f78065;
} 
.hide-check .el-checkbox__inner:hover {
  border-color: #f78065;
}
.berry-check .el-button {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
}
.berry-check .el-button:hover {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  opacity: 0.8;
}
.berry-check .el-button:focus {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  opacity: 0.8;
}
.donation-btn {
  display: flex;
  justify-content: center;
  padding-bottom: 10%;
}
.donation-btn .el-button {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  margin: 0 2%;
}
.donation-btn .el-button:hover {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  opacity: 0.8;
}
.donation-btn .el-button:focus {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  opacity: 0.8;
}
.donation-btn .el-button.berry-charge {
  background-color: #7e93eb;
  border-color: #7e93eb;
  color: white;
}
.donation-btn .el-button.berry-charge:hover {
  background-color: #7e93eb;
  border-color: #7e93eb;
  color: white;
}
.donation-btn .el-button.berry-charge:focus {
  background-color: #7e93eb;
  border-color: #7e93eb;
  color: white;
}
.donation-input-box #create-wallet {
  color: white;
  background-color: #f78065;
  border: #f78065;
}
.donation-input-box #create-wallet:hover {
  color: white;
  background-color: #f78065;
  border: #f78065;
  opacity: 0.8;
}
</style>