import axios from 'axios';
import { BASE_URL } from '@/config/constants';
// const BASE_URL = "http://localhost:8080/api"
export const Mydata = {
  namespaced: true,
  actions: {
    userEdit(context, payload) {
      console.log(payload)
      return axios.post(`${BASE_URL}/user/edit`, payload)
    },
    userDelete(context, email) {
      return axios.delete(`${BASE_URL}/user/${email}`)
    },
    compEdit(context, payload) {
      console.log(payload)
      return axios.post(`${BASE_URL}/comp/edit`, payload)
    },
    compDelete(context, email) {
      return axios.delete(`${BASE_URL}/comp/${email}`)
    },
  }
}