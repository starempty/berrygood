import axios from 'axios';
import { BASE_URL } from '@/config/constants';

export const Tx = {
    namespaced: true,
    actions: {
        // 트랜잭션
        transactionCreate(context, tx) {
            console.log(tx)
            return axios
                .post(`${BASE_URL}/transaction`, tx)
        },
        transactionFrom(context, address) {
            let hash = address.toString().toLowerCase();
            return axios
                .get(`${BASE_URL}/transaction/from/${hash}`)
        },
        transactionTo(context, address) {
            let hash = address.toString().toLowerCase();
            return axios
                .get(`${BASE_URL}/transaction/to/${hash}`)
        },
    }
}