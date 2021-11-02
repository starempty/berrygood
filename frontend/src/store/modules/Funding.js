import axios from 'axios';
import { BASE_URL } from '@/config/constants';
import { ElMessage } from 'element-plus'

export const Funding = {
    namespaced: true,
    actions: {
        // 펀딩
        fundingCreate(context, formData) {
            return axios
                .post(`${BASE_URL}/funding`, formData, {
                    headers: {
                        "Accept": "*/*",
                        "Content-Type": "multipart/form-data"
                    }
                })
        },
        fundingList() {
            return axios
                .get(`${BASE_URL}/funding`)
        },
        fundingDetail(context, fundingNo) {
            return axios
                .get(`${BASE_URL}/funding/${fundingNo}`)
        },
        fundingUpdate(context, { fundingNo, formData }) {
            return axios
                .post(`${BASE_URL}/funding/${fundingNo}`, formData, {
                    headers: {
                        "Accept": "*/*",
                        "Content-Type": "multipart/form-data"
                    }
                })
        },
        fundingDelete(context, fundingNo) {
            return axios
                .delete(`${BASE_URL}/funding/${fundingNo}`)
        },
        participantRegister(context, payload) {
            console.log(payload)
            return axios
                .post(`${BASE_URL}/funding/participant`, payload)
        },
        participantDelete(context, payload) {
            return axios
                .delete(`${BASE_URL}/funding/participant`, { data: { amount: payload.amount, projectId: payload.projectId, userEmail: payload.userEmail } })
                .then(() => {
                    ElMessage.error('펀딩이 취소되었습니다.')
                })
        },
        findByEmail(context, email) {
            return axios.get(`${BASE_URL}/funding/funding/${email}`)
        },
        findByCompemail(context, email) {
            return axios.get(`${BASE_URL}/funding/funding/comp/${email}`)
        },
        findByNo(context, no) {
            return axios.get(`${BASE_URL}/funding/comp/${no}`)
        }
    }
}