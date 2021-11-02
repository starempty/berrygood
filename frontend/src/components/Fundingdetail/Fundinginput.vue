<template>
  <h2>펀딩 참여하기</h2>
  <div class="funding-input-box container">
    <el-form
      :label-position="'top'"
      v-model="state.fundingInput"
    >
      <el-form-item label="펀딩 대상">
        <el-input class="funding-input-disable" v-model="state.fundingInput.comp" :disabled="true"></el-input>
      </el-form-item>
      <div class="row">
        <el-form-item class="col-10" label="이름">
          <div v-if="state.fundingInput.hideName">
            <el-input class="funding-input-disable" v-model="state.fundingInput.usernickname" :disabled="true"></el-input>
          </div>
          <div v-else>
            <el-input class="funding-input-disable" v-model="state.fundingInput.username" :disabled="true"></el-input>
          </div>
        </el-form-item>
        <el-form-item class="col-2 d-flex align-items-center">
          <el-checkbox class="hide-check" label="익명" v-model="state.fundingInput.hideName"></el-checkbox>
        </el-form-item>
        <!-- <el-form-item class="col-2 d-flex align-items-center" v-if="state.fundingInput.fundingValue">
          {{ $filters.money(state.fundingInput.fundingValue) }}
        </el-form-item> -->
      </div>
      <div class="row">
        <el-form-item class="col-4" label="잔여 베리 (B)">
          <el-input class="funding-input-disable" v-model="state.myBerry" :disabled="true"></el-input>
        </el-form-item>
        <div class="col-2 d-flex justify-content-start align-items-center">
          <el-form-item class="berry-check">
            <el-button @click="berryCheck">조회</el-button>
          </el-form-item>
        </div>
        <el-form-item class="col-6" label="펀딩 베리 (B)">
          <el-input class="funding-input" v-model="state.fundingInput.fundingValue">
          </el-input>
        </el-form-item>
      </div>
      <div class="funding-btn">
        <el-button class="berry-charge" @click="clickCharge">충전하기</el-button>
        <el-button @click="clickFunding(state.fundingInput)">펀딩하기</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import { ref, reactive, computed } from 'vue'
import { useStore } from 'vuex';
import { ElMessage } from 'element-plus'

export default {
  props: {
    comp: String,
    user: Object,
  },

  setup(props, { emit }) {
    const store = useStore();
    const state = reactive ({
      fundingInput: {
        comp: computed(() => props.comp),
        username: computed(() => props.user.name),
        usernickname: computed(() => props.user.nickname),
        fundingValue: null,
        hideName: ref(false),
      },
      myBerry: null,
      openFundingDialog: ref(false),
      wallet: computed(()=>store.getters['getWallet']),
      chargeDialog: false, 
    })
    const berryCheck = () => {
      state.myBerry = state.wallet.berry;
    }
    const clickCharge = () => {
      emit('openChargeDialog')
    }
    const clickFunding = (fundingInput) => {
      if (!state.myBerry) {
        ElMessage.error('잔여 베리를 조회해주세요.')
      }
      else if (!state.fundingInput.fundingValue) {
        ElMessage.error('펀딩 베리를 작성해주세요.')
      }
      else if (state.myBerry >= state.fundingInput.fundingValue) {
        emit('openFundingDialog', fundingInput)
      }
      else {
        ElMessage.error('베리가 부족합니다.')
      }
    }

    return { 
      state, berryCheck, clickCharge, clickFunding, 
    }
  },
}
</script>

<style>
.funding-input-box {
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
.funding-input-disable .el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
}
.funding-input-disable .el-input__inner {
  background-color: white !important;
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
.funding-btn {
  display: flex;
  justify-content: center;
  padding-bottom: 10%;
}
.funding-btn .el-button {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  margin: 0 2%;
}
.funding-btn .el-button:hover {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  opacity: 0.8;
}
.funding-btn .el-button:focus {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  opacity: 0.8;
}
.funding-btn .el-button.berry-charge {
  background-color: #7e93eb;
  border-color: #7e93eb;
  color: white;
}
.funding-btn .el-button.berry-charge:hover {
  background-color: #7e93eb;
  border-color: #7e93eb;
  color: white;
}
.funding-btn .el-button.berry-charge:focus {
  background-color: #7e93eb;
  border-color: #7e93eb;
  color: white;
}
</style>