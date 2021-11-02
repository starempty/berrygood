<template>
  <div class="comp-input-box container">
    <el-form
      :label-position="'top'"
      :model="state.compInput"
      :rules="state.rules"
      v-model="state.compInput"
    >
      <el-form-item prop="name" label="기업명" @keyup="nameChange">
        <el-input class="comp-input" v-model="state.compInput.name"></el-input>
      </el-form-item>
      <div class="row animate__animated animate__fadeInUp" v-show="state.compInput.name">
        <el-form-item prop="id" class="col-9" label="아이디">
          <div class="d-flex">
            <el-input class="user-input id-input" v-model="state.id" @keyup="emailChange"></el-input>
            <span class="mx-2">@</span>
            <el-input class="user-input email-input" v-model="state.email" :disabled="state.emailInput"></el-input>
            <el-select class="user-input email-select" v-model="state.selected" placeholder="직접입력" @change="emailChange()">
              <el-option label="직접입력" value=""></el-option>
              <el-option label="naver.com" value="naver.com"></el-option>
              <el-option label="daum.net" value="daum.net"></el-option>
              <el-option label="hanmail.net" value="hanmail.net"></el-option>
              <el-option label="nate.com" value="nate.com"></el-option>
              <el-option label="gmail.com" value="gmail.com"></el-option>
              <el-option label="hotmail.com" value="hotmail.com"></el-option>
            </el-select>
          </div>
        </el-form-item>
        <div class="col d-flex justify-content-end align-items-center">
          <div v-if="!state.compInput.id">
            <el-form-item class="check-button-empty">
              <el-button disabled>중복체크</el-button>
            </el-form-item>
          </div>
          <div v-else>
            <el-form-item class="check-button animate__animated animate__pulse animate__repeat-2">
              <el-button @click="idCheck">중복체크</el-button>
            </el-form-item>
          </div>
        </div>
      </div>
      <el-form-item prop="password" class="animate__animated animate__fadeInUp" v-show="state.showOption.passwordInput" @keyup="passwordChange" label="비밀번호">
        <el-input class="comp-input" v-model="state.compInput.password" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item class="animate__animated animate__fadeInUp" v-show="state.showOption.passwordconfirmInput" @keyup="passwordconfirmChange" label="비밀번호 확인">
        <el-input class="comp-input" v-model="state.compInput.passwordconfirm" autocomplete="off" show-password></el-input>
        <div v-if="!state.compInput.passwordconfirm" :class="{ visible: state.passwordConfirm }" class="password-confirm">
          비밀번호를 입력하세요.
        </div>
        <div v-else :class="{ visible: state.passwordConfirm }" class="password-confirm">
          비밀번호가 올바르지 않습니다.
        </div>
      </el-form-item>
      <el-form-item prop="phonenum" class="animate__animated animate__fadeInUp" v-show="state.showOption.phonenumInput" @keyup="phonenumChange" label="연락처">
        <el-input class="comp-input" v-model="state.compInput.phonenum"></el-input>
      </el-form-item>
      <el-form-item prop="address" class="animate__animated animate__fadeInUp" v-show="state.showOption.addressInput" @keyup="addressChange" label="주소">
        <el-input class="comp-input" v-model="state.compInput.address"></el-input>
      </el-form-item>
      <el-form-item class="signup-btn animate__animated animate__fadeInUp" v-show="state.showOption.signupBtn">
        <el-button @click="clickSignup">회원가입</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { reactive } from "vue";
import { ElMessage } from "element-plus";
import { useStore } from "vuex";

export default {
  setup() {
    const store = useStore();

    const state = reactive({
      compInput: {
        id: "",
        name: "",
        password: "",
        passwordconfirm: "",
        phonenum: "",
        address: "",
      },
      showOption: {
        passwordInput: false,
        passwordconfirmInput: false,
        phonenumInput: false,
        addressInput: false,
        signupBtn: false,
      },
      passwordInput: false,
      idCheck: false,
      id: "",
      email: "",
      selected: "",
      emailInput: false,
      passwordConfirm: true,
      rules: {
        name: [
          { required: true, message: "필수 항목입니다.", trigger: "blur" },
        ],
        id: [{ required: true, message: "필수 항목입니다.", trigger: "blur" }],
        password: [
          { required: true, message: "필수 항목입니다.", trigger: "blur" },
          {
            pattern: /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/,
            message:
              "영문 + 숫자 + 특수문자 조합으로 8자 이상 25자 이하로 작성해야 합니다.",
            trigger: ["blur", "change"],
          },
        ],
        phonenum: [
          { required: true, message: "필수 항목입니다.", trigger: "blur" },
          {
            pattern: /^\d{3}-\d{4}-\d{4}$/,
            message: "'-' 를 포함한 핸드폰 번호를 입력하세요",
            trigger: ["blur", "change"],
          },
        ],
        address: [
          { required: true, message: "필수 항목입니다.", trigger: "blur" },
        ],
      },
    });
    const nameChange = () => {
      if (!state.compInput.name) {
        state.showOption.passwordInput = false;
        state.showOption.passwordconfirmInput = false;
        state.showOption.phonenumInput = false;
        state.showOption.addressInput = false;
        state.showOption.signupBtn = false;
      } else {
        if (state.compInput.id && state.idCheck) {
          state.showOption.passwordInput = true;
        }
        if (passwordValid.test(state.compInput.password)) {
          state.showOption.passwordconfirmInput = true;
        }
        if (
          state.compInput.password === state.compInput.passwordconfirm &&
          state.compInput.password
        ) {
          state.showOption.phonenumInput = true;
        }
        if (state.compInput.phonenum) {
          state.showOption.addressInput = true;
        }
        if (state.compInput.address) {
          state.showOption.signupBtn = true;
        }
      }
    };
    const emailValid =
      /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    const emailChange = () => {
      if (state.selected) {
        state.email = state.selected;
        state.idCheck = false;
        state.emailInput = true;
        state.showOption.passwordInput = false;
        state.showOption.passwordconfirmInput = false;
        state.showOption.phonenumInput = false;
        state.showOption.addressInput = false;
        state.showOption.signupBtn = false;
        state.compInput.id = `${state.id}@${state.email}`;
      } else {
        state.email = "";
        state.idCheck = false;
        state.emailInput = false;
        state.showOption.passwordInput = false;
        state.showOption.passwordconfirmInput = false;
        state.showOption.phonenumInput = false;
        state.showOption.addressInput = false;
        state.showOption.signupBtn = false;
        state.compInput.id = `${state.id}@${state.email}`;
      }
    };
    const idCheck = () => {
      state.compInput.id = `${state.id}@${state.email}`;
      console.log(state.compInput.id);

      if (!emailValid.test(state.compInput.id)) {
        ElMessage.error("이메일을 올바르게 입력해 주세요.");
      } else {
        store
          .dispatch("compemailCheck", state.compInput.id)
          .then(({ data }) => {
            ElMessage({
              message: data,
              type: "success",
            });
            state.idCheck = true;
            if (state.idCheck) {
              state.showOption.passwordInput = true;
            }
            if (passwordValid.test(state.compInput.password)) {
              state.showOption.passwordconfirmInput = true;
            }
            if (
              state.compInput.password === state.compInput.passwordconfirm &&
              state.compInput.password
            ) {
              state.showOption.phonenumInput = true;
            }
            if (state.compInput.phonenum) {
              state.showOption.addressInput = true;
            }
            if (state.compInput.address) {
              state.showOption.signupBtn = true;
            }
          })
          .catch(() => {
            ElMessage.error("중복된 ID가 존재합니다.");
            state.idCheck = false;
          });
      }
    };
    const passwordValid =
      /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
    const passwordChange = () => {
      if (passwordValid.test(state.compInput.password)) {
        state.showOption.passwordconfirmInput = true;
        if (state.compInput.password === state.compInput.passwordconfirm) {
          state.showOption.phonenumInput = true;
        }
        if (state.compInput.phonenum) {
          state.showOption.addressInput = true;
        }
        if (state.compInput.address) {
          state.showOption.signupBtn = true;
        }
      } else {
        state.showOption.passwordconfirmInput = false;
        state.showOption.phonenumInput = false;
        state.showOption.addressInput = false;
        state.showOption.signupBtn = false;
      }
    };
    const passwordconfirmChange = () => {
      if (state.compInput.password === state.compInput.passwordconfirm) {
        state.showOption.phonenumInput = true;
        state.passwordConfirm = true;
        if (state.compInput.phonenum) {
          state.showOption.addressInput = true;
        }
        if (state.compInput.address) {
          state.showOption.signupBtn = true;
        }
      } else {
        state.showOption.phonenumInput = false;
        state.showOption.addressInput = false;
        state.showOption.signupBtn = false;
        if (state.compInput.passwordconfirm) {
          state.passwordConfirm = false;
        }
      }
    };
    const phoneValid = /^\d{3}-\d{3,4}-\d{4}$/;
    const phonenumChange = () => {
      if (phoneValid.test(state.compInput.phonenum)) {
        state.showOption.addressInput = true;
        if (state.compInput.address) {
          state.showOption.signupBtn = true;
        }
      } else {
        state.showOption.addressInput = false;
        state.showOption.signupBtn = false;
      }
    };
    const addressChange = () => {
      if (state.compInput.address) {
        state.showOption.signupBtn = true;
      } else {
        state.showOption.signupBtn = false;
      }
    };
    const clickSignup = () => {
      if (state.idCheck) {
        store
          .dispatch("compsignup", {
            email: state.compInput.id,
            name: state.compInput.name,
            password: state.compInput.password,
            phone: state.compInput.phonenum,
            address: state.compInput.address,
          })
          .then(() => {
            ElMessage({
              message: "회원가입이 되었습니다.",
              type: "success",
            });
          });
      } else {
        ElMessage.error("ID 또는 닉네임 중복체크를 해주시기 바랍니다.");
      }
    };

    return {
      state,
      nameChange,
      emailChange,
      idCheck,
      passwordChange,
      passwordconfirmChange,
      phonenumChange,
      addressChange,
      clickSignup,
    };
  },
};
</script>

<style>
.comp-input-box {
  width: 100%;
  /* border: 1px solid black; */
  padding: 20px;
}
.el-form--label-top .el-form-item__label {
  padding: 0;
  line-height: 1vh;
  font-size: 12px;
  margin-bottom: 2px;
}
.comp-input-disable .el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
}
.comp-input-disable .el-input__inner {
  background-color: white !important;
}
.visible.password-confirm {
  display: none;
}
.password-confirm {
  color: var(--el-color-danger);
  font-size: 12px;
  line-height: 1;
  padding-top: 4px;
  position: absolute;
  top: 100%;
  left: 0;
}
.comp-input .el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
}
.comp-input .el-input__inner:hover {
  border-bottom: 1.5px solid #c0c4cc;
}
.comp-input .el-input__inner:focus {
  border-bottom: 1px solid #f78065;
}
.check-button .el-button {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
}
.check-button .el-button:hover {
  opacity: 0.8;
}
</style>