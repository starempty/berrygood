import axios from 'axios';
import { BASE_URL } from '@/config/constants';
import { ElMessage } from 'element-plus'

export const Donation = {
  namespaced: true,
  actions: {
    // 기부
    donateCreate(context, formData) {
      return axios
        .post(`${BASE_URL}/donate`, formData, {
          headers: {
            "Accept": "*/*",
            "Content-Type": "multipart/form-data"
          }
        })
    },
    donateUpdate(context, { donationNo, formData }) {
      return axios
        .post(`${BASE_URL}/donate/${donationNo}`, formData, {
          headers: {
            "Accept": "*/*",
            "Content-Type": "multipart/form-data"
          }
        })
    },
    donateDelete(context, donationNo) {
      return axios
        .delete(`${BASE_URL}/donate/${donationNo}`)
    },
    donateList() {
      return axios
        .get(`${BASE_URL}/donate`)
    },
    donateDetail(context, donateId) {
      return axios
        .get(`${BASE_URL}/donate/${donateId}`)
    },
    participantRegister(context, payload) {
      console.log(payload)
      return axios
        .post(`${BASE_URL}/donate/participant`, payload)
        .then(() => {
          ElMessage({
            message: "기부가 완료되었습니다.",
            type: "success"
          })
        })
    },
    participantDelete(context, payload) {
      return axios
        .delete(`${BASE_URL}/donate/participant`, { data: { amount: payload.amount, projectId: payload.projectId, userEmail: payload.userEmail } })
        .then(() => {
          ElMessage.error('기부가 취소되었습니다.')
        })
    },
    findByEmail(context, email) {
      return axios.get(`${BASE_URL}/donate/donate/${email}`)
    },
    findByCompemail(context, email) {
      return axios.get(`${BASE_URL}/donate/donate/comp/${email}`)
    },
    findByNo(context, no) {
      return axios.get(`${BASE_URL}/donate/comp/${no}`)
    }
  },
}