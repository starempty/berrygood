import { createStore } from 'vuex'
import axios from 'axios'
import router from '@/router/index.js'
import createPersistedState from 'vuex-persistedstate'
import { ElMessage } from 'element-plus'
import { BASE_URL } from '@/config/constants';
import { Blood } from '@/store/modules/Blood.js';
import { Donation } from '@/store/modules/Donation.js';
import { Funding } from '@/store/modules/Funding.js';
import { Service } from '@/store/modules/Service.js';
import { Mydata } from '@/store/modules/Mydata.js';
import { Tx } from '@/store/modules/Tx.js';

const getDefaultState = () => {
  return {
    user: {},
    token: "",
    wallet: null,
    pjtWallet: null,
    check: true,
  }
}

export default createStore({
  state: getDefaultState(),
  modules: { Blood, Donation, Funding, Service, Tx, Mydata },
  getters: {
    getToken(state) {
      return state.token
    },
    getUser(state) {
      return state.user
    },
    getWallet(state) {
      return state.wallet
    },
    getCheck(state) {
      return state.check
    },
    getPjtwallet(state) {
      return state.pjtWallet
    }
  },

  mutations: {
    LOGIN(state, token) {
      state.token = token
    },
    USER(state, data) {
      // state.userid = data.email
      // state.role = data.role
      state.user = data;
    },
    LOGOUT(state) {
      Object.assign(state, getDefaultState())
    },
    WALLET(state, wallet) {
      state.wallet = wallet
    },
    PJTWALLET(state, pjtWallet) {
      state.pjtWallet = pjtWallet
    },
    CHECK(state, check) {
      state.check = check;
    }
  },

  actions: {
    // 중복체크
    useremailCheck(context, email) {
      return axios
        .get(`${BASE_URL}/user/existEmail/${email}`)
    },
    compemailCheck(context, email) {
      return axios
        .get(`${BASE_URL}/comp/existEmail/${email}`)
    },
    nicknameCheck(context, nickname) {
      return axios
        .get(`${BASE_URL}/user/existNickname/${nickname}`)
    },

    // 회원가입
    signup(context, payload) {
      return axios
        .post(`${BASE_URL}/user`, payload)
        .then(() => {
          ElMessage({
            message: "회원가입이 되었습니다.",
            type: "success"
          })
          router.push('/')
        })
        .catch((err) => {
          console.error(err)
        })
    },
    compsignup(context, payload) {
      return axios
        .post(`${BASE_URL}/comp`, payload)
        .then(() => {
          router.push('/')
        })
        .catch((err) => {
          console.error(err)
        })
    },
    // 로그인
    userLogin({ dispatch, commit }, payload) {
      return axios
        .post(`${BASE_URL}/users/auth/login`, payload)
        .then(({ data }) => {
          commit("LOGIN", data)
          axios.get(`${BASE_URL}/users/auth/me`, {
            headers: {
              'Authorization': `Bearer ${data}`
            }
          })
            .then(({ data }) => {
              dispatch("userInfo", data.email).then(user => {
                user.data.role = data.role;
                commit("USER", user.data);
              });
              ElMessage({
                message: "로그인 되었습니다.",
                type: "success"
              })
              router.push('/');
            })
            .catch((err) => {
              console.log(err)
            })
        })
        .catch((err) => {
          console.error(err)
          ElMessage.error("ID 또는 Password 가 잘못되었습니다.")
        });
    },
    compLogin({ dispatch, commit }, payload) {
      return axios
        .post(`${BASE_URL}/users/auth/comp/login`, payload)
        .then(({ data }) => {
          commit("LOGIN", data)
          axios.get(`${BASE_URL}/users/auth/comp/me`, {
            headers: {
              'Authorization': `Bearer ${data}`
            }
          })
            .then(({ data }) => {
              dispatch("compInfo", data.email).then(user => {
                user.data.role = data.role;
                commit("USER", user.data);
              });
              ElMessage({
                message: "로그인 되었습니다.",
                type: "success"
              })
              router.push('/');
            })
            .catch((err) => {
              console.log(err)
            })
        })
        .catch((err) => {
          console.error(err)
          ElMessage.error("ID 또는 Password 가 잘못되었습니다.")
        });
    },
    myInfo(context, payload) {
      return axios
        .get(`${BASE_URL}/users/auth/me`, {
          headers: {
            'Authorization': `Bearer ${payload.token}`
          }
        })
    },
    //뱃지 조회
    badge(context, userNo) {
      return axios
        .post(`${BASE_URL}/badge/tier/${userNo}`)
    },
    // 로그아웃
    logout({ commit }) {
      commit("LOGOUT")
    },
    // 유저 정보
    userInfo(context, email) {
      return axios
        .get(`${BASE_URL}/user/${email}`)
    },
    // 기업 정보
    compInfo(context, email) {
      return axios
        .get(`${BASE_URL}/comp/${email}`)
    },
    // 개인 지갑
    setWallet({ commit }, payload) {
      return axios
        .post(`${BASE_URL}/wallet`, payload)
        .then(() => {
          commit("WALLET", payload)
        })
    },
    walletDetail(context, email) {
      return axios
        .get(`${BASE_URL}/wallet/${email}`)
    },
    walletUpdate({ commit }, payload) {
      return axios
        .post(`${BASE_URL}/wallet/update`, payload)
        .then(() => {
          commit("WALLET", payload)
        })
    },
    // 기업 지갑
    setCompwallet({ commit }, payload) {
      return axios
        .post(`${BASE_URL}/compwallet`, payload)
        .then(() => {
          commit('WALLET', payload)
        })
    },
    compwalletDetail(context, email) {
      return axios
        .get(`${BASE_URL}/compwallet/${email}`)
    },
    compwalletUpdate({ commit }, payload) {
      return axios
        .post(`${BASE_URL}/compwallet/update`, payload)
        .then(() => {
          commit("WALLET", payload)
        })
    },
  },
  plugins: [
    createPersistedState()
  ]
})
