import axios from 'axios';
import { BASE_URL } from '@/config/constants';
import { ElMessage } from 'element-plus';

export const Service = {
  namespaced: true,

  actions: {
    // 봉사
    serviceCreate(context, formData) {
      return axios
        .post(`${BASE_URL}/volunteer`, formData, {
          headers: {
            "Accept": "*/*",
            "Content-Type": "multipart/form-data"
          }
        })
    },
    serviceList() {
      return axios
        .get(`${BASE_URL}/volunteer`)
    },
    serviceDetail(context, serviceId) {
      return axios
        .get(`${BASE_URL}/volunteer/${serviceId}`)
    },
    serviceUpdate(context, { serviceNo, formData }) {
      return axios
        .post(`${BASE_URL}/volunteer/${serviceNo}`, formData, {
          headers: {
            "Accept": "*/*",
            "Content-Type": "multipart/form-data"
          }
        })
    },
    serviceDelete(context, serviceId) {
      return axios
        .delete(`${BASE_URL}/volunteer/${serviceId}`)
    },
    participantRegister(context, payload) {
      return axios
        .post(`${BASE_URL}/volunteer/participant`, payload)
        .then(() => {
          ElMessage({
            message: "참가 신청이 완료되었습니다.",
            type: "success"
          })
        })
    },
    participantDelete(context, payload) {
      return axios
        .delete(`${BASE_URL}/volunteer/participant`, { data: { amount: payload.amount, projectId: payload.projectId, userEmail: payload.userEmail } })
        .then(() => {
          ElMessage({
            message: "정상적으로 취소되었습니다.",
            type: "success"
          })
        })
    },
    findByEmail(context, email) {
      return axios.get(`${BASE_URL}/volunteer/volunteer/${email}`)
    }
  },
}