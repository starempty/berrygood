## 도커 이미지로 이더리움 네트워크 구축하기.

ec2에 도커 다운로드

```jsx
sudo yum -y upgrade
sudo yum -y install docker

## 도커 버전 확인
docker -v
```

geth 도커 이미지 다운로드

```jsx
docker pull pjt3591oo/ethereum-geth:1.90
```

도커 이미지 확인

```jsx
docker images
```

컨테이너 생성

```jsx
docker run -it -name "컨테이너 이름" -p 8545:8545 -p 30303:30303 pjt3591oo/ethereum-geth:1.90 /bin/bash
```

생성 후 다음과 같은 명령어 입력

```docker
mkdir node1
cd node1
geth --datadir $PWD account new ## 현재 폴더에 계정 생성 후 비밀번호 입력
```

Address: {c62d70f5dbdc5fa4eceba828142e571651299ae0}

pwd : 1234

해당 폴더에 `keystore` 파일 생성.

`keystore` : 비밀번호와 keystore 파일을 조합하여 개인키를 복구하는 역할

제네시스 파일 생성

`alloc` : 위에서 생성한 계정 주소 but 현재 진행 과정에서 에러가 나서 alloc 적용하지 않음

```docker
{
  "config": {
    "chainId": 921,
    "homesteadBlock": 0,
    "eip150Block": 0,
    "eip150Hash": "0x0000000000000000000000000000000000000000000000000000000000000000",
    "eip155Block": 0,
    "eip158Block": 0,
    "byzantiumBlock": 0,
    "constantinopleBlock": 0,
    "petersburgBlock": 0,
    "istanbulBlock": 0,
    "ethash": {}
  },
  "alloc": {},
  "nonce": "0xdeadbeefdeadbeef",
  "difficulty": "0x10",
  "extraData": "",
  "gasLimit": "9999999"
}
```

제네시스 블록을 생성하면 datadir에 명시한 해당 노드에서 데이터를 관리함

```docker
geth --datadir $PWD init genesis.json
```

노드 실행하기

```docker
## 일반적인 실행 방법
cd node1
geth --datadir $PWD --netweorkid 1234 console

## 현제 테스트중인 방법
## eth0
geth --networkid 921 --nodiscover --maxpeers 5 --datadir node1 --rpcaddr 0.0.0.0 --rpc --rpcport 8545 --rpccorsdomain "*" --rpcapi="db,eth,net,web3,personal,miner,admin" --miner.threads 1 console 2>> node1/geth.log
## eth1~
geth --networkid 921 --syncmode 'full' --maxpeers 5 --datadir node1 --rpcaddr 127.0.0.1 --rpc --rpcport 8545 --rpccorsdomain "*" --miner.threads 1 console 2>> node1/geth.log

```

networkid를 입력하는 이유는 프라이빗 네트워크를 실행할 때 필요한 추가적인 옵션이기 때문.

networkid는 1~4번까지는 메인 네트워크와 테스트 네트워크이기 때문에 1~4까지를 제외하고 아무수치나 적어주면 가능

도커 컨테이너 ip주소 확인법

```docker
##docker 컨테이너 ip주소 확인
sudo docker inspect -f "{{.NetworkSettings.IPAddress}}" CONTAINER_ID
```

노드를 실행할 때 console을 붙이지 않으면 이더리움 노드만 실행함. console을 붙여줘야 이더리움을 조작 가능.

이 아래에서부턴 기존 블록체인 방식과 동일함.

mining을 시작하면 generating 이 끊임없이 되고 있을텐데 상관없이 명령어를 입력하면 해당 명령어가 실행됨.

### 블록체인 명령어

```docker
%%writefile src/ethCommands.js
eth.accounts; // 존재하는 계좌 리스트
eth.blockNumber; // 마이닝이 된 만큼 블록이 있음
eth.coinbase; // 현재 코인베이스
eth.getTransactionCount(eth.accounts[0]) // 거래 수

var bal = eth.getBalance(eth.accounts[0]); // 계좌의 잔액
web3.fromWei(bal, "ether"); // wei를 ether 단위로 변경

miner.start(); // 마이닝 시작
miner.stop(); // 마이닝 중지
miner.start(1);admin.sleepBlocks(1);miner.stop() // 1회만 마이닝
miner.setEtherbase(eth.accounts[1]); // coinbase 설정

admin.nodeInfo; // 노드 정보
admin.peers; // peer 정보
net.peerCount; // peer 갯수
net.listening; // peer를 찾고 있는 중인지 확인
```
