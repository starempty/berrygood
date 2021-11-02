import axios from 'axios';
import { BASE_URL } from '@/config/constants';

export const Blood = {
    namespaced: true,
    actions: {
        // 헌혈증
        blood(context, payload) {
            console.log(payload)
            return axios
                .post(`${BASE_URL}/blood`, payload)
        },
        bloodInfo(context, email) {
            return axios
                .get(`${BASE_URL}/blood/${email}`)
        },
    }
}