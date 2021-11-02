<template>
  <div class="user-input-box container">
    <el-form
      :label-position="'top'"
      :model="state.userInput"
      :rules="state.rules"
      v-model="state.userInput"
    >
      <div class="row">
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
          <div v-if="!state.userInput.id">
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
        <el-input class="user-input" v-model="state.userInput.password" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item prop="passwordconfirm" class="animate__animated animate__fadeInUp" v-show="state.showOption.passwordconfirmInput" @keyup="passwordconfirmChange" label="비밀번호 확인">
        <el-input class="user-input" v-model="state.userInput.passwordconfirm" autocomplete="off" show-password></el-input>
        <div v-if="!state.userInput.passwordconfirm" :class="{ visible: state.passwordConfirm }" class="password-confirm">
          비밀번호를 입력하세요.
        </div>
        <div v-else :class="{ visible: state.passwordConfirm }" class="password-confirm">
          비밀번호가 올바르지 않습니다.
        </div>
      </el-form-item>
      <el-form-item prop="name" class="animate__animated animate__fadeInUp" v-show="state.showOption.nameInput" @keyup="nameChange" label="이름">
        <el-input class="user-input" v-model="state.userInput.name"></el-input>
      </el-form-item>
      <div class="row animate__animated animate__fadeInUp" v-show="state.showOption.nicknameInput">
        <el-form-item prop="nickname" class="col-8" label="닉네임" @keyup="nicknameChange">
          <el-input class="user-input" v-model="state.userInput.nickname"></el-input>
        </el-form-item>
        <div class="col d-flex justify-content-end align-items-center">
          <div v-if="!state.userInput.nickname">
            <el-form-item class="check-button-empty">
              <el-button disabled>중복체크</el-button>
            </el-form-item>
          </div>
          <div v-else>
            <el-form-item class="check-button animate__animated animate__pulse animate__repeat-2">
              <el-button @click="nicknameCheck">중복체크</el-button>
            </el-form-item>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <el-form-item prop="birthday" class="animate__animated animate__fadeInUp date-picker" v-show="state.showOption.birthdayInput" label="생년월일">
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
            <div class="animate__animated animate__fadeInUp mx-4" v-show="state.year">
              <el-select v-model="state.month" placeholder="월" @change="dateChange()">
                <el-option 
                  v-for="month in 12"
                  :key="month"
                  :value="month"
                >
                </el-option>
              </el-select>
            </div>
            <div class="animate__animated animate__fadeInUp" v-show="state.month">
              <el-select v-model="state.day" placeholder="일" @change="dateChange()">
                <el-option 
                  v-for="day in 31"
                  :key="day"
                  :value="day"
                >
                </el-option>
              </el-select>
            </div>
          </el-form-item>
        </div>
        <el-form-item prop="gender" class="col animate__animated animate__fadeInUp" v-show="state.day && state.showOption.birthdayInput" label="성별">
          <el-radio-group v-model="state.userInput.gender">
            <el-radio label="m">남자</el-radio>
            <el-radio label="f">여자</el-radio>
          </el-radio-group>
        </el-form-item>
      </div>
      <el-form-item prop="phonenum" class="animate__animated animate__fadeInUp" v-show="state.userInput.gender && state.showOption.birthdayInput" @keyup="phonenumChange" label="연락처">
        <el-input class="user-input" v-model="state.userInput.phonenum"></el-input>
      </el-form-item>
      <el-form-item prop="address" class="animate__animated animate__fadeInUp" v-show="state.showOption.addressInput && state.showOption.birthdayInput" @keyup="addressChange" label="주소">
        <el-input class="user-input" v-model="state.userInput.address"></el-input>
      </el-form-item>
      <el-form-item class="signup-btn animate__animated animate__fadeInUp" 
        v-show="state.showOption.signupBtn"
      >
        <el-button @click="clickSignup">회원가입</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { reactive } from "vue";
import { useStore } from "vuex";
// import { useRouter } from 'vue-router'
import { ElMessage } from "element-plus";

export default {
  setup() {
    const store = useStore();
    // const router = useRouter()

    const state = reactive({
      userInput: {
        id: "",
        name: "",
        password: "",
        passwordconfirm: "",
        nickname: "",
        birthday: "",
        gender: "",
        phonenum: "",
        address: "",
      },
      showOption: {
        passwordInput: false,
        passwordconfirmInput: false,
        nameInput: false,
        birthdayInput: false,
        nicknameInput: false,
        addressInput: false,
        signupBtn: false,
      },
      idCheck: false,
      nicknameCheck: false,
      startYear: 2021,
      yearCount: 122,
      year: "",
      month: "",
      day: "",
      emailInput: false,
      id: "",
      email: "",
      selected: "",
      passwordConfirm: true,
      rules: {
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
        name: [
          { required: true, message: "필수 항목입니다.", trigger: "blur" },
        ],
        nickname: [
          { required: true, message: "필수 항목입니다.", trigger: "blur" },
        ],
        birthday: [
          { required: true, message: "필수 항목입니다.", trigger: "blur" },
        ],
        gender: [
          { required: true, message: "필수 항목입니다.", trigger: "blur" },
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

    // const phoneValid = /^[0-9]+/g
    // const ageValid = /^[0-9]+/g
    const emailValid =
      /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    const emailChange = () => {
      if (state.selected) {
        state.idCheck = false;
        state.emailInput = true;
        state.showOption.passwordInput = false;
        state.showOption.passwordconfirmInput = false;
        state.showOption.nameInput = false;
        state.showOption.nicknameInput = false;
        state.showOption.birthdayInput = false;
        state.showOption.addressInput = false;
        state.showOption.signupBtn = false;
        state.email = state.selected;
        state.userInput.id = `${state.id}@${state.email}`;
      } else {
        state.idCheck = false;
        state.emailInput = false;
        state.showOption.passwordInput = false;
        state.showOption.passwordconfirmInput = false;
        state.showOption.nameInput = false;
        state.showOption.nicknameInput = false;
        state.showOption.birthdayInput = false;
        state.showOption.addressInput = false;
        state.showOption.signupBtn = false;
        state.email = "";
        state.userInput.id = `${state.id}@${state.email}`;
      }
    };
    const idCheck = () => {
      state.userInput.id = `${state.id}@${state.email}`;
      console.log(state.userInput.id);

      if (!emailValid.test(state.userInput.id)) {
        ElMessage.error("이메일을 올바르게 입력해 주세요.");
      } else {
        store
          .dispatch("useremailCheck", state.userInput.id)
          .then(({ data }) => {
            ElMessage({
              message: data,
              type: "success",
            });
            state.idCheck = true;
            if (state.idCheck) {
              state.showOption.passwordInput = true;
            }
            if (passwordValid.test(state.userInput.password)) {
              state.showOption.passwordconfirmInput = true;
            }
            if (
              state.userInput.password === state.userInput.passwordconfirm &&
              state.userInput.password
            ) {
              state.showOption.nameInput = true;
            }
            if (state.userInput.name) {
              state.showOption.nicknameInput = true;
            }
            if (state.userInput.nickname && state.nicknameCheck) {
              state.showOption.birthdayInput = true;
            }
            if (state.userInput.phonenum) {
              state.showOption.addressInput = true;
            }
            if (state.userInput.address) {
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
      if (passwordValid.test(state.userInput.password)) {
        state.showOption.passwordconfirmInput = true;
        if (state.userInput.password === state.userInput.passwordconfirm) {
          state.showOption.nameInput = true;
        }
        if (state.userInput.name) {
          state.showOption.nicknameInput = true;
        }
        if (state.userInput.nickname && state.nicknameCheck) {
          state.showOption.birthdayInput = true;
        }
        if (state.userInput.phonenum) {
          state.showOption.addressInput = true;
        }
        if (state.userInput.address) {
          state.showOption.signupBtn = true;
        }
      } else {
        state.showOption.passwordconfirmInput = false;
        state.showOption.nameInput = false;
        state.showOption.nicknameInput = false;
        state.showOption.birthdayInput = false;
        state.showOption.addressInput = false;
        state.showOption.signupBtn = false;
      }
    };
    const passwordconfirmChange = () => {
      if (state.userInput.password === state.userInput.passwordconfirm) {
        state.showOption.nameInput = true;
        state.passwordConfirm = true;
        if (state.userInput.name) {
          state.showOption.nicknameInput = true;
        }
        if (state.userInput.nickname && state.nicknameCheck) {
          state.showOption.birthdayInput = true;
        }
        if (state.userInput.phonenum) {
          state.showOption.addressInput = true;
        }
        if (state.userInput.address) {
          state.showOption.signupBtn = true;
        }
      } else {
        state.showOption.nameInput = false;
        state.showOption.nicknameInput = false;
        state.showOption.birthdayInput = false;
        state.showOption.addressInput = false;
        state.showOption.signupBtn = false;
        if (state.userInput.passwordconfirm) {
          state.passwordConfirm = false;
        }
      }
    };
    const nameChange = () => {
      if (state.userInput.name) {
        state.showOption.nicknameInput = true;
        if (state.userInput.nickname && state.nicknameCheck) {
          state.showOption.birthdayInput = true;
        }
        if (state.userInput.phonenum) {
          state.showOption.addressInput = true;
        }
        if (state.userInput.address) {
          state.showOption.signupBtn = true;
        }
      } else {
        state.showOption.nicknameInput = false;
        state.showOption.birthdayInput = false;
        state.showOption.addressInput = false;
        state.showOption.signupBtn = false;
      }
    };
    const nicknameChange = () => {
      state.nicknameCheck = false;
      state.showOption.birthdayInput = false;
      state.showOption.signupBtn = false;
    };
    const nicknameCheck = () => {
      store
        .dispatch("nicknameCheck", state.userInput.nickname)
        .then(({ data }) => {
          ElMessage({
            message: data,
            type: "success",
          });
          state.nicknameCheck = true;
          state.showOption.birthdayInput = true;
          if (state.userInput.phonenum && state.userInput.address) {
            state.showOption.signupBtn = true;
          }
        })
        .catch(() => {
          ElMessage.error("중복된 닉네임이 존재합니다.");
          state.nicknameCheck = false;
        });
    };
    const dateChange = () => {
      state.userInput.birthday = `${state.year}-${("0" + state.month).slice(
        -2
      )}-${("0" + state.day).slice(-2)}`;
    };
    const phoneValid = /^\d{3}-\d{3,4}-\d{4}$/;
    const phonenumChange = () => {
      if (phoneValid.test(state.userInput.phonenum)) {
        state.showOption.addressInput = true;
        if (state.userInput.address) {
          state.showOption.signupBtn = true;
        }
      } else {
        state.showOption.addressInput = false;
        state.showOption.signupBtn = false;
      }
    };
    const addressChange = () => {
      if (state.userInput.address) {
        state.showOption.signupBtn = true;
      } else {
        state.showOption.signupBtn = false;
      }
    };
    const clickSignup = () => {
      if (state.idCheck && state.nicknameCheck) {
        store.dispatch("signup", {
          email: state.userInput.id,
          name: state.userInput.name,
          password: state.userInput.password,
          nickname: state.userInput.nickname,
          birthday: state.userInput.birthday,
          gender: state.userInput.gender,
          phone: state.userInput.phonenum,
          address: state.userInput.address,
        });
      } else {
        ElMessage.error("ID 또는 닉네임 중복체크를 해주시기 바랍니다.");
      }
    };

    return {
      state,
      emailChange,
      idCheck,
      passwordChange,
      passwordconfirmChange,
      nameChange,
      nicknameChange,
      nicknameCheck,
      dateChange,
      phonenumChange,
      addressChange,
      clickSignup,
    };
  },
};
</script>

<style>
.user-input-box {
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
.user-input.id-input {
  max-width: 90px;
}
.user-input.email-input {
  max-width: 110px;
}
.user-input.email-select {
  max-width: 110px;
  margin-left: 10px;
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
.user-input-disable .el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
}
.user-input-disable .el-input__inner {
  background-color: white !important;
}
.el-input.is-disabled .el-input__inner {
  background-color: white !important;
}
.user-input .el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
}
.user-input .el-input__inner:hover {
  border-bottom: 1.5px solid #c0c4cc;
}
.user-input .el-input__inner:focus {
  border-bottom: 1px solid #f78065 !important;
}
.date-picker .el-form-item__content {
  display: flex;
  justify-content: flex-start;
}
.date-picker .el-input__inner {
  border: none;
  border-radius: 0;
  border-bottom: 1px solid #dcdfe6;
  margin-bottom: 1vh;
  padding: 0;
  font-weight: bold;
}
.date-picker .el-input__inner:hover {
  border-bottom: 1.5px solid #c0c4cc;
}
.date-picker .el-input__inner:focus {
  border-bottom: 1px solid #f78065;
}
.el-select .el-input__inner:focus {
  border-bottom: 1px solid #f78065 !important;
}
.el-select .el-input.is-focus .el-input__inner {
  border-color: #f78065 !important;
}
.el-radio__input.is-checked .el-radio__inner {
  border-color: #f78065 !important;
  background: #f78065 !important;
}
.el-radio__inner:hover {
  border-color: #f78065 !important;
}
.el-radio__input.is-checked + .el-radio__label {
  color: #f78065 !important;
}
.el-radio__label:hover {
  color: #f78065 !important;
}
.check-button .el-button {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
}
.check-button .el-button:hover {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  opacity: 0.8;
}
.check-button .el-button:focus {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
}
.signup-btn .el-button {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
}
.signup-btn .el-button:hover {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
  opacity: 0.8;
}
.signup-btn .el-button:focus {
  background-color: #f78065;
  border-color: #f78065;
  color: white;
}
.el-popper__arrow {
  display: none;
}
.el-select-dropdown__item.selected {
  color: #f78065 !important;
}
.el-select-dropdown__item.hover,
.el-select-dropdown__item:hover {
  background-color: #ffeadc !important;
}
</style>