import Web3 from 'web3';
import { BLOCKCHAIN_URL, BERRY_CONTRACT_ADDRESS } from '@/config';
import { BERRY_CONTRACT_ABI } from '@/config/ABIs.js';

//BERRY 컨트랙트의 인스턴스
function createBerryContract(web3) {
  return new web3.eth.Contract(BERRY_CONTRACT_ABI, BERRY_CONTRACT_ADDRESS);
}
// Web3 Object 생성
export function createWeb3() {
  var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
  return web3;
}

export async function walletInfo(payload) {

  if (!payload) return {};
  let tmp = payload;
  var web3 = createWeb3();
  var contract = createBerryContract(web3);
  web3.eth.getBalance(payload.address).then((res) => {
    tmp.balance = parseInt(res / 1000000000000000000);
  });

  contract.methods
    .balanceOf(payload.address)
    .call()
    .then(res => {
      tmp.berry = parseInt(res);
    })
    .catch(err => {
      console.log(err)
    })
  console.log(tmp)
  return tmp;

}